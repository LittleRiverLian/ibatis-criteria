/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.common;

public enum ExprOper {
	eq("="), 
	ieq("="),
	ne("<>"), 
	like(" like "), 
	ilike(" like "),
	notLike(" not like "),
	gt(">"), 
	lt("<"), 
	le("<="), 
	ge(">="), 
	in(" IN"),
	notin(" NOT IN"),
	isNull("IS NULL"), 
	isNotNull("IS NOT NULL"), 
	notBetween(" not between "),
	between(" between "),
	proExpr("proExpr"),
	groupBy(" GROUP BY "),
	subSel("subSel"),
	tName("tName"),
	fuzzy("fuzzy"),
	limit(" limit "),
	AND_JUNC(" AND "),
	OR_JUNC(" OR "),
	LFET_JOIN(" LEFT JOIN "), 
	RIGHT_JOIN(" RIGHT JOIN "), 
	JOIN(" JOIN ");
	
	
	private String op;
	ExprOper(String op){
		this.op = op;
	}
	public String getOp() {
		return op;
	}
}
