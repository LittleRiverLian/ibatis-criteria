package com.hs.ibatis.criterion.beans;

import java.util.List;

/**
 * 
 * 
 * @author wangbin1(wangbin1@yihaodian.com)
 * @version Revision: 1.00 Date: 2015-5-13
 */
public class Pagination<T> {

	private List<T> result;

	private int currentPage;

	private int pageSize = 10;

	private int totalRows;

	public Pagination() {
	}

	public Pagination(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return new Integer((new Double(Math.ceil(new Double(totalRows) / new Double(pageSize)))).intValue());
	}

	public int getNextPage() {
		return 0;
	}

	public int getPrevPage() {
		return 0;
	}

	public int getFirstResult() {
		return (currentPage - 1) * pageSize > 0 ? (currentPage - 1) * pageSize : 0;
	}
}
