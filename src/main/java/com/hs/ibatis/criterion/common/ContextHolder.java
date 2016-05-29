/**
 * Copyright (C), 2011-2016 The Store
 * File Name: BaseDao.java
 * Encoding: UTF-8
 * Date: Sep 7, 2011
 * History: 
 */
package com.hs.ibatis.criterion.common;

/**
 * 
 * @author Wayne Wan (bestirwiny@gmail.com)
 * @version Revision: 1.00 Date: Sep 7, 2011
 */
public class ContextHolder {
	private static ThreadLocal<ContextEntity> threadVar = new ThreadLocal<ContextEntity>();

	public static ContextEntity get() {
		if (threadVar.get() == null) {
			return new ContextEntity();
		}
		return threadVar.get();
	}

	public static void set(ContextEntity obj) {
		threadVar.set(obj);
	}
}
