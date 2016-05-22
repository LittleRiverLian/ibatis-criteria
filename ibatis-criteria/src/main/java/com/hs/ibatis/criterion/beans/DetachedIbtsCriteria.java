/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.beans;

import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.IbsOrder;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

import java.util.Map;


/**
 *@FileName  DetachedCriteria.java
 *@Date  16-5-20 下午5:09
 *@author Colley
 *@version 1.0
 */
public class DetachedIbtsCriteria implements Serializable {
    private static final long serialVersionUID = 2860555967858668492L;
    protected final static Log logger = LogFactory.getLog(DetachedIbtsCriteria.class);
    public final static String  HS_IBATISCRITERIA="findHs_IbatisCriteria";
    private final IbatisCriteria criteria;
    private String resultMappingId;

    protected DetachedIbtsCriteria(Object entity) {
        this.criteria = new IbatisCriteria();
        this.criteria.setEntity(entity);
    }

    public static DetachedIbtsCriteria forClass(Class<?> clazz) {
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

        return new DetachedIbtsCriteria(Instance);
    }

    public static DetachedIbtsCriteria forInstance(IbtsCriteria Instance) {
        return new DetachedIbtsCriteria(Instance);
    }

    public static DetachedIbtsCriteria forEntityFullName(String entityFullName) {
        try {
            Class<?> clazz = Class.forName(entityFullName);
            return forClass(clazz);
        } catch (ClassNotFoundException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("create " + entityFullName + " instance error", e);
                throw new RuntimeException("create " + entityFullName + " instance error");
            }
        }

        return new DetachedIbtsCriteria(null);
    }

    public DetachedIbtsCriteria addColumnNames(Map<String, String> resultmapping) {
        if (MapUtils.isNotEmpty(resultmapping)) {
            for (Map.Entry<String, String> entry : resultmapping.entrySet()) {
                String displayName = entry.getKey();
                String columnName = entry.getValue();
                criteria.addColumn(columnName + " as " + displayName);
            }
        }

        return this;
    }

    public void setTableName(String tableName) {
        criteria.setTableName(tableName);
    }

    public DetachedIbtsCriteria add(Criterion criterion) {
        criteria.add(criterion);
        return this;
    }

    public DetachedIbtsCriteria setmappingId(String mappingId) {
        this.resultMappingId = mappingId;
        return this;
    }

    public DetachedIbtsCriteria addOrder(IbsOrder order) {
        criteria.addOrder(order);
        return this;
    }

    public IbtsCriteria getCriteria() {
        return criteria;
    }

    public Object getEntityBean() {
        return criteria.getEntity();
    }

    public String getResultMappingId() {
        return resultMappingId;
    }
}
