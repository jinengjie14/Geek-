package com.openkx.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkx.utils.MyPage;

public class BaseDao<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public MyPage<T> findPageByCriteria(final DetachedCriteria detachedCriteria) {
        return findPageByCriteria(detachedCriteria, MyPage.PAGESIZE, 0);
    }

    public MyPage<T> findPageByCriteria(final DetachedCriteria detachedCriteria, int page) {
        return findPageByCriteria(detachedCriteria, MyPage.PAGESIZE, page);
    }

    public MyPage<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, int page) {
        // final int startIndex = (page - 1) * pageSize;
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = 0;
        try {
            totalCount = (Long) object;
        } catch (Exception e) {
        }
        MyPage<T> ps = new MyPage<>((int) totalCount, pageSize, page);
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> items = criteria.setFirstResult(ps.getStartindex()).setMaxResults(pageSize).list();
        // MyPage<T> ps = new MyPage<T>(items, (int) totalCount, pageSize,
        // startIndex);
        ps.setItems(items);
        return ps;
    }

    public MyPage<T> findPageByCriteria(Session session, final DetachedCriteria detachedCriteria, final int pageSize,
                                        int page) {
        // final int startIndex = (page - 1) * pageSize;
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = 0;
        try {
            totalCount = (Long) object;
        } catch (Exception e) {
        }
        MyPage<T> ps = new MyPage<>((int) totalCount, pageSize, page);
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> items = criteria.setFirstResult(ps.getStartindex()).setMaxResults(pageSize).list();
        // MyPage<T> ps = new MyPage<T>(items, (int) totalCount, pageSize,
        // startIndex);
        ps.setItems(items);
        return ps;
    }

    public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        return criteria.list();
    }

    public List<T> findAllByCriteria(Session session, final DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        return criteria.list();
    }

    public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = 0;
        try {
            totalCount = (Long) object;
        } catch (Exception e) {
        }
        return (int) totalCount;
    }

    /**
     * 新建
     *
     * @param t
     */
    public void save(T t) {
        log.debug("saving T instance");
        try {
            getSession().save(t);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    /**
     * 更新
     *
     * @param t
     * @return T
     */
    public T merge(T t) {
        log.debug("merging T instance");
        try {
            T result = (T) getSession().merge(t);
            getSession().flush();// 立即提交，避免新建社群递归不到整个树
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /**
     * 删除
     *
     * @param t
     */
    public void delete(String id, Class<T> arg) {
        log.debug("deleting T instance");
        try {
        	T t = findById(id, arg);
    		getSession().delete(t);
    		log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /**
     * FindById
     *
     * @param id
     * @param arg
     * @return T
     */
    public T findById(String id, Class<T> arg) {
        log.debug("getting T instance with id: " + id);
        try {
            T instance = (T) getSession().get(arg, id);
            log.debug("get T successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * FindByUser
     *
     * @param userId
     * @return List<T>
     */
    public List<T> findByUser(String userId, String... status) {
        log.debug("findByUser T instance with userId: " + userId);
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(getClass());
            dc.add(Property.forName("userId").eq(userId));
            addStatus(dc, status);
            List<T> list = findAllByCriteria(dc);
            return list;
        } catch (RuntimeException re) {
            log.error("findByUser failed", re);
            throw re;
        }
    }

    /**
     * 添加匹配 status 字段条件
     * @param dc
     * @param status
     */
    public void addStatus(DetachedCriteria dc, String... status) {
        if (null != status && status.length > 0) {
            Disjunction disjunction = Restrictions.disjunction();
            for (String s : status) {
                disjunction.add(Property.forName("status").like(s, MatchMode.ANYWHERE));
            }
            dc.add(disjunction);
        }
    }
}