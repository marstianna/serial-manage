package com.hotpot.aop;

import com.hotpot.commons.WebUtils;
import com.hotpot.commons.pagination.PageHelper;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.commons.pagination.annotation.QueryBind;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Aspect
@Order(1)
public class PagingProcess {

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 拦截promotion下所有被Pagination注解的方法,使用PageHelper添加分页参数
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.hotpot..*.*(..)) && @annotation(pagination)")
    public Object around(ProceedingJoinPoint point, Pagination pagination)
            throws Throwable {

        int pageNum = DEFAULT_PAGE_SIZE;
        int pageSize = DEFAULT_PAGE_NUM;
        boolean count = true;
        String[] sidxs = null;
        String[] sorts = null;
        if (pagination.source() == Pagination.PageParamSource.Request) { // 从request中获取分页参数
            HttpServletRequest request = WebUtils.getRequest();
            Map<String, String> httpRequestParam = WebUtils.getParameterMap(request);

            pageNum = MapUtils.getIntValue(httpRequestParam, "page",
                    DEFAULT_PAGE_NUM);
            pageSize = MapUtils.getIntValue(httpRequestParam, "rows",
                    DEFAULT_PAGE_SIZE);
            count = !(httpRequestParam.containsKey("count")); // 当http中有count元素时,不需要统计count.

            sidxs = request.getParameterValues("sidx");

            sorts = request.getParameterValues("sord");

        } else {                                            // 从annotation中获取分页参数
            pageNum = pagination.pageNum() != 0 ? pagination.pageNum()
                    : DEFAULT_PAGE_NUM;
            pageSize = pagination.pageSize() != 0 ? pagination.pageSize()
                    : DEFAULT_PAGE_SIZE;
            count = pagination.count();
        }
        sidxs = filterOutEmpty(sidxs);
        sorts = filterOutEmpty(sorts);
        PageHelper.startPage(pageNum, pageSize, count, sidxs, sorts);

        Object object = point.proceed();

        PageHelper.clear();

        return object;
    }


    public static Integer isQueryBindParam(Annotation[][] annotations) {
        for (int idx = 0, length = annotations.length; idx < length; idx++) {
            for (Annotation anno : annotations[idx]) {
                if (anno.getClass() == QueryBind.class || StringUtils.equals(anno.annotationType().getName(), QueryBind.class.getName())) {
                    return idx;
                }
            }
        }
        return -1;
    }

    public String getMapperFullName(Class<?> clazz) {
        Class<?>[] clazzs = clazz.getInterfaces();
        for (Class<?> c : clazzs) {
            Repository repo = c.getAnnotation(Repository.class);
            if (repo != null) {
                return c.getName();
            }
        }
        return null;
    }

    /**
     * 移除数组中的空元素
     *
     * @param array
     */
    public String[] filterOutEmpty(String[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return array;
        }

        List<String> result = new ArrayList<String>();
        for (String r : array) {
            if (StringUtils.isBlank(r)) {
                continue;
            }
            result.add(r);
        }
        return result.toArray(new String[result.size()]);
    }
}
