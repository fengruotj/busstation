package com.basic.bustation.dao;

import com.basic.bustation.model.Roadstation;
import com.basic.bustation.model.Stationtoline;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stationtolineDAO")
public class StationtolineDAO extends BaseHibernateDAOImpl<StationtolineDAO> {
	private static final Logger log = LoggerFactory
			.getLogger(StationtolineDAO.class);
	// property constants
	public static final String NAME = "name";

	public void save(Stationtoline transientInstance) {
		log.debug("saving Stationtoline instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Stationtoline persistentInstance) {
		log.debug("deleting Stationtoline instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Stationtoline findById(Long id) {
		log.debug("getting Stationtoline instance with id: " + id);
		try {
			Stationtoline instance = (Stationtoline) getSession().get(
					"com.basic.bustation.model.Stationtoline", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Stationtoline instance) {
		log.debug("finding Stationtoline instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bustation.model.Stationtoline")
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
		log.debug("finding Stationtoline instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Stationtoline as model where model."
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

	public List findAll() {
		log.debug("finding all Stationtoline instances");
		try {
			String queryString = "from Stationtoline";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Stationtoline merge(Stationtoline detachedInstance) {
		log.debug("merging Stationtoline instance");
		try {
			Stationtoline result = (Stationtoline) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Stationtoline instance) {
		log.debug("attaching dirty Stationtoline instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Stationtoline instance) {
		log.debug("attaching clean Stationtoline instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Stationtoline> findBystation(Roadstation station){
		 return getSession().createQuery("FROM Stationtoline p WHERE p.roadstation =:station ORDER BY p.id")
					.setEntity("station", station)
					.list();
	}
}
