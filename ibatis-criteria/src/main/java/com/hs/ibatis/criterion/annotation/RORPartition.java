
package com.hs.ibatis.criterion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
/**
 * 读写分离注解
 * @author Colley
 *
 */
public @interface RORPartition {
    /**
     * 读/写标识
     * @Title: readOrWrite
     * @Description:
     * @param
     * @return ReadOrWrite
     */
    ReadOrWrite readOrWrite();
}
