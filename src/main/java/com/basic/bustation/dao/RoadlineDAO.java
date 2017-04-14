package com.basic.bustation.dao;

import com.basic.bustation.model.Roadline;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for
 * Roadline entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see
 * @author MyEclipse Persistence Tools
 */
@Repository("roadlineDAO")
public class RoadlineDAO extends BaseHibernateDAOImpl<Roadline> {
	private static final Logger log = LoggerFactory
			.getLogger(RoadlineDAO.class);
	// property constants
	public static final String NAME = "name";

	public void save(Roadline transientInstance) {
		log.debug("saving Roadline instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Roadline persistentInstance) {
		log.debug("deleting Roadline instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Roadline findById(Long id) {
		log.debug("getting Roadline instance with id: " + id);
		try {
			Roadline instance = (Roadline) getSession().get(
					"com.basic.bustation.model.Roadline", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Roadline instance) {
		log.debug("finding Roadline instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bustation.model.Roadline")
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
		log.debug("finding Roadline instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Roadline as model where model."
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
		log.debug("finding all Roadline instances");
		try {
			String queryString = "from Roadline";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Roadline merge(Roadline detachedInstance) {
		log.debug("merging Roadline instance");
		try {
			Roadline result = (Roadline) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Roadline instance) {
		log.debug("attaching dirty Roadline instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Roadline instance) {
		log.debug("attaching clean Roadline instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Roadline> queryJoinStartStationAndEndStation(String name, int page, int size){
		return getSession().createQuery("FROM Roadline p  WHERE p.name LIKE :name ORDER BY p.id")
				.setString("name","%"+name +"%")
				.setFirstResult((page-1)*size)
				.setMaxResults(size)
				.list();
	}

	public long getCount(String name) {
		// TODO 自动生成的方法存根
		return (long) getSession().createQuery("SELECT count(c) FROM Roadline c WHERE c.name LIKE :name")
				.setString("name","%"+name +"%")
				.uniqueResult();
	}
	
}
