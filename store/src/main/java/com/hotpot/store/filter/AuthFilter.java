package com.hotpot.store.filter;

import com.hotpot.commons.ApplicationContextUtil;
import com.hotpot.domain.Store;
import com.hotpot.service.StoreService;
import com.hotpot.service.impl.StoreServiceImpl;
import com.hotpot.service.Context;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by zoupeng on 16/1/8.
 */
public class AuthFilter implements Filter{
    private static final String[] WHITE_URL = {"/**/static/**","/**/login/**"};

    private static AntPathMatcher matcher	= new AntPathMatcher();

    private StoreService storeService = ApplicationContextUtil.getBean(StoreServiceImpl.class);

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
        Optional<String> loginInfo =  Optional.of(String.valueOf(req.getSession().getAttribute("loginInfo")));
        if(!loginInfo.orElse("false").equals("success")){
            req.setAttribute("srcPath", req.getRequestURI());
            req.getRequestDispatcher("/login/turnToLogin").forward(req, rep);
            return;
        }
        try {
            Integer storeId = Integer.parseInt(String.valueOf(req.getSession().getAttribute("storeId")));
            Store store = storeService.getStoreByStoreId(storeId);
            Context.set(store);
            chain.doFilter(request, response);
        }finally {
            Context.remove();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * @param url
     * @param patterns
     * @return
     */
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
