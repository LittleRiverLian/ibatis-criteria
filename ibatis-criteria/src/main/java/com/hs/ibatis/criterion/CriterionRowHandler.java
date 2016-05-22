/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import java.util.List;

/**
 *@FileName  CriterionRowHandler.java
 *@Date  16-5-22 下午2:00
 *@author Colley
 *@version 1.0
 */
public interface CriterionRowHandler<K, V> {

	List<K> handleRow(V valueObject,Class<?> clazz);
}
