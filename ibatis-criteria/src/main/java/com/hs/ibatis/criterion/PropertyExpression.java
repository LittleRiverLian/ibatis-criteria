/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import com.hs.ibatis.criterion.common.ExprOper;

/**
 *@FileName  PropertyExpression.java
 *@Date  16-5-24 下午1:17
 *@author Colley
 *@version 1.0
 */
public class PropertyExpression implements Criterion {
    private static final long serialVersionUID = 2471086998662951133L;
    private String propertyName;
    private String otherPropertyName;
    private final ExprOper exprOper;

    protected PropertyExpression(String propertyName, String otherPropertyName, ExprOper exprOper) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
        this.exprOper = exprOper;
    }

    public void setProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setProperty(String propertyName, String otherPropertyName) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
    }

    public String getProperty() {
        return propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getOtherPropertyName() {
        return otherPropertyName;
    }

    public String getOp() {
        return exprOper.getOp();
    }
    
    public String getOpType() {
		return ExprOper.proExpr.name();
	}

    @Override
    public String getSqlString(CriterionQuery criterionQuery){
    	return new StringBuffer().append(propertyName)
    			.append(getOp()).append(otherPropertyName)
    			.toString();
    }
}
