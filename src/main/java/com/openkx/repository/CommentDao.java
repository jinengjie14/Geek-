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
public class CommentDao extends BaseDao<Comment>{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	/**
	 * 获取qestion_id下面所有答案，按时间排序
	 * @param question_id
	 * @return
	 */
	public List<Comment> findAll_ctime(String question_id){
		log.debug("select Comment desc ctime");
        try {
        	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    		CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
    		Root<Comment> root = query.from(Comment.class);
    		query.where(builder.equal(root.get("question_id"), question_id));
    		query.orderBy(builder.desc(root.get("ctime")));
    		List<Comment> list  = entityManager.createQuery(query).getResultList();
    		log.debug("select Comment successful");
    		return list;
        } catch (RuntimeException re) {
            log.error("select Comment desc ctime", re);
            throw re;
        }
	}
	
	/**
	 * 获取qestion_id下面所有答案，按useful排序
	 * @param question_id
	 * @return
	 */
	public List<Comment> findAll(String question_id){
		log.debug("select Comment desc userful");
		try{
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
			Root<Comment> root = query.from(Comment.class);
			query.where(builder.equal(root.get("question_id"), question_id));
			query.orderBy(builder.desc(root.get("useful")));
			List list  = entityManager.createQuery(query).getResultList();
			log.debug("select Comment successful");
			return list;
		 } catch (RuntimeException re) {
	         log.error("select Comment desc userful", re);
	         throw re;
	     }
	}
	public void save(Comment comment){
		log.debug("save Comment");
		try {
			getSession().save(comment);
		} catch (RuntimeException re) {
			log.error("save Comment ", re);
	         throw re;
		}
		
	}
	
	
}
