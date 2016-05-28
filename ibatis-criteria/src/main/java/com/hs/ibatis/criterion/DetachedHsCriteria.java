/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hs.ibatis.criterion.common.HsSqlText;
import com.hs.ibatis.criterion.sql.AliasColumn;
import com.hs.ibatis.criterion.sql.GroupCriteria;
import com.hs.ibatis.criterion.sql.HsCriteria;
import com.hs.ibatis.criterion.sql.IbatisSelect;
import com.hs.ibatis.criterion.sql.IbsOrder;
import com.hs.ibatis.criterion.sql.JoinCriteria;
import com.hs.ibatis.criterion.sql.SqlFromCriteria;
import com.hs.ibatis.criterion.sql.SqlJoinCriteria;
import com.hs.ibatis.criterion.sql.TableJoinCriteria;


/**
 *@FileName  DetachedCriteria.java
 *@Date  16-5-20 下午5:09
 *@author Colley
 *@version 1.0
 */
public class DetachedHsCriteria implements Serializable {
    private static final long serialVersionUID = 2860555967858668492L;
    protected final static Log logger = LogFactory.getLog(DetachedHsCriteria.class);
    protected final static String HS_IBATISCRITERIA = "Ibatis_QueryByCriteriaSelect";
    private final HsCriteria criteria;
    private CriterionQuery criterionQuery;
    private String resultMappingId;

    protected DetachedHsCriteria(Object entity, boolean subSel) {
        this.criteria = subSel ? new SqlFromCriteria() : new IbatisSelect();
        this.criteria.setEntity(entity);
        this.criterionQuery = new CriterionQueryTranslator();
    }

    protected DetachedHsCriteria(Object entity) {
        this(entity, false);
    }

    public static DetachedHsCriteria forClass(Class<?> clazz) {
        Object Instance = null;
        if (clazz.isInterface()) {
            throw new RuntimeException(clazz.getName() + " is Interface");
        }

        try {
            Instance = clazz.newInstance();
        } catch (InstantiationException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("create " + clazz.getName() + " instance error", e);
                throw new RuntimeException("create " + clazz.getName() + " instance error");
            }
        } catch (IllegalAccessException e) {
            if (logger.isWarnEnabled()) {
                logger.error("create " + clazz.getName() + " instance error", e);
            }

            throw new RuntimeException("create " + clazz.getName() + " instance error");
        }

        return new DetachedHsCriteria(Instance);
    }

    public static DetachedHsCriteria forInstance() {
        return new DetachedHsCriteria(null);
    }
    
    
    public static DetachedHsCriteria forInstance(DetachedHsCriteria subCriteria) {
        return new DetachedHsCriteria(null);
    }

    public static DetachedHsCriteria subCriteriaInstance() {
        return new DetachedHsCriteria(null, true);
    }

    public static DetachedHsCriteria forEntityFullName(String entityFullName) {
        try {
            Class<?> clazz = Class.forName(entityFullName);
            return forClass(clazz);
        } catch (ClassNotFoundException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("create " + entityFullName + " instance error", e);
                throw new RuntimeException("create " + entityFullName + " instance error");
            }
        }

        return new DetachedHsCriteria(null);
    }

    public DetachedHsCriteria addColumnNames(Map<String, String> resultmapping) {
        if (MapUtils.isNotEmpty(resultmapping)) {
            for (Map.Entry<String, String> entry : resultmapping.entrySet()) {
                String displayName = entry.getKey();
                String columnName = entry.getValue();
                criteria.addColumn(columnName + " AS " + displayName);
            }
        }

        return this;
    }

    public DetachedHsCriteria addColumnName(AliasColumn aliasColumn) {
        criteria.addColumn(aliasColumn.getColumnName());
        return this;
    }

    public DetachedHsCriteria addColumnName(AliasColumn[] aliasColumns) {
    	if(ArrayUtils.isNotEmpty(aliasColumns)){
		   for (AliasColumn alias : aliasColumns) {
	            criteria.addColumn(alias.getColumnName());
	        }
    	}
        return this;
    }

    public DetachedHsCriteria addColumnName(String[] columnNames) {
        criteria.addColumn(columnNames);
        return this;
    }

    public DetachedHsCriteria addColumnName(List<String> columnNames) {
        criteria.addColumn(columnNames);
        return this;
    }

    public DetachedHsCriteria addFromClause(HsCriteria fromCriteria) {
        criteria.addFromClause(fromCriteria,"");
        return this;
    }
    
    public DetachedHsCriteria addFromClauseAlias(HsCriteria fromCriteria,String aliasTableName) {
        criteria.addFromClause(fromCriteria,aliasTableName);
        return this;
    }

    public DetachedHsCriteria add(Criterion criterion) {
        criteria.add(criterion);
        return this;
    }
    
    public DetachedHsCriteria add(List<Criterion> criterions) {
    	if(CollectionUtils.isNotEmpty(criterions)){
    		for(Criterion criterion:criterions){
    			if(criterion!=null){
    				 criteria.add(criterion);
    			}
    		}
    	}
        return this;
    }

    public DetachedHsCriteria addGroupByClause(GroupCriteria groupByClause) {
        criteria.addGroupBy(groupByClause);
        return this;
    }

    public DetachedHsCriteria addJoinsClause(JoinCriteria joinCriteria) {
        criteria.addFromJoins(joinCriteria);
        return this;
    }

    public DetachedHsCriteria leftJoinOn(String tabelName, String[] onClasuse) {
        criteria.addFromJoins(TableJoinCriteria.leftJoinOn(tabelName, onClasuse));
        return this;
    }

    public DetachedHsCriteria rightJoinOn(String tabelName, String[] onClasuse) {
        criteria.addFromJoins(TableJoinCriteria.rightJoinOn(tabelName, onClasuse));
        return this;
    }

    public DetachedHsCriteria joinOn(String tabelName, String[] onClasuse) {
        criteria.addFromJoins(TableJoinCriteria.joinOn(tabelName, onClasuse));
        return this;
    }

    public DetachedHsCriteria leftJoinOn(HsCriteria sqlCriteria,String aliasTabelName,  String[] onClasuse) {
        criteria.addFromJoins(SqlJoinCriteria.leftJoinOn(aliasTabelName, sqlCriteria, onClasuse));
        return this;
    }

    public DetachedHsCriteria rightJoinOn(HsCriteria sqlCriteria, String aliasTabelName, String[] onClasuse) {
        criteria.addFromJoins(SqlJoinCriteria.rightJoinOn(aliasTabelName, sqlCriteria, onClasuse));
        return this;
    }

    public DetachedHsCriteria joinOn(HsCriteria sqlCriteria,String aliasTabelName,  String[] onClasuse) {
        criteria.addFromJoins(SqlJoinCriteria.joinOn(aliasTabelName, sqlCriteria, onClasuse));
        return this;
    }

    public DetachedHsCriteria setResultMappingId(String namespace, String mappingId) {
        if (StringUtils.isNotEmpty(namespace)) {
            this.resultMappingId = namespace + "." + mappingId;
        } else {
            this.resultMappingId = mappingId;
        }

        return this;
    }

    public DetachedHsCriteria addOrder(IbsOrder order) {
        criteria.addOrder(order);
        return this;
    }

    public DetachedHsCriteria addLimit(Criterion criterion) {
        criteria.addPagingLimit(criterion);
        return this;
    }

    public HsSqlText getHsSqlText() {
        String newSql = criteria.getSqlString(criterionQuery);
        Object parameter = criterionQuery.getParameter();
        return new HsSqlText(newSql, parameter);
    }

    public HsCriteria getCriteria() {
        return criteria;
    }

    public HsCriteria getFromCriteria() {
        return criteria;
    }

    public Object getEntityBean() {
        return criteria.getEntity();
    }

    public String getResultMappingId() {
        return StringUtils.isEmpty(resultMappingId) ? "defaultMapping" : resultMappingId;
    }

    public String getDynamicQueryByCriteria() {
        return HS_IBATISCRITERIA;
    }
}
