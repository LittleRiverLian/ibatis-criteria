package com.hs.ibatis.criterion.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.hs.ibatis.criterion.BaseTestCase;
import com.hs.ibatis.criterion.DetachedHsCriteria;
import com.hs.ibatis.criterion.IbsRestrictions;
import com.hs.ibatis.criterion.common.IbsMatchMode;
import com.hs.ibatis.criterion.sample.ISampleDao;
import com.hs.ibatis.criterion.sample.entity.SemUser;
import com.hs.ibatis.criterion.sql.IbsOrder;
import com.hs.ibatis.criterion.sql.TableFromCriteria;

public class SampleDaoTest extends BaseTestCase {

	 @Resource
	private ISampleDao sampleDao;
	
	 
	   @Test
		public void testSample1(){
			DetachedHsCriteria detachedIbtsCriteria
			= DetachedHsCriteria.forClass(SemUser.class);
			detachedIbtsCriteria.addFromClause(TableFromCriteria.setTableName("SEM_USER"));
			detachedIbtsCriteria.add(IbsRestrictions.between("age", 10, 20));
			detachedIbtsCriteria.add(IbsRestrictions.ilike("userName", "coll",IbsMatchMode.END));
			detachedIbtsCriteria.add(IbsRestrictions.notIn("age", new Integer[]{6,5,4}));
			detachedIbtsCriteria.add(IbsRestrictions.in("age", new Integer[]{10,20}));
			detachedIbtsCriteria.add(IbsRestrictions.isNotNull("address"));
			detachedIbtsCriteria.add(IbsRestrictions.eq("userName", "colley"));
			detachedIbtsCriteria.addOrder(IbsOrder.asc("age"));
			List<SemUser> list = sampleDao.findByCriteria(detachedIbtsCriteria,"findAllSemUser");
			System.out.println(JSON.toJSONString(list));
		}
		
	 
	@Test
	public void testSample(){
		DetachedHsCriteria detachedIbtsCriteria
		= DetachedHsCriteria.forClass(SemUser.class);
		detachedIbtsCriteria.addFromClause(TableFromCriteria.setTableName("SEM_USER"));
		detachedIbtsCriteria.add(IbsRestrictions.between("age", 10, 20));
		detachedIbtsCriteria.add(IbsRestrictions.ilike("userName", "coll",IbsMatchMode.END));
		detachedIbtsCriteria.add(IbsRestrictions.notIn("age", new Integer[]{6,5,4}));
		detachedIbtsCriteria.add(IbsRestrictions.in("age", new Integer[]{10,20}));
		detachedIbtsCriteria.add(IbsRestrictions.isNotNull("address"));
		detachedIbtsCriteria.add(IbsRestrictions.eq("userName", "colley"));
		detachedIbtsCriteria.addOrder(IbsOrder.asc("age"));
		List<SemUser> list = sampleDao.findByCriteria(detachedIbtsCriteria);
		System.out.println(JSON.toJSONString(list));
	}
	
	
	
}


