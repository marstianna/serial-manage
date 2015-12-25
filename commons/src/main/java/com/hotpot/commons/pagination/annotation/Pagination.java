/*
 * 
 */
package com.hotpot.commons.pagination.annotation;

import java.lang.annotation.*;

// TODO: Auto-generated Javadoc

/**
 * APO标记注解，被注解的方法将会执行PagingProcess中定义的逻辑.
 *
 * @author Administrator
 * @see com.jumei.union.pagination.PagingProcess
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {

	/**
	 * 分页参数来源.
	 *
	 * @return the page param source
	 */
	public PageParamSource source() default PageParamSource.Request;

	/**
	 * 页数.
	 *
	 * @return the int
	 */
	public int pageNum() default 0;

	/**
	 * 分页大小.
	 *
	 * @return the int
	 */
	public int pageSize() default 0;

	/**
	 * 是否执行count查询.
	 *
	 * @return true, if successful
	 */
	public boolean count() default true;

	/**
	 * The Enum PageParamSource.
	 */
	public enum PageParamSource {

		/** The Request. */
		Request("Request"),
		/** The Explicit. */
		Explicit("Explicit");

		/** The source. */
		private final String source;

		/**
		 * Instantiates a new page param source.
		 *
		 * @param source
		 *            the source
		 */
		private PageParamSource(String source) {
			this.source = source;
		}

		/**
		 * Gets the source.
		 *
		 * @return the source
		 */
		public String getSource() {
			return source;
		}
	}
}
