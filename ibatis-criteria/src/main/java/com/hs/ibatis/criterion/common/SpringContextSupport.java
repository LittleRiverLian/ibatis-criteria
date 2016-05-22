/**
 * Copyright (C), 2011-2016 The Store
 * File Name: SpringContextSupport.java
 * Encoding: UTF-8
 * Date: Nov 4, 2011
 * History: 
 */
package com.hs.ibatis.criterion.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Wayne Wan(bestirwiny@gmail.com)
 * @version Revision: 1.00 Date: Nov 4, 2011
 */
public class SpringContextSupport implements ApplicationContextAware {
	
	private static ApplicationContext context;
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	/**
	 * 
	 * @Title: getBean
	 * @Description:
	 * @param @param beanId
	 * @param @return
	 * @return Object
	 * @author Wayne Wan(wanyi@yihaodian.com)
	 * @date  Nov 4, 2011
	 */
	public static Object getBean(String beanId){
		return context.getBean(beanId);
	}
}
