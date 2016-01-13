package com.hotpot.interceptor;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * the layout interceptor
 */
public class LayoutInterceptor implements HandlerInterceptor {

    private static final String USE_LAYOUT = "useLayout";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean useLayout = ServletRequestUtils.getBooleanParameter(request, USE_LAYOUT);
        if (useLayout == null || useLayout) {
            request.setAttribute(USE_LAYOUT, true);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        String url = request.getRequestURI() + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString());
//        request.setAttribute("originalURL", url);
        Map<String, String[]> params = request.getParameterMap();

        if (MapUtils.isNotEmpty(params)) {
            StringBuffer queryString = new StringBuffer();
            for (Map.Entry<String, String[]> param : params.entrySet()) {
                if (!USE_LAYOUT.equals(param.getKey()))
                    queryString.append(param.getKey()).append("=").append(param.getValue()[0]).append("&");
            }
            if (queryString.length() > 0) {
                String result = queryString.substring(0, queryString.length() - 1);
                request.setAttribute("requestURL", request.getRequestURL() + "?" + result + "&useLayout=false");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
