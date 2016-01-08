package com.hotpot.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

/**
 * Created by zoupeng on 16/1/8.
 */
public class UrlMatcherTest {
    AntPathMatcher matcher = new AntPathMatcher();
    @Test
    public void test(){
//        String requestPath="/user/list.htm?username=aaa&id=2&no=1&page=20";
//        String patternPath="/user/list.htm**";

        //不完整路径匹配
//        String requestPath="/app/pub/login.do";
//        String patternPath="/**/login.do";

        //模糊路径匹配
        //String requestPath="/app/pub/login.do";
        //String patternPath="/**/*.do";

        //模糊单字符路径匹配
        String patternPath = "/**/static/**";
        String requestPath = "/static/js";

        boolean result = matcher.match(patternPath, requestPath);

        Assert.assertTrue(result);
    }
}
