/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.common;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSON;
import com.hs.ibatis.criterion.sql.CriterionParameterMap;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.InlineParameterMapParser;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
import com.ibatis.sqlmap.engine.mapping.sql.SqlText;
import com.ibatis.sqlmap.engine.type.TypeHandlerFactory;


/**
 *@FileName  BaseJdbcClientDao.java
 *@Date  16-5-25 上午11:08
 *@author Colley
 *@version 1.0
 */
public class BaseJdbcClientDao extends JdbcTemplate {
    private final InlineParameterMapParser PARAM_PARSER = new InlineParameterMapParser();
    private final TypeHandlerFactory typeHandlerFactory = new TypeHandlerFactory();
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 用于调试SQL语句的执行时间.
     */
    private void logRunTime(long runTime) {
        if (logger.isWarnEnabled()) {
            logger.warn("Sql  executed, Run time estimated: " + runTime + " ms");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> queryForCriteria(String sql, Object parameterObject, Class<T> clazzType)
        throws DaoException {
        long startTime = System.currentTimeMillis();
        SqlText sqlText = parseSqlText(sql, parameterObject);
        String newSql = sqlText.getText();
        if (logger.isWarnEnabled()) {
            logger.warn("new Execution sql:" + newSql);
            logger.warn("current database is " + JSON.toJSONString(ContextHolder.get()));
        }

        Object[] paramterObjectValues = getParameterObjectValues(sqlText, parameterObject);
        if (logger.isWarnEnabled()) {
            logger.warn("finaly Execution sql:" + sqlText.getText());
            logger.warn("finaly parameter:" + JSON.toJSONString(paramterObjectValues));
        }

        List<T> result = null;
        try {
            if (ArrayUtils.isEmpty(paramterObjectValues)) {
                result = super.queryForList(newSql, clazzType);
            } else {
                result = super.queryForList(newSql, paramterObjectValues, clazzType);
            }
        } finally {
            long endTime = System.currentTimeMillis();
            logRunTime(endTime - startTime);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    public List queryForCriteria(String sql, Object parameterObject)throws DaoException {
        long startTime = System.currentTimeMillis();
        SqlText sqlText = parseSqlText(sql, parameterObject);
        String newSql = sqlText.getText();
        if (logger.isWarnEnabled()) {
            logger.warn("new Execution sql:" + newSql);
            logger.warn("current database is " + JSON.toJSONString(ContextHolder.get()));
        }

        Object[] paramterObjectValues = getParameterObjectValues(sqlText, parameterObject);
        if (logger.isWarnEnabled()) {
            logger.warn("finaly Execution sql:" + sqlText.getText());
            logger.warn("finaly parameter:" + JSON.toJSONString(paramterObjectValues));
        }

        List result = null;
        try {
            if (ArrayUtils.isEmpty(paramterObjectValues)) {
                result = super.queryForList(newSql);
            } else {
                result = super.queryForList(newSql, paramterObjectValues);
            }
        } finally {
            long endTime = System.currentTimeMillis();
            logRunTime(endTime - startTime);
        }

        return result;
    }

    public SqlText parseSqlText(String newSql, Object parameterObject) {
        SqlText sqlText = PARAM_PARSER.parseInlineParameterMap(typeHandlerFactory, newSql, parameterObject.getClass());
        return sqlText;
    }

    public Object[] getParameterObjectValues(SqlText sqlText, Object parameterObject) {
        CriterionParameterMap paramMap = new CriterionParameterMap(new SqlMapExecutorDelegate());
        paramMap.setParameterClass(parameterObject.getClass());
        List<ParameterMapping> mappingList = Arrays.asList(sqlText.getParameterMappings());
        paramMap.setParameterMappingList(mappingList);
        return paramMap.getParameterObjectValues(parameterObject);
    }
}
