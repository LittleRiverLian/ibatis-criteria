/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.sql;

import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.CriterionQuery;


/**
 *@FileName  JoinCriteria.java
 *@Date  16-5-24 上午10:07
 *@author Colley
 *@version 1.0
 */
public interface JoinCriteria {
    public String getOp();

    public String getTableName();

    public Criterion[] getCriteria();

    public Criterion getOnCriteria();

    public void add(Criterion criterion);
    
    public String getSqlString(CriterionQuery criterionQuery);
    
    public String getOpType();


}
