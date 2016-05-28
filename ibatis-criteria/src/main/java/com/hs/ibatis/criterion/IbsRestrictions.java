/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import java.util.Collection;

import com.hs.ibatis.criterion.common.ExprOper;
import com.hs.ibatis.criterion.common.IbsMatchMode;


/**
 *@FileName  IbsRestrictions.java
 *@Date  16-5-20 上午11:21
 *@author Colley
 *@version 1.0
 */
public class IbsRestrictions {
    IbsRestrictions() {
        //cannot be instantiated
    }
    

    
    public static FuzzyExpression fuzzy(String propertyName, Object value,String op) {
        return new FuzzyExpression(propertyName, value,op);
    }
    
    /**
      * Apply an "equal" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static SimpleExpression eq(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ExprOper.eq);
    }

    /**
      * Apply a "not equal" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static SimpleExpression ne(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ExprOper.ne);
    }

    /**
      * Apply a "like" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static Criterion like(String propertyName, Object value) {
        return new LikeExpression(propertyName, value);
    }

    /**
      * Apply a "like" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static Criterion like(String propertyName, String value, IbsMatchMode matchMode) {
        return new LikeExpression(propertyName, value, matchMode);
    }
    
    public static Criterion notLike(String propertyName, String value, IbsMatchMode matchMode) {
        return new LikeExpression(propertyName, value,ExprOper.notLike,matchMode);
    }

    /**
      * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
      * operator
      *
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static Criterion ilike(String propertyName, String value, IbsMatchMode matchMode) {
        return new LikeExpression(propertyName, value, true, matchMode);
    }

    /**
      * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
      * operator
      *
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static Criterion ilike(String propertyName, Object value) {
        return new LikeExpression(propertyName, value, true, IbsMatchMode.EXACT);
    }

    /**
      * Apply a "greater than" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static SimpleExpression gt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ExprOper.gt);
    }

    /**
      * Apply a "less than" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static SimpleExpression lt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ExprOper.lt);
    }

    /**
      * Apply a "less than or equal" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static SimpleExpression le(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ExprOper.le);
    }

    /**
      * Apply a "greater than or equal" constraint to the named property
      * @param propertyName
      * @param value
      * @return Criterion
      */
    public static SimpleExpression ge(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ExprOper.ge);
    }

    /**
      * Apply a "between" constraint to the named property
      * @param propertyName
      * @param lo value
      * @param hi value
      * @return Criterion
      */
    public static Criterion between(String propertyName, Object lo, Object hi) {
        return new BetweenExpression(propertyName, lo, hi);
    }
    
    public static Criterion notBetween(String propertyName, Object lo, Object hi) {
        return new BetweenExpression(propertyName, lo, hi,ExprOper.notBetween);
    }

    /**
      * Apply an "in" constraint to the named property
      * @param propertyName
      * @param values
      * @return Criterion
      */
    public static Criterion in(String propertyName, Object[] values) {
        return new InExpression(propertyName, values, ExprOper.in);
    }

    /**
      * Apply an "in" constraint to the named property
      * @param propertyName
      * @param values
      * @return Criterion
      */
    @SuppressWarnings("rawtypes")
    public static Criterion in(String propertyName, Collection values) {
        return new InExpression(propertyName, values.toArray(), ExprOper.in);
    }
    
    /**
     * Apply an "not In" constraint to the named property
     * @param propertyName
     * @param values
     * @return Criterion
     */
   public static Criterion notIn(String propertyName, Object[] values) {
       return new InExpression(propertyName, values, ExprOper.notin);
   }

   /**
     * Apply an "not in" constraint to the named property
     * @param propertyName
     * @param values
     * @return Criterion
     */
   @SuppressWarnings("rawtypes")
   public static Criterion notIn(String propertyName, Collection values) {
       return new InExpression(propertyName, values.toArray(), ExprOper.notin);
   }

    /**
      * Apply an "is null" constraint to the named property
      * @return Criterion
      */
    public static Criterion isNull(String propertyName) {
        return new SimpleExpression(propertyName, ExprOper.isNull);
    }

    /**
      * Apply an "is not null" constraint to the named property
      * @return Criterion
      */
    public static Criterion isNotNull(String propertyName) {
        return new SimpleExpression(propertyName, ExprOper.isNotNull);
    }
    
    
    /**
	 * Apply an "equal" constraint to two properties
	 */
	public static PropertyExpression eqProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ExprOper.eq);
	}
	/**
	 * Apply a "not equal" constraint to two properties
	 */
	public static PropertyExpression neProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ExprOper.ne);
	}
	/**
	 * Apply a "less than" constraint to two properties
	 */
	public static PropertyExpression ltProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName,ExprOper.lt);
	}
	/**
	 * Apply a "less than or equal" constraint to two properties
	 */
	public static PropertyExpression leProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ExprOper.le);
	}
	/**
	 * Apply a "greater than" constraint to two properties
	 */
	public static PropertyExpression gtProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ExprOper.gt);
	}
	/**
	 * Apply a "greater than or equal" constraint to two properties
	 */
	public static PropertyExpression geProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ExprOper.ge);
	}
	
	/**
	 * Group expressions together in a single conjunction (A and B and C...)
	 *
	 * @return JunctionExpression
	 */
	public static JunctionExpression conjunction() {
		return new JunctionExpression(ExprOper.AND_JUNC);
	}

	/**
	 * Group expressions together in a single disjunction (A or B or C...)
	 *
	 * @return JunctionExpression
	 */
	public static JunctionExpression disjunction() {
		return new JunctionExpression(ExprOper.OR_JUNC);
	}
	
	public static PagingExpression limit(int startPos, int pageSize) {
		return new PagingExpression(startPos, pageSize, ExprOper.limit);
	}
}
