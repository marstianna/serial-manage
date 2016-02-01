package com.hotpot.filter;

import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zoupeng on 16/1/8.
 */
public class AuthFilter implements Filter{
    private static final String[] WHITE_URL = {"/**/static/**","/**/login/**","/**/test/**"};

    private static AntPathMatcher matcher	= new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        String url = req.getServletPath();

        if(urlMatch(url, WHITE_URL)){
            chain.doFilter(request, response);
            return;
        }
        String loginInfo = String.valueOf(req.getSession().getAttribute("loginInfo"));
        if(loginInfo == null || !loginInfo.equals("success")){
            req.setAttribute("srcPath", req.getRequestURI());
            req.getRequestDispatcher("/login/turnToLogin").forward(req, rep);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean urlMatch(String url,String[] patterns){
        if (url == null || patterns == null)
            return false;

        for (String pattern : patterns)
        {
            if (matcher.match(pattern, url))
                return true;
        }
        return false;
    }
}
