/*
 * 
 */
package com.hotpot.commons.pagination;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Mybatis - 分页对象
 * 修改原版增加排序支持.
 *
 * @version 3.2.2
 * @param <E> the element type
 */
public class Page<E> extends ArrayList<E> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8164651926571332545L;

	/** 不进行count查询. */
    private static final int NO_SQL_COUNT = -1;

    /** 进行count查询. */
    private static final int SQL_COUNT = 0;

    /** The page num. */
    private int pageNum;
    
    /** The page size. */
    private int pageSize;
    
    /** The start row. */
    private int startRow;
    
    /** The end row. */
    private int endRow;
    
    /** The total. */
    private long total;
    
    /** The pages. */
    private int pages;

    /** The sidxs. */
    private String[] sidxs;
    
    /** The sorts. */
    private String[] sorts;
    
    /**
     * Instantiates a new page.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     */
    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, SQL_COUNT);
    }

    /**
     * Instantiates a new page.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     * @param count the count
     */
    public Page(int pageNum, int pageSize, boolean count) {
        this(pageNum, pageSize, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT);
    }

    /**
     * Instantiates a new page.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     * @param total the total
     */
    public Page(int pageNum, int pageSize, int total) {
        super(pageSize);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
    }

    /**
     * Instantiates a new page.
     *
     * @param pageNum the page num
     * @param pageSize the page size
     * @param count the count
     * @param sidxs the sidxs
     * @param sorts the sorts
     */
    public Page(int pageNum, int pageSize,boolean count,String[] sidxs,String[] sorts) {
    	this(pageNum,pageSize,count);
    	this.sidxs = sidxs;
    	this.sorts = sorts;
    }

    
    /**
     * Instantiates a new page.
     *
     * @param rowBounds the row bounds
     * @param count the count
     */
    public Page(RowBounds rowBounds, boolean count) {
        this(rowBounds, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT);
    }


    /**
     * Instantiates a new page.
     *
     * @param rowBounds the row bounds
     * @param total the total
     */
    public Page(RowBounds rowBounds, int total) {
        super(rowBounds.getLimit());
        this.pageSize = rowBounds.getLimit();
        this.startRow = rowBounds.getOffset();
        //RowBounds方式默认不求count总数，如果想求count,可以修改这里为SQL_COUNT
        this.total = total;
        this.endRow = this.startRow + this.pageSize;
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public List<E> getResult() {
        return this;
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
        this.pages = (int) (total / this.pageSize + ((total % this.pageSize == 0) ? 0 : 1));
    }

    /**
     * Checks if is count.
     *
     * @return true, if is count
     */
    public boolean isCount() {
        return this.total > NO_SQL_COUNT;
    }

    
    /**
     * Gets the sidxs.
     *
     * @return the sidxs
     */
    public String[] getSidxs() {
		return sidxs;
	}

	/**
	 * Sets the sidxs.
	 *
	 * @param sidxs the new sidxs
	 */
	public void setSidxs(String[] sidxs) {
		this.sidxs = sidxs;
	}

	/**
	 * Gets the sorts.
	 *
	 * @return the sorts
	 */
	public String[] getSorts() {
		return sorts;
	}

	/**
	 * Sets the sorts.
	 *
	 * @param sorts the new sorts
	 */
	public void setSorts(String[] sorts) {
		this.sorts = sorts;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                ", sidxs" + ArrayUtils.toString(sidxs) +
                ", sorts" + ArrayUtils.toString(sorts) +
                '}';
    }
}