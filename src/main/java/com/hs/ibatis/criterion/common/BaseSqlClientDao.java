/*
 * Copyright (c) 2016-2017 by colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.fastjson.JSON;
import com.hs.common.utils.GetterUtil;
import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.DetachedHsCriteria;
import com.hs.ibatis.criterion.sql.HsCriteria;
import com.hs.ibatis.criterion.sql.IbsOrder;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMapping;


/**
 * Ibatis操作基础类，封装常用的方法
 *@FileName  SemIbatisBaseDao.java
 *@Date  16-5-13 上午11:11
 *@author Ma Yuanchao
 *@version 1.0
 */
public abstract class BaseSqlClientDao extends SqlMapClientDaoSupport {
    protected static final int PAGE_SIZE = 4;
    protected static final String ALL_DEFAULT_DB_NAME = "all";
    protected static final Object[] NO_ARGUMENTS = new Object[0];
    private static Integer DEFAULT_MAX_RESULT_SET_SIZE = new Integer(10000); // 能够返回的记录集合最大值的缺省值
    protected Log log = LogFactory.getLog(this.getClass());
    private Integer maxResultSetSize = DEFAULT_MAX_RESULT_SET_SIZE; // 能够返回的记录集合最大值
    protected static ConcurrentHashMap<String, Map<String, String>> resultmapping = new ConcurrentHashMap<String, Map<String, String>>();

    public BaseSqlClientDao() {
    }

    /**
     * @return Returns the maxResultSetSize.
     */
    public Integer getMaxResultSetSize() {
        return maxResultSetSize;
    }

    /**
     * @param maxResultSetSize
     *            The maxResultSetSize to set.
     */
    public void setMaxResultSetSize(Integer maxResultSetSize) {
        this.maxResultSetSize = maxResultSetSize;
    }

    /**
     * 用于调试SQL语句的执行时间.
     */
    private void logRunTime(String statementName, long runTime) {
        if (log.isWarnEnabled()) {
            log.warn("current database is " + JSON.toJSONString(ContextHolder.get()));
            log.warn("Sql " + statementName + " executed, Run time estimated: " + runTime + " ms");
        }
    }

    /**
     * 查找单个对象，可以统计记录的个数
     *
     * @param statementName
     * @param parameterObject
     * @param qualification
     *
     * @return
     *
     * @throws DaoException
     */
    @SuppressWarnings("unchecked")
    protected <T> T queryForObject(String statementName, Object parameterObject)
        throws DaoException {
        long startTime = System.currentTimeMillis();
        statementName = getFullStatementName(statementName);
        T returnObject = null;
        try {
            returnObject = (T) this.getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
            long endTime = System.currentTimeMillis();
            logRunTime(statementName, endTime - startTime);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        return returnObject;
    }

    protected <T> T qryObjectForCriteria(String statementName, DetachedHsCriteria detachedCriteria)
        throws DaoException {
        String resultmappingId = detachedCriteria.getResultMappingId();
        HsCriteria baseCriteria = detachedCriteria.getCriteria();
        criteriaPreHandler(resultmappingId, baseCriteria);
        return this.queryForObject(statementName, baseCriteria);
    }

    /**
     * 返回查询列表
     * @param <T>
     *
     * @param statementName
     * @param parameterObject
     *
     * @return
     *
     * @throws DaoException
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> queryForList(String statementName, Object parameterObject)
        throws DaoException {
        long startTime = System.currentTimeMillis();
        statementName = getFullStatementName(statementName);
        List<T> list = new ArrayList<T>();
        try {
            list = this.getSqlMapClientTemplate().queryForList(statementName, parameterObject);

            long endTime = System.currentTimeMillis();
            logRunTime(statementName, endTime - startTime);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        return list;
    }

    protected <T> List<T> queryForCriteria(DetachedHsCriteria criteria)
        throws DaoException {
        return queryForCriteria(criteria, -1, -1);
    }

    protected <T> List<T> queryForCriteria(final DetachedHsCriteria criteria, int skipResults, int maxResults)
        throws DaoException {
        PreAssert.notNull(criteria, "DetachedCriteria must not be null");
        String mappingId = criteria.getResultMappingId();
        Map<String, String> mapping = fetchResultmapping(mappingId);
        criteria.addColumnNames(mapping);
        List<T> result = null;
        if ((skipResults == -1) && (maxResults == -1)) {
            result = queryForCriteria(criteria.getDynamicQueryByCriteria(), criteria);
        } else {
            result = queryForCriteria(criteria.getDynamicQueryByCriteria(), criteria, skipResults, maxResults);
        }

        return result;
    }

    /**
     * Ibatis 动态条件查询
     * @param statementName
     * @param resultMapId
     * @param baseCriteria
     * @return
     * @throws DaoException
     * @author mayuanchao
     * @date 2016年5月20日
     */
    protected <T> List<T> queryForCriteria(String statementName, String resultMapId, HsCriteria baseCriteria,
        int skipResults, int maxResults) throws DaoException {
        statementName = getFullStatementName(statementName);
        criteriaPreHandler(resultMapId, baseCriteria);

        if ((skipResults == -1) && (maxResults == -1)) {
            return this.queryForList(statementName, baseCriteria);
        }

        return this.queryForList(statementName, baseCriteria, skipResults, maxResults);
    }

    /**
     * Ibatis 动态条件查询
     * @param statementName
     * @param detachedCriteria
     * @return
     * @throws DaoException
     * @author mayuanchao
     * @date 2016年5月20日
     */
    protected <T> List<T> queryForCriteria(String statementName, DetachedHsCriteria detachedCriteria, int skipResults,
        int maxResults) throws DaoException {
        String mappingId = detachedCriteria.getResultMappingId();
        HsCriteria baseCriteria = detachedCriteria.getCriteria();
        return queryForCriteria(statementName, mappingId, baseCriteria, skipResults, maxResults);
    }

    protected <T> List<T> queryForCriteria(String statementName, DetachedHsCriteria detachedCriteria)
        throws DaoException {
        return queryForCriteria(statementName, detachedCriteria, -1, -1);
    }

    /**
     *分页查询数据
     * @param countStatementName 查询总条数的StatementName
     * @param qryStatementName  查询数据的StatementName
     * @param params            参数
     * @param total            总条数，这个参数用于查询总条数只查询一次，点击下一页的时候，传递过来就必要查询总条数了
     * @return Pagination
     * @throws DaoException
     * @author mayuanchao
     * @date 2016年5月13日
     */
    protected <T> Pagination<T> queryForPagination(String countStatementName, String qryStatementName,
        Object parameterObject, int total) throws DaoException {
        countStatementName = getFullStatementName(countStatementName);
        qryStatementName = getFullStatementName(qryStatementName);
        Pagination<T> pagination = new Pagination<T>();
        int count = total;
        if (total <= 0) {
            count = queryForInteger(countStatementName, parameterObject);
        }

        pagination.setTotalRows(count);
        if (count > 0) {
            List<T> result = queryForList(qryStatementName, parameterObject);
            if (CollectionUtils.isNotEmpty(result)) {
                pagination.setResult(result);
            }
        }

        return pagination;
    }

    /**
     * 分页查询数据
     * @param countStatementName
     * @param qryStatementName
     * @param parameterObject
     * @return
     * @throws DaoException
     * @author mayuanchao
     * @date 2016年5月13日
     */
    protected <T> Pagination<T> queryForPagination(String countStatementName, String qryStatementName,
        Object parameterObject) throws DaoException {
        return queryForPagination(countStatementName, qryStatementName, parameterObject, 0);
    }

    protected <T> List<T> executeQueryForList(String statementName, int skipResults, int maxResults)
        throws DaoException {
        return queryForList(statementName, null, skipResults, maxResults);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> queryForList(String statementName, Object parameterObject, int skipResults, int maxResults)
        throws DaoException {
        long startTime = System.currentTimeMillis();
        statementName = getFullStatementName(statementName);
        List<T> list = new ArrayList<T>();
        try {
            list = this.getSqlMapClientTemplate().queryForList(statementName, parameterObject, skipResults, maxResults);
            long endTime = System.currentTimeMillis();
            logRunTime(statementName, endTime - startTime);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        return list;
    }

    /**
     * 返回一个MAP结果集，KEY是返回DO中的一个字段名称
     * @param <K>
     *
     * @param statementName
     * @param parameterObject
     * @param dr
     * @param key
     *
     * @return
     *
     * @throws DaoException
     */
    @SuppressWarnings("unchecked")
    protected <K, V> Map<K, V> queryForMap(String statementName, Object parameterObject, String key)
        throws DaoException {
        long startTime = System.currentTimeMillis();
        statementName = getFullStatementName(statementName);
        Map<K, V> resultMap = new HashMap<K, V>();
        try {
            resultMap = this.getSqlMapClientTemplate().queryForMap(statementName, parameterObject, key);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        long endTime = System.currentTimeMillis();
        logRunTime(statementName, endTime - startTime);
        return resultMap;
    }

    /**
     * 更新数据库，可以插入一条记录，也可以删除一条记录 返回受影响的条数
     *
     * @param statementName
     * @param parameterObject
     * @param dr
     *
     * @return 被更新的记录数
     *
     * @throws DaoException
     */
    protected int update(String statementName, Object parameterObject)
        throws DaoException {
        int updateRows = 0;
        long startTime = System.currentTimeMillis();
        statementName = getFullStatementName(statementName);
        try {
            updateRows = this.getSqlMapClientTemplate().update(statementName, parameterObject);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        long endTime = System.currentTimeMillis();
        logRunTime(statementName, endTime - startTime);
        return updateRows;
    }

    /**
     * 插入一条记录
     *
     * @param statementName
     * @param parameterObject
     * @param dr
     *
     * @return 新增加的记录主键
     *
     * @throws DaoException
     */
    @SuppressWarnings("unchecked")
    protected <T> T insert(String statementName, Object parameterObject)
        throws DaoException {
        T back = null;
        long startTime = System.currentTimeMillis();
        statementName = getFullStatementName(statementName);
        // 调用Spring template
        try {
            back = (T) this.getSqlMapClientTemplate().insert(statementName, parameterObject);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        long endTime = System.currentTimeMillis();

        logRunTime(statementName, endTime - startTime);

        return back;
    }

    /**
     *批量插入记录
     * @param statementName                        sqlMap操作的id
     * @param parameterList                        需要插入的记录数
     * @param num                                  多少次插入一次
     * @return                                插入的主键列表，理论上size和parameterList是相等
     * @throws DaoException
     */
    protected void batchInsert(String statementName, List<?> parameterList, int num)
        throws DaoException {
        try {
            if ((parameterList == null) || parameterList.isEmpty()) {
                log.warn(
                    "event=[SemIbatisBaseDao#executeBatchInsert] parameterList is null or empty, statementName = [" +
                    statementName + "]");
                return;
            }

            long startTime = System.currentTimeMillis();

            this.getSqlMapClient().startBatch();
            for (int i = 0; i < parameterList.size(); i++) {
                int count = i + 1;

                this.getSqlMapClient().insert(statementName, parameterList.get(i));

                if (((count % num) == 0) || (count == parameterList.size())) {
                    this.getSqlMapClient().executeBatch();
                    if (count < parameterList.size()) {
                        this.getSqlMapClient().startBatch();
                    }
                }
            }

            long endTime = System.currentTimeMillis();

            logRunTime(statementName, endTime - startTime);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *批量更新记录
     * @param statementName                        sqlMap操作的id
     * @param parameterList                        需要更新的记录
     * @param num                                  多少次更新一次
     * @return                                插入的主键列表，理论上size和parameterList是相等
     * @throws DaoException
     */
    protected int batchUpdate(String statementName, List<?> parameterList, int num)
        throws DaoException {
        try {
            if ((parameterList == null) || parameterList.isEmpty()) {
                log.warn(
                    "event=[SemIbatisBaseDao#executeBatchUpdate] parameterList is null or empty, statementName = [" +
                    statementName + "]");
                return 0;
            }

            long startTime = System.currentTimeMillis();

            this.getSqlMapClient().startBatch();
            int total = 0;
            for (int i = 0; i < parameterList.size(); i++) {
                total = i;
                int count = i + 1;

                this.getSqlMapClient().update(statementName, parameterList.get(i));

                if (((count % num) == 0) || (count == parameterList.size())) {
                    this.getSqlMapClient().executeBatch();
                    if (count < parameterList.size()) {
                        getSqlMapClient().startBatch();
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            logRunTime(statementName, endTime - startTime);
            return total;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * 插入或更新一条记录，处理逻辑：先根据参数获取是否存在，存在就执行更新操作，不存在，执行插入操作
     * @param countStatementName
     * @param insertStatementName
     * @param updateStatementName
     * @param parameterObject
     * @param dr
     * @return Object
     * @throws DaoException
     */
    protected Object insertOrUpdate(String countStatementName, String insertStatementName, String updateStatementName,
        Object parameterObject) throws DaoException {
        Integer count = this.queryForObject(countStatementName, parameterObject);
        if ((null != count) && (count.intValue() > 0)) {
            int u = this.update(updateStatementName, parameterObject);
            return new Integer(u);
        }

        return this.insert(insertStatementName, parameterObject);
    }

    protected Object insertOrUpdate(String insertStatementName, String updateStatementName, Object parameterObject,
        boolean isUpdate) throws DaoException {
        if (isUpdate) {
            int u = this.update(updateStatementName, parameterObject);
            return new Integer(u);
        }

        return this.insert(insertStatementName, parameterObject);
    }

    /**
     * @param statementName
     * @return
     * @author mayuanchao
     * @date 2016年5月13日
     */
    private String getFullStatementName(String statementName) {
        //String fullStatementName = namespace + "." + statementName;
        String fullStatementName = statementName;
        log.info("the full statementName is :" + fullStatementName);
        return fullStatementName;
    }

    /**
     * 返回Inteter，用于获取总数或者返回一个Integer的sql
     * @param statement
     * @param parameter
     * @return
     * @throws DaoException
     * @author mayuanchao
     * @date 2016年5月13日
     */
    protected int queryForInteger(String statement, Object parameter)
        throws DaoException {
        Integer rtn = queryForObject(statement, parameter);
        if (rtn == null) {
            return 0;
        }

        return rtn.intValue();
    }

    protected int deleteForCriteria(String statementName, DetachedHsCriteria detachedCriteria)
        throws DaoException {
        String resultmappingId = detachedCriteria.getResultMappingId();
        HsCriteria baseCriteria = detachedCriteria.getCriteria();
        criteriaPreHandler(resultmappingId, baseCriteria);
        return this.delete(statementName, baseCriteria);
    }

    /**
     * 删除操作
     * @param statement
     * @param parameter
     * @return
     * @throws DaoException
     * @author mayuanchao
     * @date 2016年5月13日
     */
    protected int delete(String statement, Object parameter)
        throws DaoException {
        return this.getSqlMapClientTemplate().delete(getFullStatementName(statement), parameter);
    }

    /**
     * 批量删除
     * @param statementName
     * @param parameterList
     * @param num
     * @return
     * @author mayuanchao
     * @date 2016年5月13日
     */
    protected int batchDelete(String statementName, List<?> parameterList, int num) {
        try {
            if ((parameterList == null) || parameterList.isEmpty()) {
                log.warn(
                    "event=[SemIbatisBaseDao#executeBatchDelete] parameterList is null or empty, statementName = [" +
                    statementName + "]");
                return 0;
            }

            long startTime = System.currentTimeMillis();

            this.getSqlMapClient().startBatch();
            int total = 0;
            for (int i = 0; i < parameterList.size(); i++) {
                total = i;
                int count = i + 1;

                this.getSqlMapClient().delete(statementName, parameterList.get(i));

                if (((count % num) == 0) || (count == parameterList.size())) {
                    this.getSqlMapClient().executeBatch();
                    if (count < parameterList.size()) {
                        getSqlMapClient().startBatch();
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            logRunTime(statementName, endTime - startTime);
            return total;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void criteriaPreHandler(String resultmappingId, HsCriteria baseCriteria) {
        Criterion[] criteria = baseCriteria.allCriteria();
        List<IbsOrder> orders = baseCriteria.getOrderBys();
        Map<String, String> mapping = fetchResultmapping(resultmappingId);
        if (log.isWarnEnabled()) {
            log.warn("fetchResultmapping by id:" + resultmappingId + " data={}" + JSON.toJSONString(mapping));
        }

        //处理order columName
        if (CollectionUtils.isNotEmpty(orders)) {
            for (IbsOrder order : orders) {
                String columname = order.getPropertyName();
                String newColumname = mapping.get(columname);
                if (StringUtils.isNotEmpty(newColumname)) {
                    order.setPropertyName(newColumname);
                } else {
                    if (log.isWarnEnabled()) {
                        log.warn("columname is " + columname + " don't found table columname");
                    }
                }
            }
        }

        //处理动态columName
        if (ArrayUtils.isNotEmpty(criteria)) {
            if (MapUtils.isNotEmpty(mapping)) {
                for (Criterion criter : criteria) {
                    String columname = criter.getProperty();
                    String newColumname = mapping.get(columname);
                    if (StringUtils.isNotEmpty(newColumname)) {
                        criter.setProperty(newColumname);
                    } else {
                        if (log.isWarnEnabled()) {
                            log.warn("columname is " + columname + " don't found table columname");
                        }
                    }
                }
            }
        }
    }

    protected Map<String, String> fetchResultmapping(String id) {
        if (!resultmapping.containsKey(id)) {
            try {
                SqlMapClientImpl sqlmap = (SqlMapClientImpl) this.getSqlMapClient();
                SqlMapExecutorDelegate delegate = sqlmap.getDelegate();
                @SuppressWarnings("rawtypes")
                Iterator iter = delegate.getResultMapNames();
                while (iter.hasNext()) {
                    String mappingId = iter.next().toString();
                    ResultMap resultmaps = delegate.getResultMap(mappingId);
                    if (resultmaps != null) {
                        ResultMapping[] mappings = resultmaps.getResultMappings();
                        Map<String, String> mapMapping = new HashMap<String, String>();
                        for (int i = 0; i < mappings.length; i++) {
                            ResultMapping mapping = mappings[i];
                            mapMapping.put(mapping.getPropertyName(), mapping.getColumnName());
                        }

                        if (MapUtils.isNotEmpty(mapMapping)) {
                            resultmapping.putIfAbsent(mappingId, mapMapping);
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("fetchResultmapping failed,mapping id=" + id, e);
            }
        }

        return GetterUtil.getFixDatamap(resultmapping.get(id));
    }
}
