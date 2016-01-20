/*
 * 
 */
package com.hotpot.commons.framework;

import com.alibaba.fastjson.JSONObject;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc

/**
 * The Class BaseController.
 */
public class BaseController {

    /** The page error. */
    protected String PAGE_ERROR = "error";

    /** The PAG e_404. */
    protected String PAGE_404 = "404";

    /** The PAG e_500. */
    protected String PAGE_500 = "500";

    /**
     * The Enum ALERTTYPE.
     */
    public static enum ALERTTYPE {

        /** The error. */
        ERROR,
        /** The info. */
        INFO
    }

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Gets the real ip addr.
     *
     * @return the real ip addr
     */
    protected String getRealIpAddr() {
        HttpServletRequest req = this.getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (StringUtils.contains(ip, ",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    /**
     * Sets the alert message.
     *
     * @param message
     *            the message
     * @param type
     *            the type
     */
    protected void setAlertMessage(String message, ALERTTYPE type) {
        this.getRequest().setAttribute("alertMessage", message);
        this.getRequest().setAttribute("alertType", type);
    }

//    /**
//     * Gets the login user.
//     *
//     * @return the login user
//     */
//    protected UserInfo getLoginUser() {
//        return ThreadLocalUser.get();
//    }

    /**
     * Error page.
     *
     * @param mes
     *            the mes
     * @return the string
     */
    protected String errorPage(String mes) {
        this.setRequestAttribute("mes", mes);
        return PAGE_ERROR;
    }

    /**
     * Error.
     *
     * @param message
     *            the message
     * @return the string
     */
    public String error(String message) {
        this.setAlertMessage(message, ALERTTYPE.ERROR);
        return "error";
    }

    /**
     * Gets the request.
     *
     * @return the request
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * Response_404.
     *
     * @param response
     *            the response
     * @return the string
     */
    protected String response_404(HttpServletResponse response) {
        try {
            response.sendError(404);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;// 设置responseCode为404后,不需要显示返回页面
    }

    /**
     * Sets the request attribute.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    protected void setRequestAttribute(String key, Object value) {
        this.getRequest().setAttribute(key, value);
    }

    /**
     * Gets the parameter map.
     *
     * @return the parameter map
     */
    protected Map<String, String> getParameterMap() {
        return getParameterMap(this.getRequest());
    }

    /**
     * Gets the parameter map.
     *
     * @param request
     *            the request
     * @return the parameter map
     */
    @SuppressWarnings("unchecked")
    protected Map<String, String> getParameterMap(HttpServletRequest request) {
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

    /**
     * 拦截所有系统级异常.
     *
     * @param ex
     *            the ex
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the object
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error(ex.getMessage(), ex);
        if (isAjaxRequest(request)) {
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("message", ex.getMessage());
            result.put("details", ExceptionUtils.getStackTrace(ex));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            flush(response, result);
            return null;
        } else {
            ModelAndView exceptionView = new ModelAndView("500");
            exceptionView.addObject("title", "System fault");
            exceptionView.addObject("message", ex.getMessage());
            return exceptionView;
        }
    }

    /**
     * 拦截后台验证异常.
     *
     * @param ex
     *            the ex
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the object
     */
    @ExceptionHandler(BindException.class)
    public Object handleBindExceptionn(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        if (isAjaxRequest(request)) {
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("status", false);
            result.put("message", ex.getMessage());
            result.put("details", ExceptionUtils.getStackTrace(ex));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            flush(response, result);
            return null;
        } else {
            ModelAndView inputView = new ModelAndView("error/formerror");
            inputView.addObject("fieldErrors", ex.getBindingResult().getFieldErrors());
            return inputView;
        }
    }

    // @ExceptionHandler(ResourceNotFoundException.class)
    /**
     * Handle resource not found exception.
     *
     * @return the string
     */
    public String handleResourceNotFoundException() {
        return "meters/notfound";
    }

    /**
     * Checks if is ajax request.
     *
     * @param request
     *            the request
     * @return true, if is ajax request
     */
    protected boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (StringUtils.isNotBlank(requestType) && StringUtils.equals(requestType, "XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    /**
     * Flush.
     *
     * @param response
     *            the response
     * @param data
     *            the data
     */
    protected void flush(HttpServletResponse response, Object data) {
        try {
            BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(JSONObject.toJSONString(data).getBytes("UTF-8"));
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Out binding result.
     *
     * @param bindResult
     *            the bind result
     * @return the string
     */
    protected String outBindingResult(BindingResult bindResult) {
        StrBuilder sb = new StrBuilder();
        List<FieldError> errors = bindResult.getFieldErrors();
        for (FieldError error : errors) {
            sb.append(error.getField()).append(":").append(error.getDefaultMessage());
        }
        return sb.toString();
    }

    /**
     * Hide menu.
     */
    protected void hideMenu() {
        this.setRequestAttribute("hideMenu", true);
    }

    /**
     * Adds the root cookie.
     *
     * @param key
     *            the key
     * @param val
     *            the val
     * @param response
     *            the response
     */
    protected void addRootCookie(String key, String val, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(getRequest(), key);

        if (cookie == null)
            cookie = new Cookie(key, val);
        else
            cookie.setValue(val);

        cookie.setPath("/");
        response.addCookie(cookie);
        cookie.setMaxAge(1200);
    }

    protected <E,O> PageInfo getResultPage(Page<O> origin,List<E> results){
        PageInfo pageInfo = new PageInfo(origin);
        pageInfo.setList(results);
        return pageInfo;
    }


}

