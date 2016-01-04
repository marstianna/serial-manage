/**
 * Copyright (C) 2012-2013 jumei, Inc. 
 * GroupedMessageSource.java
 * 2014年9月14日
 */
package com.hotpot.commons;

import org.springframework.context.HierarchicalMessageSource;

import java.util.Locale;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * The Interface GroupedMessageSource.
 *
 * @author shizhongl@jumei.com
 */
public interface GroupedMessageSource extends HierarchicalMessageSource {

    /**
     * Gets the messages by group.
     *
     * @param locale the locale
     * @param group the group
     * @return the messages by group
     */
    public Map<Object,Object> getMessagesByGroup(Locale locale, String group);
    
    /**
     * Gets the message.
     *
     * @param code the code
     * @return the message
     */
    public String getMessage(String code);
    
    /**
     * Gets the object.
     *
     * @param <R> the generic type
     * @param code the code
     * @return the object
     */
    public <R> R getObject(String code);
    
}
