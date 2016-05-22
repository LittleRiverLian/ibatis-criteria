/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.beans;

import java.io.Serializable;
import java.util.List;

import com.hs.ibatis.criterion.Criterion;
import com.hs.ibatis.criterion.IbsOrder;


/**
 *@FileName  BaseCriteria.java
 *@Date  16-5-20 下午1:38
 *@author Colley
 *@version 1.0
 */
public interface IbtsCriteria extends Serializable {
    public Criterion[] getCriteria();

    List<IbsOrder> getOrderBys();

    public void addOrder(IbsOrder order);

    public void add(Criterion criterion);
}
