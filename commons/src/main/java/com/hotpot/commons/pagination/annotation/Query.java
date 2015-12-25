/*
 * 
 */
package com.hotpot.commons.pagination.annotation;

import java.lang.annotation.*;

// TODO: Auto-generated Javadoc

/**
 * The Interface Query.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

	/**
	 * Identify.
	 *
	 * @return the string
	 */
	public String identify() default "";
	
	/**
	 * Mapper.
	 *
	 * @return the string
	 */
	public String mapper();

	/**
	 * Action.
	 *
	 * @return the string
	 */
	public String action();
	
}
