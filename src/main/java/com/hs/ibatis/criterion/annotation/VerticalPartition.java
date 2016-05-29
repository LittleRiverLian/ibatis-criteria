
package com.hs.ibatis.criterion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 垂直拆分注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface VerticalPartition {
	/**
	 * 垂直分库地址
	 * @Title: verticalAddr
	 * @Description:
	 * @param 
	 * @return String
	 */
	VerticalPartitionType verticalAddr();
}
