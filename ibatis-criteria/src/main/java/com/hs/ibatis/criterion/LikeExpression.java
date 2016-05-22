/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

/**
 *@FileName  IlikeExpression.java
 *@Date  16-5-20 上午11:31
 *@author Colley
 *@version 1.0
 */
public class LikeExpression implements Criterion {
    private static final long serialVersionUID = 6691136444709699737L;
    private  String property;
    private final Object value;
    private final boolean ignoreCase;
    private final String op;
    private final String matchMode;
    
    protected LikeExpression(String property, Object value) {
		this(property, value,false, IbsMatchMode.EXACT);
	}
    
    protected LikeExpression(String property, Object value,IbsMatchMode matchMode) {
		this(property, value,false, matchMode);
	}

	protected LikeExpression(String property, Object value,boolean ignoreCase, IbsMatchMode matchMode) {
		this.property = property;
		this.value = value;
		this.matchMode = matchMode.toString();
		this.ignoreCase = ignoreCase;
		if(ignoreCase){
			this.op = ExprOper.ilike.toString();
		}else{
			this.op = ExprOper.like.toString();
		}
	}

	public String getProperty() {
		return property;
	}

	public Object getValue() {
		return value;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public String getOp() {
		return op;
	}

	public String getMatchMode() {
		return matchMode;
	}

	@Override
	public void setProperty(String property) {
		this.property = property;
	}
}
