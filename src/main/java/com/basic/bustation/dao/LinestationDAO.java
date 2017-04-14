package com.basic.bustation.dao;

import com.basic.bustation.model.Linestation;
import com.basic.bustation.model.Roadline;
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
 * Linestation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.bus.model.Linestation
 * @author MyEclipse Persistence Tools
 */

@Repository("linestationDAO")
public class LinestationDAO extends BaseHibernateDAOImpl<Linestation> {
	private static final Logger log = LoggerFactory
			.getLogger(LinestationDAO.class);
	// property constants
	public static final String SN = "sn";

	public void save(Linestation transientInstance) {
		log.debug("saving Linestation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Linestation persistentInstance) {
		log.debug("deleting Linestation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Linestation findById(Long id) {
		log.debug("getting Linestation instance with id: " + id);
		try {
			Linestation instance = (Linestation) getSession().get(
					"com.basic.bustation.model.Linestation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Linestation instance) {
		log.debug("finding Linestation instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bustation.model.Linestation")
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
		log.debug("finding Linestation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Linestation as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySn(Object sn) {
		return findByProperty(SN, sn);
	}

	public List findAll() {
		log.debug("finding all Linestation instances");
		try {
			String queryString = "from Linestation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Linestation merge(Linestation detachedInstance) {
		log.debug("merging Linestation instance");
		try {
			Linestation result = (Linestation) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Linestation instance) {
		log.debug("attaching dirty Linestation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Linestation instance) {
		log.debug("attaching clean Linestation instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Linestation findByRoadlineAndStation(Roadline road, Roadstation station){
			return (Linestation) getSession().createQuery("FROM Linestation p  WHERE p.roadline =:road and p.roadstation =:station")
					.setEntity("road", road)
					.setEntity("station", station)
					.list().get(0);
	}
}
