package com.hs.ibatis.criterion.sample;

import java.util.List;

import com.hs.ibatis.criterion.DetachedHsCriteria;
import com.hs.ibatis.criterion.common.BaseSqlClientDao;
import com.hs.ibatis.criterion.sample.entity.SemUser;

public class SampleDaoImpl extends BaseSqlClientDao implements ISampleDao {

	@Override
	public List<SemUser> findByCriteria(DetachedHsCriteria detachedIbtsCriteria) {
		detachedIbtsCriteria.setResultMappingId("semUser","SemUserResult");
		return super.queryForCriteria(detachedIbtsCriteria);
	}

	@Override
	public List<SemUser> findByCriteria(DetachedHsCriteria detachedIbtsCriteria, String statementName) {
		detachedIbtsCriteria.setResultMappingId("semUser","SemUserResult");
		return super.queryForCriteria(statementName, detachedIbtsCriteria);
	}
}
