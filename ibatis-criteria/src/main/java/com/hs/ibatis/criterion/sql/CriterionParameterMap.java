/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.sql;

import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;


/**
 *@FileName  CriterionParameterMap.java
 *@Date  16-5-25 上午10:44
 *@author Colley
 *@version 1.0
 */
public class CriterionParameterMap extends ParameterMap {
    public CriterionParameterMap(SqlMapExecutorDelegate delegate) {
        super(delegate);
    }
    

    public Object[] getParameterObjectValues(Object parameterObject) {
        return super.getParameterObjectValues(new StatementScope(new SessionScope()), parameterObject);
    }
}
