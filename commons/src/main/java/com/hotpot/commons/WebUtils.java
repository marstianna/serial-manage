/*
 * 
 */
package com.hotpot.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc

/**
 * The Class WebUtils.
 */
public class WebUtils {

    /**
     * Gets the request.
     *
     * @return the request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * Gets the parameter map.
     *
     * @param request the request
     * @return the parameter map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<String, String>();
        Set<String> keys = request.getParameterMap().keySet();
        for (String key : keys) {
            if (StringUtils.isEmpty(request.getParameter(key))) {
                continue;
            }
            parameterMap.put(key, request.getParameter(key));
        }
        return parameterMap;
    }
    
	
}
