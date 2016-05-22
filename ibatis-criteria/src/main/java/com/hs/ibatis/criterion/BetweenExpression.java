/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;


/**
 *@FileName  IbsBetweenExpression.java
 *@Date  16-5-20 上午11:18
 *@author Colley
 *@version 1.0
 */
public class BetweenExpression implements Criterion {
    private static final long serialVersionUID = -2136550490838064365L;
    private String property;
    private final Object lo;
    private final Object hi;
    private final String op;

    protected BetweenExpression(String property, Object lo, Object hi) {
        this.property = property;
        this.lo = lo;
        this.hi = hi;
        this.op = ExprOper.between.toString();
    }

    public String getOp() {
        return op;
    }

    public String getProperty() {
        return property;
    }

    public Object getLo() {
        return lo;
    }

    public Object getHi() {
        return hi;
    }
    
	@Override
	public void setProperty(String property) {
		this.property = property;
	}
}
