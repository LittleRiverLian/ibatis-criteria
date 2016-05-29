/*
 * Copyright (c) 2016-2017 by colley
 * All rights reserved.
 */
package com.hs.ibatis.criterion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 *@FileName  BaseTestCase.java
 *@Date  16-5-21 下午11:09
 *@author Colley
 *@version 1.0
 */

@ContextConfiguration(locations =  {
    "classpath*:springTest.xml"}
)
public abstract class BaseTestCase extends AbstractJUnit4SpringContextTests {
    protected Log log = LogFactory.getLog(this.getClass());
     
      @Before  
      public void before(){  
    	  System.out.println(System.getProperty("os.name"));
          String osName = System.getProperty("os.name");
          if ((osName != null) && osName.toLowerCase().startsWith("win")) {
              // my configuration file
              System.setProperty("global.config.path", "D:/env/");
          } else {
              System.setProperty("global.config.path", "/var/www/webapps/config");
          }
          
          Logger semLogIbatis = Logger.getLogger("com.hs.ibatis.criterion.common");
          semLogIbatis.setLevel(Level.DEBUG);
      }  
  
}
