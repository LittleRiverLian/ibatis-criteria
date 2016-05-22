/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.hs.ibatis.criterion.common.DaoException;


/**
 *@FileName  DefaultCriteriRowHandler.java
 *@Date  16-5-22 下午2:06
 *@author Colley
 *@version 1.0
 *
 * @param <T> DOCUMENT ME!
 */
public class DefaultCriteriRowHandler<T> implements CriterionRowHandler<T, List<Map<String, Object>>> {
    @SuppressWarnings("unchecked")
	@Override
    public List<T> handleRow(List<Map<String, Object>> maplist, Class<?> clazz) {
        List<T> criteriaResult = new ArrayList<T>();
        if (CollectionUtils.isNotEmpty(maplist)) {
            for (Map<String, Object> propertie : maplist) {
                try {
                    T criteriaBean = (T) clazz.newInstance();
                    BeanUtils.populate(criteriaBean, propertie);
                    criteriaResult.add(criteriaBean);
                } catch (Exception e) {
                    throw new DaoException(e);
                }
            }
        }

        return criteriaResult;
    }
}
