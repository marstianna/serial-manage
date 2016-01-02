/*
 * 
 */
package com.hotpot.commons;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO: Auto-generated Javadoc

/**
 * Add grouping supports to MessageResouce
 * 
 * Temporary way to solve codes related problem since message codes may be fetched from database later.
 */
public class GroupedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource implements
        GroupedMessageSource {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(GroupedResourceBundleMessageSource.class);

    /** The Constant GLOBAL_LOCALE. */
    private static final Locale GLOBAL_LOCALE = new Locale("global");

    /** The lock. */
    private Lock lock = new ReentrantLock();

    /** The grouped properties. */
    protected Map<Object, Map<Object, Object>> groupedProperties = new ConcurrentHashMap<Object, Map<Object, Object>>();
    
    private String[] basenames;

    /* (non-Javadoc)
     * @see com.jumei.union.framework.support.messagesource.GroupedMessageSource#getMessage(java.lang.String)
     */
    @Override
    public String getMessage(String code) {
        try {
            return getMessage(code, null, null);
        } catch (NoSuchMessageException e) {
            return StringUtils.EMPTY;
        }
    }

    /* (non-Javadoc)
             * @see com.jumei.union.framework.support.messagesource.GroupedMessageSource#getObject(java.lang.String)
             */
    @SuppressWarnings("unchecked")
    @Override
    public <R> R getObject(String code) {
        return (R) this.getMessage(code);
    }

    /**
     * return a map of specific groupKey.
     *
     * @param locale the locale
     * @param group the group
     * @return the messages by group
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getMessagesByGroup(Locale locale, String group) {
        locale = ObjectUtils.defaultIfNull(locale, GLOBAL_LOCALE);
        if (!groupedProperties.containsKey(locale)) {
            PropertiesHolder holder = getMergedProperties(locale);
            initGroupedProperties(holder, locale);
        }

        String groupKey = constructGroupKey(locale, group);
        return groupedProperties.get(groupKey) != null ? MapUtils.unmodifiableMap(groupedProperties.get(groupKey))
                : MapUtils.EMPTY_MAP;
    }

    @Override public void clearCache() {
        super.clearCache();
    }

    /**
     * intercept parent's getMergedProperties method to initialize groupProperties.
     *
     * @param holder the holder
     * @param locale the locale
     */
    @SuppressWarnings("unchecked")
    protected void initGroupedProperties(PropertiesHolder holder, Locale locale) {
        try {
            lock.lock(); // ensure at most one thread can rebuild groupProperties at same time.

            if (groupedProperties.containsKey(locale)) { // if locale not empty, means this locale has bean initialized
                return;
            }
            
            for(String basename : basenames){
                loadPropertiesForGrouping(basename, locale);
            }

            // place a dummy to indicate that messages of specific locale has been grouped already.
            groupedProperties.put(locale, Maps.newLinkedHashMap());
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            lock.unlock();
        }
    }
    
    protected void loadPropertiesForGrouping(String baseName,Locale locale) throws IOException {
        List<String> fileNames = super.calculateAllFilenames(baseName, locale);
        for(String fileName : fileNames ){

            fileName = fileName + ".properties";
            
            URL resource = this.getClass().getClassLoader().getResource(fileName.replaceAll("classpath:",""));
            if(resource == null){
                continue;
            }

            try(InputStream inputStream = resource.openStream()){
                OrderedProperties props = new OrderedProperties();
                props.load(inputStream);
                Set<String> keys = props.stringPropertyNames();
                for (String name : keys) {
                    if (StringUtils.isEmpty(name)) {
                        continue;
                    }
                    groupingMessage(locale, name, props.get(name));
                }

            }
        }
    }

    @Override public void setBasenames(String... basenames) {
        if (basenames != null) {
            this.basenames = new String[basenames.length];
            for (int i = 0; i < basenames.length; i++) {
                String basename = basenames[i];
                Assert.hasText(basename, "Basename must not be empty");
                this.basenames[i] = basename.trim();
            }
        }
        else {
            this.basenames = new String[0];
        }
        super.setBasenames(basenames);
    }

    /**
     * Grouping message.
     *
     * @param locale the locale
     * @param name the name
     * @param value the value
     */
    private void groupingMessage(Locale locale, String name, Object value) {
        int lastDotIdx = name.lastIndexOf(".");

        if (lastDotIdx < 0) {
            return;
        }

        // split message key into [group] and [item]
        // e.g. order.status.paid -> group=order.status item=paid
        String group = name.substring(0, lastDotIdx);
        String item = name.substring(lastDotIdx + 1);

        // groupKey is combined by locale and group
        // e.g. zh_CN_order.status
        StrBuilder groupKey = new StrBuilder(Objects.toString(locale, "global"));
        groupKey.append("_");
        groupKey.append(group);

        Map<Object, Object> groupMap = groupedProperties.get(groupKey.toString());

        if (groupMap == null) {
            groupMap = new LinkedHashMap();
            groupedProperties.put(groupKey.toString(), groupMap);
        }

        groupMap.put(item, value);
    }

    /**
     * monitoring resource reloading.
     * purge cached groupedProperties once resource reloaded.
     *
     * @param filename the filename
     * @param propHolder the prop holder
     * @return the properties holder
     */
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        groupedProperties.clear();
        return super.refreshProperties(filename, propHolder);
    }

    /**
     * Construct group key.
     *
     * @param locale the locale
     * @param group the group
     * @return the string
     */
    private String constructGroupKey(Locale locale, String group) {
        StrBuilder groupKey = new StrBuilder(Objects.toString(locale, "global"));
        groupKey.append("_").append(group);
        return groupKey.toString();
    }

}
