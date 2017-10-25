package com.openkx.repository;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.openkx.domain.Question;
import com.openkx.utils.MyPage;

@Repository
@Transactional

public class QuestionDao extends BaseDao<Question> {
	/**
	 * 首页内容分页,查询10条
	 * 模糊查询
	 * @param page
	 * @return MyPage<Question>
	 */
	public MyPage<Question> FindPage(int page, String keyword){
	log.debug("select MyPage<Question> desc ctime");
	 	try{
		    DetachedCriteria dc = DetachedCriteria.forClass(Question.class);//和
		    if(StringUtils.isNoneBlank(keyword)){
		    	Disjunction disjunction = Restrictions.disjunction();//或者
		    	
		    	disjunction.add(Property.forName("title").like(keyword,MatchMode.ANYWHERE));
		    	disjunction.add(Property.forName("context").like(keyword,MatchMode.ANYWHERE));
		    	dc.add(disjunction);
	    }
		    dc.addOrder(Order.desc("ctime"));//通过 ctime 来排序
		    return findPageByCriteria(dc,page);  //question 进行分页查询然后传回页面
		} catch (RuntimeException re) {
		    log.error("select MyPage<Question> desc ctime", re);
		    throw re;
		}

	}
	
	
	
	public void DelectArticle(String id){
		delete(id, Question.class);
	}
	
	public Question findById(String id) {
		return findById(id, Question.class);
	}
	

	public void save(Question question){
		getSession().save(question);
	}
	/**
	 * 通过栏目ID(NavId)查找其下的所有问题    	--通过ctime（创建时间）降序
	 * @param navid
	 * @param page
	 * @return
	 */
	public MyPage<Question> findByNavId(String navid, int page) {
		log.debug("select MyPage<Question> desc ctime");
		try {
		 DetachedCriteria dc = DetachedCriteria.forClass(Question.class);
		 dc.add(Property.forName("nav.id").eq(navid));
		 dc.addOrder(Order.desc("ctime"));
		 MyPage<Question> list = findPageByCriteria(dc,10,page);
		 log.debug("select MyPage<Question> desc ctime successful");
		 return list;
		} catch (RuntimeException re) {
			log.error("select MyPage<Question> desc ctime error",re);
			throw re;
		}
	}
	
	
	/**
	 * 记录浏览问题的用户
	 * @param question
	 */

	public void addState(Question question){
		getSession().update(question);
	}
	
	
	
	

	

	
	
	
	
	
	

}
