package com.openkx.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.openkx.domain.Answer;
import com.openkx.domain.Comment;
import com.openkx.domain.Question;


@Transactional
@Component
public class AnswerDao extends BaseDao<Answer> {
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	/**
	 * 获取qestion_id下面所有答案，按时间排序
	 * @param question_id
	 * @return
	 */
	public List<Answer> findAll_ctime(String question_id){
		log.debug("select List<Answer> By QuestionID");
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Answer> query = builder.createQuery(Answer.class);
			Root<Answer> root = query.from(Answer.class);
			query.where(builder.equal(root.get("question_id"), question_id));
			query.orderBy(builder.desc(root.get("ctime")));
			List<Answer> list  = entityManager.createQuery(query).getResultList();
			log.debug("select List<Answer> By QuestionID successful");
			return list;
		} catch (RuntimeException re) {
			log.error("select List<Answer> By QuestionID error");
			throw re;
		}
	}
	
	/**
	 * 获取qestion_id下面所有答案，按useful排序
	 * @param question_id
	 * @return
	 */
	public List<Answer> findAll(String question_id){
		log.debug("select List<Answer> By QuestionID desc useful");
		try{
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Answer> query = builder.createQuery(Answer.class);
			Root<Answer> root = query.from(Answer.class);
			query.where(builder.equal(root.get("question_id"), question_id));
			query.orderBy(builder.desc(root.get("useful")));
			List list  = entityManager.createQuery(query).getResultList();
			log.debug("select List<Answer> By QuestionID desc useful successful");
			return list;
		} catch (RuntimeException re) {
			log.error("select List<Answer> By QuestionID desc useful error");
			throw re;
		}
	}
	
	public Answer findById(String id) {
			return findById(id, Answer.class);
	}
	
	
	public void save(Answer answer){
		log.debug("save Answer");
		try {
			getSession().save(answer);
		} catch (RuntimeException re) {
			log.error("save Answer ", re);
	         throw re;
		}
		
	}
	
}
