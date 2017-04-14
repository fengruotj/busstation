package com.basic.bustation.dao;

import com.basic.bustation.model.Roadsection;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for
 * Roadsection entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.bus.model.Roadsection
 * @author MyEclipse Persistence Tools
 */

@Repository("roadsectionDAO")
public class RoadsectionDAO extends BaseHibernateDAOImpl<Roadsection> {
	private static final Logger log = LoggerFactory
			.getLogger(RoadsectionDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ELAPSEDTIME = "elapsedtime";

	public void save(Roadsection transientInstance) {
		log.debug("saving Roadsection instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Roadsection persistentInstance) {
		log.debug("deleting Roadsection instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Roadsection findById(Long id) {
		log.debug("getting Roadsection instance with id: " + id);
		try {
			Roadsection instance = (Roadsection) getSession().get(
					"com.basic.bustation.model.Roadsection", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Roadsection instance) {
		log.debug("finding Roadsection instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bustation.model.Roadsection")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Roadsection instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Roadsection as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByElapsedtime(Object elapsedtime) {
		return findByProperty(ELAPSEDTIME, elapsedtime);
	}

	public List findAll() {
		log.debug("finding all Roadsection instances");
		try {
			String queryString = "from Roadsection";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Roadsection merge(Roadsection detachedInstance) {
		log.debug("merging Roadsection instance");
		try {
			Roadsection result = (Roadsection) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Roadsection instance) {
		log.debug("attaching dirty Roadsection instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Roadsection instance) {
		log.debug("attaching clean Roadsection instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public long getCount(String name) {
		// TODO 自动生成的方法存根
		return (long) getSession().createQuery("SELECT count(c) FROM Roadsection c WHERE c.name LIKE :name")
				.setString("name","%"+name +"%")
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Roadsection> queryWithPageAndSize(String name, int page, int size){
		return getSession().createQuery("FROM Roadsection s WHERE s.name LIKE :name ORDER BY s.id")
				.setString("name","%"+name +"%")
				.setFirstResult((page-1)*size)
				.setMaxResults(size)
				.list();
	}
}
