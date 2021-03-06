/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.sql;

import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.CriterionQuery;

import java.io.Serializable;

import java.util.List;


/**
 *@FileName  BaseCriteria.java
 *@Date  16-5-20 下午1:38
 *@author Colley
 *@version 1.0
 */
public interface HsCriteria extends Serializable {
 public Criterion[] getCriteria();

    public Criterion[] allCriteria();

    List<IbsOrder> getOrderBys();

    public HsCriteria addOrder(IbsOrder order);

    public HsCriteria add(Criterion criterion);

    public HsCriteria addColumn(String columnName);

    public Object getFromClause();

    public JoinCriteria[] getFromJoins();

    public String getSqlString(CriterionQuery criterionQuery);

    public HsCriteria addColumn(String[] columnNames);

    public HsCriteria addColumn(List<String> columnNames);

    public HsCriteria addFromClause(HsCriteria fromCriteria, String aliasTableName);
    
    public HsCriteria addFromClause(HsCriteria fromClause);
    
    public HsCriteria addGroupBy(GroupCriteria groupByClause);

    public HsCriteria addFromJoins(JoinCriteria leftJoinOn);

    public HsCriteria addPagingLimit(Criterion criterion);

    public Object getEntity();

    public void setEntity(Object entity);

    public AliasColumn[] getColumnNames();

    public String getOp();

    public void setAliasTableName(String aliasTableName);

    public String getAliasTableName();
}
