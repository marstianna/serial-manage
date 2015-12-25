/*
 * 
 */
package com.hotpot.commons.pagination;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * Description: 对Page<E>结果进行包装 Author: liuzz.
 *
 * @version 3.2.2
 * @since 3.2.2
 */
public class PageInfo {
	
	/** The page num. */
	@JSONField(name = "page")
	private int pageNum;

	/** The page size. */
	private int pageSize;
	
	/** The start row. */
	private int startRow;
	
	/** The end row. */
	private int endRow;
	
	/** The total. */
	@JSONField(name = "records")
	private long total;
	
	/** The pages. */
	@JSONField(name = "total")
	private int pages;
	
	/** The list. */
	@JSONField(name = "rows")
	private List<?> list;

	/**
	 * Instantiates a new page info.
	 *
	 * @param list the list
	 */
	public PageInfo(List<?> list) {
		if (list instanceof Page) {
			Page<?> page = (Page<?>) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.startRow = page.getStartRow();
			this.endRow = page.getEndRow();
			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
		}
	}

	/**
	 * Gets the page num.
	 *
	 * @return the page num
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * Sets the page num.
	 *
	 * @param pageNum the new page num
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * Gets the page size.
	 *
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the page size.
	 *
	 * @param pageSize the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Gets the start row.
	 *
	 * @return the start row
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * Sets the start row.
	 *
	 * @param startRow the new start row
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * Gets the end row.
	 *
	 * @return the end row
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * Sets the end row.
	 *
	 * @param endRow the new end row
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Gets the pages.
	 *
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * Sets the pages.
	 *
	 * @param pages the new pages
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list the new list
	 */
	public void setList(List<?> list) {
		this.list = list;
	}
}
