/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import java.io.Serializable;


/**
 *@FileName  Criterion.java
 *@Date  16-5-20 下午1:48
 *@author Colley
 *@version 1.0
 */
public interface Criterion extends Serializable {
    public void setProperty(String property);
    
    public String getProperty();
}
