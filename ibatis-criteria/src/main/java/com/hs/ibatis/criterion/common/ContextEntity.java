/**
 * Copyright (C), 2011-2016 The Store
 * File Name: BaseDao.java
 * Encoding: UTF-8
 * Date: Sep 7, 2011
 * History: 
 */
package com.hs.ibatis.criterion.common;

import com.hs.ibatis.criterion.annotation.ReadOrWrite;
import com.hs.ibatis.criterion.annotation.VerticalPartitionType;

/**
 * <p>
 * 线程绑定上下文变量实体
 * 对于分库，需要设置垂直分库地址，channelId，以及读写标识
 * channelId可以不设置，不设置则默认为空字符串，其他两个必须设置
 * 如果涉及到跨库的操作则需要设置crossVerticalAddr,crossReadOrWrite,crossChannelId，
 * 对于未水平分库的分库无需设置channelId
 * <p>跨库实例如下：
 * <pre>
 * ContextHolder.get().setCrossVerticalAddr(VerticalPartitionType.CORE);
 * ContextHolder.get().setCrossChannelId(2);
 * 
 * </pre>
 * @author Wayne Wan (bestirwiny@gmail.com)
 * @version Revision: 1.00 Date: Sep 7, 2011
 */
public class ContextEntity {
	private static final String SPLITTER = "_";
	/**
	 * 当前事务中的分库读写分离参数
	 */
	private VerticalPartitionType verticalAddr ;
	
	private ReadOrWrite readOrWrite;
	
	private String channelId = "";
	
	/**
	 * 重新执行任务选择日期 
	 */
	private String runDate;
		
	/**
	 * 跨库操作中的分库 以及读写分离参数
	 */
	private VerticalPartitionType crossVerticalAddr ;
	
	private ReadOrWrite crossReadOrWrite;
	
	private String crossChannelId = "";
	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * 获取当前事务中用到的datasource
	 * @Title: setDataSourceKey
	 * @Description:
	 * @param 
	 * @return void
	 * @author Wayne Wan(waynewan@mail.com)
	 * @date  Apr 28, 2012
	 */
	public String getDataSourceKey() {
		if(getChannelId()!=null&&getChannelId().length()>0 && getVerticalAddr().equals(VerticalPartitionType.CORE)){
			HorizonalPartition horizonalPartition = (HorizonalPartition)SpringContextSupport.getBean("horizonalPartitionBean"); 
			return getVerticalAddr() + SPLITTER + horizonalPartition.getHorizonNodeAddr(getChannelId()) + SPLITTER + getReadOrWrite();
		}
		//非core库目前未做水平拆分
		return getVerticalAddr() + SPLITTER + SPLITTER + getReadOrWrite();
	}

	/**
	 * 获取执行跨库操作的datasource, 
	 * 若为null则表示下一个数据库操作依然使用当前事务中的datasource
	 * @Title: setDataSourceKey
	 * @Description:
	 * @param 
	 * @return void
	 * @author Wayne Wan(waynewan@mail.com)
	 * @date  Apr 28, 2012
	 */
	public String getCrossDataSourceKey() {
		if(getCrossChannelId()!=null&&getCrossChannelId().length()>0 && getCrossVerticalAddr().equals(VerticalPartitionType.CORE)){
			HorizonalPartition horizonalPartition = (HorizonalPartition)SpringContextSupport.getBean("horizonalPartitionBean"); 
			return getCrossVerticalAddr() + SPLITTER + horizonalPartition.getHorizonNodeAddr(getCrossChannelId()) + SPLITTER + getCrossReadOrWrite();
		}
		return getCrossVerticalAddr() + SPLITTER + SPLITTER + getCrossReadOrWrite();
	}

	/**
	 * 重置跨库操作lookupkey
	 * @Title: resetCrossDataSourceKey
	 * @Description:
	 * @param 
	 * @return void
	 * @author Wayne Wan(waynewan@mail.com)
	 * @date  May 2, 2012
	 */
	public void resetCrossDataSourceKey(){
		this.setCrossVerticalAddr(null);
		this.setCrossReadOrWrite(null);
	}
	
	public VerticalPartitionType getVerticalAddr() {
		return verticalAddr;
	}

	public void setVerticalAddr(VerticalPartitionType verticalAddr) {
		this.verticalAddr = verticalAddr;
	}

	public ReadOrWrite getReadOrWrite() {
		return readOrWrite==null?ReadOrWrite.WRITE:readOrWrite;
	}

	public void setReadOrWrite(ReadOrWrite readOrWrite) {
		this.readOrWrite = readOrWrite;
	}

	public VerticalPartitionType getCrossVerticalAddr() {
		return crossVerticalAddr;
	}

	public void setCrossVerticalAddr(VerticalPartitionType crossVerticalAddr) {
		this.crossVerticalAddr = crossVerticalAddr;
	}

	public ReadOrWrite getCrossReadOrWrite() {
		return crossReadOrWrite==null?ReadOrWrite.WRITE:crossReadOrWrite;
	}

	public void setCrossReadOrWrite(ReadOrWrite crossReadOrWrite) {
		this.crossReadOrWrite = crossReadOrWrite;
	}

	public String getCrossChannelId() {
		return crossChannelId;
	}

	public void setCrossChannelId(String crossChannelId) {
		this.crossChannelId = crossChannelId;
	}

	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}
	
}
