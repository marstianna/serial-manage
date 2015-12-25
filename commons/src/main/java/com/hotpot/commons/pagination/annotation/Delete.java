/*
 * 
 */
package com.hotpot.commons.pagination.annotation;

import java.lang.annotation.*;

// TODO: Auto-generated Javadoc

/**
 * The Interface Delete.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
	
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
