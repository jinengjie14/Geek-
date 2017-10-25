package com.openkx.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.openkx.domain.Nav;
import com.openkx.domain.User;


@Repository
@Transactional
public class NavDao extends BaseDao<Nav>{
	
	/**
	 * 查找所有栏目（Nav）
	 * @return
	 */
	public List<Nav> FindAll(){
	    DetachedCriteria dc = DetachedCriteria.forClass(Nav.class);
	    return findAllByCriteria(dc);
	}
	
	public void save(Nav nav){
		log.debug("save Comment");
		try {
			getSession().save(nav);
		} catch (RuntimeException re) {
			log.error("save Comment ", re);
	         throw re;
		}
		
	}
	
}
