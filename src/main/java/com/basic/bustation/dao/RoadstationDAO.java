package com.basic.bustation.dao;

import com.basic.bustation.model.Roadstation;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for
 * Roadstation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com
 * @author MyEclipse Persistence Tools
 */

@Repository("roadstationDAO")
public class RoadstationDAO extends BaseHibernateDAOImpl<Roadstation> {
	private static final Logger log = LoggerFactory
			.getLogger(RoadstationDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DEMO = "demo";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String STAYTIME = "staytime";

	public void save(Roadstation transientInstance) {
		log.debug("saving Roadstation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Roadstation persistentInstance) {
		log.debug("deleting Roadstation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Roadstation findById(Long id) {
		log.debug("getting Roadstation instance with id: " + id);
		try {
			Roadstation instance = (Roadstation) getSession().get(
					"com.basic.bustation.model.Roadstation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Roadstation instance) {
		log.debug("finding Roadstation instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bustation.model.Roadstation")
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
		log.debug("finding Roadstation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Roadstation as model where model."
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

	public List findByDemo(Object demo) {
		return findByProperty(DEMO, demo);
	}

	public List findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	public List findByStaytime(Object staytime) {
		return findByProperty(STAYTIME, staytime);
	}

	public List findAll() {
		log.debug("finding all Roadstation instances");
		try {
			String queryString = "from Roadstation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Roadstation merge(Roadstation detachedInstance) {
		log.debug("merging Roadstation instance");
		try {
			Roadstation result = (Roadstation) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Roadstation instance) {
		log.debug("attaching dirty Roadstation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Roadstation instance) {
		log.debug("attaching clean Roadstation instance");
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
		return (long) getSession().createQuery("SELECT count(c) FROM Roadstation c WHERE c.name LIKE :name")
				.setString("name",name)
				.uniqueResult();
	}
	
}
