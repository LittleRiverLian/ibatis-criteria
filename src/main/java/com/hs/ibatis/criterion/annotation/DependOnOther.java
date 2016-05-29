/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识某类或某方法上的分库操作依赖后续或者已经存在现成绑定上下文(ContextEntity)
 * 例1：如果某service上加了该注解，则标识该service上不做分库标记，若service的某方法
 * 调用了dao，则分库逻辑就依赖dao上的分库标记。
 * 例2：如果某service上加了分库标记，而该service调用的某dao上没有加分库标记，则该dao方法
 * 的调用会采用service上的分库逻辑，若该dao也加了分库标记，则dao上的分库标记会覆盖
 * serviuce上的分库标记
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DependOnOther {}
