/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.IbsOrder;


/**
 *@FileName  BaseCriteria.java
 *@Date  16-5-20 下午5:49
 *@author Colley
 *@version 1.0
 */
public  class BaseIbtsCriteria implements IbtsCriteria {
    private static final long serialVersionUID = -6819935861456680579L;
    protected final  Log logger = LogFactory.getLog(this.getClass());
    private List<IbsOrder> orderBys = new ArrayList<IbsOrder>();
    private List<Criterion> criteria = new ArrayList<Criterion>();
    

    public List<IbsOrder> getOrderBys() {
        return orderBys;
    }


    public void addOrder(IbsOrder order) {
        if (order != null) {
            this.orderBys.add(order);
        }
    }

    public void add(Criterion criterion) {
        if (criterion != null) {
            criteria.add(criterion);
        }
    }

    public Criterion[] getCriteria() {
        if (CollectionUtils.isNotEmpty(criteria)) {
            return criteria.toArray(new Criterion[0]);
        }

        return null;
    }

    public String getOrderByStr() {
        if (CollectionUtils.isNotEmpty(orderBys)) {
            StringBuffer orderBy = new StringBuffer();
            String ascending = orderBys.get(0).isAscending() ? "ASC" : "DESC";
            for (int i = 0; i < orderBys.size(); i++) {
                IbsOrder order = orderBys.get(i);
                orderBy.append(order.getPropertyName());
                if (i < (orderBys.size() - 1)) {
                    orderBy.append(",");
                }
            }

            orderBy.append(" ").append(ascending);
            return orderBy.toString();
        }

        return null;
    }
}
