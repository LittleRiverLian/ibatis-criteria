/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.sql;

import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.CriterionQuery;


/**
 *@FileName  GroupCriteria.java
 *@Date  16-5-24 上午11:28
 *@author Colley
 *@version 1.0
 */
public interface GroupCriteria {
    Criterion[] getHaving();

    GroupCriteria add(Criterion criterion);

    GroupCriteria addColumn(String columnName);

    String[] getGroupByColumn();
    
    public String getSqlString(CriterionQuery criterionQuery);
}
