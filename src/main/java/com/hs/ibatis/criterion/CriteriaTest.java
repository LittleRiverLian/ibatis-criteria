package com.hs.ibatis.criterion;

import com.alibaba.fastjson.JSON;
import com.hs.ibatis.criterion.common.BaseJdbcClientDao;
import com.hs.ibatis.criterion.common.ExprOper;
import com.hs.ibatis.criterion.common.HsSqlText;
import com.hs.ibatis.criterion.common.IbsMatchMode;
import com.hs.ibatis.criterion.sql.AliasColumn;
import com.hs.ibatis.criterion.sql.GroupByCrieria;
import com.hs.ibatis.criterion.sql.GroupCriteria;
import com.hs.ibatis.criterion.sql.IbsOrder;
import com.hs.ibatis.criterion.sql.JoinCriteria;
import com.hs.ibatis.criterion.sql.TableFromCriteria;
import com.hs.ibatis.criterion.sql.TableJoinCriteria;
import com.ibatis.sqlmap.engine.mapping.sql.SqlText;

public class CriteriaTest {
	static CriterionQuery criterionQuery = new CriterionQueryTranslator();
	
	
	public static void testInExpression(){
		InExpression in = new InExpression("age1", new Integer[]{1,3,4,5},ExprOper.in);
		System.out.println(in.getSqlString(criterionQuery));
	}
	
	public static void testSimpleExpression(){
		Criterion simple = new SimpleExpression("t.age",123,ExprOper.eq);
		System.out.println(simple.getSqlString(criterionQuery));
		
	}
	
	public static void testLikeExpression(){
		Criterion like = IbsRestrictions.ilike("name", "coll");
		System.out.println(like.getSqlString(criterionQuery));
		like = IbsRestrictions.notLike("name1", "c", IbsMatchMode.ANYWHERE);
		System.out.println(like.getSqlString(criterionQuery));
		PropertyExpression pro = IbsRestrictions.gtProperty("t2.age", "t.age");
		System.out.println(pro.getSqlString(criterionQuery));
		
		JunctionExpression junction = IbsRestrictions.conjunction();
		junction.add(like);
		junction.add(pro);
		System.out.println(junction.getSqlString(criterionQuery));
		
		FuzzyExpression fuzzy = IbsRestrictions.fuzzy("test", "test", ">=");
		System.out.println(fuzzy.getSqlString(criterionQuery));
		junction.add(fuzzy);
		System.out.println(junction.getSqlString(criterionQuery));
	}
	
	public static void testSelectSql(){
		DetachedHsCriteria detachedIbtsCriteria = DetachedHsCriteria.forInstance();
		detachedIbtsCriteria.addColumnName(AliasColumn.neAs("t2.AD_KEYWORDS_ID,t2.KEYWORD_MAX_CPC as historyValue,t2.CHANNEL_ID"));
		detachedIbtsCriteria.addColumnName(AliasColumn.neAs("t2.CHANNEL_ACCOUNT_ID,t2.MATCH_TYPE,t3.AD_GROUP_ID,t4.AD_CAMPAIGN_ID"));
		detachedIbtsCriteria.addColumnName(AliasColumn.neAs("t4.name as AD_CAMPAIGN_NAME,t2.STATUS as historyStatus,t3.name as AD_GROUP_NAME"));
		detachedIbtsCriteria.addColumnName(AliasColumn.neAs("t2.name as AD_KEYWORD_NAME"));
		detachedIbtsCriteria.addColumnName(AliasColumn.neAs("t5.ORDER_COUNT"));
		detachedIbtsCriteria.addColumnName(AliasColumn.as("CASE WHEN SUM(t5.ORDER_COUNT_NEW_CLIENT) = 0 THEN 0 ELSE CAST(ROUND(SUM(t5.COSTS)/SUM(t5.ORDER_COUNT_NEW_CLIENT),2) AS CHAR(50)) END","CPN"));
		detachedIbtsCriteria.addColumnName(AliasColumn.as("CASE WHEN SUM(t5.COSTS) = 0 THEN 0 ELSE CAST(ROUND(SUM(t5.SALES_VALUE)/SUM(t5.COSTS),2) AS CHAR(50)) END","ROI"));
		detachedIbtsCriteria.addFromClause(TableFromCriteria.setTableName("AD_KEYWORD t2"));
		JoinCriteria join = TableJoinCriteria.leftJoinOn("AD_KEYWORD_REPORT t5", new String[]{"t5.AD_KEYWORDS_ID","t2.AD_KEYWORDS_ID"});
		join.add(IbsRestrictions.fuzzy("t2.client_tag_id", "22", ">"));
		detachedIbtsCriteria.addJoinsClause(join);
		detachedIbtsCriteria.add(IbsRestrictions.in("t2.CHANNEL_ACCOUNT_ID", new Integer[]{1,2,3}));
		
		
		GroupCriteria groupBy = GroupByCrieria.groupBy(new String[]{"t2.AD_KEYWORDS_ID,t2.CHANNEL_ID,t3.AD_GROUP_ID,t4.AD_CAMPAIGN_ID"});
		groupBy.add(IbsRestrictions.gt("RANKING", 0));
		detachedIbtsCriteria.addGroupByClause(groupBy);
		
		detachedIbtsCriteria.addOrder(IbsOrder.asc("RANKING"));
		
		
		HsSqlText sqlTxt = detachedIbtsCriteria.getHsSqlText();
		System.out.println(JSON.toJSONString(sqlTxt.getNewSql()));
		System.out.println(JSON.toJSONString(sqlTxt.getParameter()));
		BaseJdbcClientDao jdbc = new BaseJdbcClientDao();
   	    SqlText sqlText = jdbc.parseSqlText(sqlTxt.getNewSql(), sqlTxt.getParameter());
   	    System.out.println(sqlText.getText());
   	    System.out.println(JSON.toJSONString(jdbc.getParameterObjectValues(sqlText, sqlTxt.getParameter())));
		
	}
	
	public static void main(String[] args) {
		testInExpression();
		testSimpleExpression();
		testLikeExpression();
		String name ="t2.NAME";
		System.out.println(name.replace(".", "_").replace("[", "").replace("]", ""));
		System.out.println(JSON.toJSONString(criterionQuery.getParameter()));
		testSelectSql();
	}
}
