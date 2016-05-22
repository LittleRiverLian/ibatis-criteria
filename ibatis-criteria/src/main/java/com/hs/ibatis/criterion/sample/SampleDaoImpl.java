package com.hs.ibatis.criterion.sample;

import java.util.List;

import com.hs.ibatis.criterion.beans.DetachedIbtsCriteria;
import com.hs.ibatis.criterion.common.BaseSqlClientDao;
import com.hs.ibatis.criterion.sample.entity.SemUser;

public class SampleDaoImpl extends BaseSqlClientDao implements ISampleDao {

	@Override
	public List<SemUser> findByCriteria(DetachedIbtsCriteria detachedIbtsCriteria) {
		detachedIbtsCriteria.setmappingId("semUser.SemUserResult");
		return super.queryForCriteria(detachedIbtsCriteria);
	}

	@Override
	public List<SemUser> findByCriteria(DetachedIbtsCriteria detachedIbtsCriteria, String statementName) {
		detachedIbtsCriteria.setmappingId("semUser.SemUserResult");
		return super.queryForCriteria(statementName, detachedIbtsCriteria);
	}
}
