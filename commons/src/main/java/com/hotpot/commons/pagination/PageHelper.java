/*
	The MIT License (MIT)

	Copyright (c) 2014 abel533@gmail.com

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.
*/

package com.hotpot.commons.pagination;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// TODO: Auto-generated Javadoc

/**
 * Mybatis - 通用分页拦截器
 * 
 * 修改原分页组件,增加排序支持.
 *
 * @author pengz
 * @version 3.2.2
 */
@SuppressWarnings("rawtypes")
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageHelper implements Interceptor {
    
    /** The Constant DEFAULT_OBJECT_FACTORY. */
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    
    /** The Constant DEFAULT_OBJECT_WRAPPER_FACTORY. */
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    
    /** The Constant NULL_META_OBJECT. */
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

    /**
     * The Class NullObject.
     */
    private static class NullObject {
    }

    /**
     * 反射对象，增加对低版本Mybatis的支持.
     *
     * @param object the object
     * @return the meta object
     */
    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
    }

    /** The Constant BOUND_SQL. */
    private static final String BOUND_SQL = "sqlSource.boundSql.sql";

    /** The Constant LOCAL_PAGE. */
    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<Page>();

    /** The Constant EMPTY_RESULTMAPPING. */
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    //数据库方言
    /** The dialect. */
    private static String dialect = "";
    //RowBounds参数offset作为PageNum使用 - 默认不使用
    /** The offset as page num. */
    private static boolean offsetAsPageNum = false;
    //RowBounds是否进行count查询 - 默认不查询
    /** The row bounds with count. */
    private static boolean rowBoundsWithCount = false;

    /**
     * 开始分页.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     */
    public static void startPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize, true);
    }

    /**
     * 开始分页.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     * @param count the count
     */
    public static void startPage(int pageNum, int pageSize, boolean count) {
        LOCAL_PAGE.set(new Page(pageNum, pageSize, count));
    }

    /**
     * 开始分页.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     * @param count the count
     * @param sidxs 排序字段
     * @param sorts 升/降序标识
     */
    public static void startPage(int pageNum, int pageSize, boolean count,String[] sidxs,String[] sorts){
    	 LOCAL_PAGE.set(new Page(pageNum, pageSize, count,sidxs,sorts));
    }
    
    /**
     * 获取分页参数.
     *
     * @param rowBounds the row bounds
     * @return the page
     */
	private Page getPage(RowBounds rowBounds) {
        Page page = LOCAL_PAGE.get();
        //移除本地变量
        LOCAL_PAGE.remove();

        if (page == null) {
            if (offsetAsPageNum) {
                page = new Page(rowBounds.getOffset(), rowBounds.getLimit(), rowBoundsWithCount);
            } else {
                page = new Page(rowBounds, rowBoundsWithCount);
            }
        }
        return page;
    }

    /**
     * Mybatis拦截器方法.
     *
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     */
    @SuppressWarnings("unchecked")
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        if (LOCAL_PAGE.get() == null && rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        } else {
            //忽略RowBounds-否则会进行Mybatis自带的内存分页
            args[2] = RowBounds.DEFAULT;
            MappedStatement ms = (MappedStatement) args[0];
            Object parameterObject = args[1];
            BoundSql boundSql = ms.getBoundSql(parameterObject);

            //分页信息
            Page page = getPage(rowBounds);
            //创建一个新的MappedStatement
            MappedStatement qs = newMappedStatement(ms, new BoundSqlSqlSource(boundSql));
            //将参数中的MappedStatement替换为新的qs，防止并发异常
            args[0] = qs;
            MetaObject msObject = forObject(qs);
            String sql = (String) msObject.getValue(BOUND_SQL);
            //简单的通过total的值来判断是否进行count查询
            if (page.isCount()) {
                //求count - 重写sql
                msObject.setValue(BOUND_SQL, getCountSql(sql));
                //查询总数
                Object result = invocation.proceed();
                //设置总数
                page.setTotal((Integer) ((List) result).get(0));
            }
            //排序sql - 重写sql
            sql = getSortSql(sql,page);
            //分页sql - 重写sql
            msObject.setValue(BOUND_SQL, getPageSql(sql, page));
            //恢复类型
            msObject.setValue("resultMaps", ms.getResultMaps());
            //执行分页查询
            Object result = invocation.proceed();
            //得到处理结果
            page.addAll((List) result);
            //返回结果
            return page;
//            return new PageInfo(page);
        }
    }

    /**
     * Clear.
     */
    public static void clear(){
    	LOCAL_PAGE.remove();
    }
    
    /**
     * 排序SQL.
     *
     * @param sql the sql
     * @param page the page
     * @return the sort sql
     */
    private String getSortSql(String sql, Page page) {
    	 
    	if(ArrayUtils.isEmpty(page.getSidxs())){
    		 return sql;
    	 }
    	 
    	 StrBuilder pageSql = new StrBuilder(200);
         if ("mysql".equals(dialect)) {
        	 pageSql.append("select _s.* from (");
             pageSql.append(sql);
        	 pageSql.append(") _s order by ");
			 for (int idx = 0, max = page.getSidxs().length; idx < max; idx++) {
				 pageSql.append(page.getSidxs()[idx]).append(" ");
				 if(ArrayUtils.getLength(page.getSorts()) > idx){
					 pageSql.append(page.getSorts()[idx]).append(" ,");
				 }else{
					 pageSql.append("desc ").append(",");
				 }
        	 }
			 pageSql.deleteCharAt(pageSql.length() -1 );
         }
         return pageSql.toString();
	}

	/**
	 * 获取总数sql - 如果要支持其他数据库，修改这里就可以.
	 *
	 * @param sql the sql
	 * @return the count sql
	 */
    private String getCountSql(String sql) {
        return "select count(0) from (" + sql + ") tmp_count";
    }

    /**
     * 获取分页sql - 如果要支持其他数据库，修改这里就可以.
     *
     * @param sql the sql
     * @param page the page
     * @return the page sql
     */
    private String getPageSql(String sql, Page page) {
        StrBuilder pageSql = new StrBuilder(200);
        if ("mysql".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" limit " + page.getStartRow() + "," + page.getPageSize());
        } else if ("hsqldb".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" LIMIT " + page.getPageSize() + " OFFSET " + page.getStartRow());
        } else if ("oracle".equals(dialect)) {
            pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
            pageSql.append(sql);
            pageSql.append(" ) temp where rownum <= ").append(page.getEndRow());
            pageSql.append(") where row_id > ").append(page.getStartRow());
        }
        return pageSql.toString();
    }

    /**
     * The Class BoundSqlSqlSource.
     */
    private class BoundSqlSqlSource implements SqlSource {
        
        /** The bound sql. */
        BoundSql boundSql;

        /**
         * Instantiates a new bound sql sql source.
         *
         * @param boundSql the bound sql
         */
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        /* (non-Javadoc)
         * @see org.apache.ibatis.mapping.SqlSource#getBoundSql(java.lang.Object)
         */
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    /**
     * 由于MappedStatement是一个全局共享的对象，因而需要复制一个对象来进行操作，防止并发访问导致错误.
     *
     * @param ms the ms
     * @param newSqlSource the new sql source
     * @return the mapped statement
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_PageHelper", newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StrBuilder keyProperties = new StrBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //由于resultMaps第一次需要返回int类型的结果，所以这里需要生成一个resultMap - 防止并发错误
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), int.class, EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 只拦截Executor.
     *
     * @param target the target
     * @return the object
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 设置属性值.
     *
     * @param p the new properties
     */
    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (dialect == null || "".equals(dialect)) {
            throw new RuntimeException("Mybatis分页插件PageHelper无法获取dialect参数!");
        }
        //offset作为PageNum使用
        String offset = p.getProperty("offsetAsPageNum");
        if (offset != null && "TRUE".equalsIgnoreCase(offset)) {
            offsetAsPageNum = true;
        }
        //RowBounds方式是否做count查询
        String withcount = p.getProperty("rowBoundsWithCount");
        if (withcount != null && "TRUE".equalsIgnoreCase(withcount)) {
            rowBoundsWithCount = true;
        }
    }
}
