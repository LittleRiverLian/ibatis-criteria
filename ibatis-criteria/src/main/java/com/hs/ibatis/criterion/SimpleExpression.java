/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;


/**
 *@FileName  IbatisExpression.java
 *@Date  16-5-20 上午11:03
 *@author Colley
 *@version 1.0
 */
public class SimpleExpression implements Criterion {
    private static final long serialVersionUID = -2835703300683541644L;
    private  String property;
    private final Object value;
    private final boolean ignoreCase;
    private final String op;

    protected SimpleExpression(String property, ExprOper op) {
        this(property, null, op, false);
    }
    
    protected SimpleExpression(String property, Object value, ExprOper op) {
        this(property, value, op, false);
    }

    protected SimpleExpression(String property, Object value, ExprOper op, boolean ignoreCase) {
        this.property = property;
        this.value = value;
        this.ignoreCase = ignoreCase;
        this.op = op.toString();
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
    

	@Override
	public void setProperty(String property) {
		this.property = property;
	}
}
