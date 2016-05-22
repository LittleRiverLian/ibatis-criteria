/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/**
 *@FileName  IbatisCriteria.java
 *@Date  16-5-21 下午9:58
 *@author Colley
 *@version 1.0
 */
public class IbatisCriteria extends BaseIbtsCriteria {
    private static final long serialVersionUID = 4310740208332982492L;
    private Object entity;
    private List<String> columnNames = new ArrayList<String>();
    private String tableName;

    public String[] getColumnNames() {
        if (CollectionUtils.isNotEmpty(columnNames)) {
            return columnNames.toArray(new String[0]);
        }

        return null;
    }

    public void addColumn(String columnName) {
        if (StringUtils.isNotEmpty(columnName) && !columnNames.contains(columnName)) {
            columnNames.add(columnName);
        }
    }
    
    public void setTableName(String tableName){
    	this.tableName = tableName;
    }
    

    public String getTableName() {
		return tableName;
	}

	public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
