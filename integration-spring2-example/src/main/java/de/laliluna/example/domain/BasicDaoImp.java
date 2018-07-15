
package de.laliluna.example.domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BasicDaoImp<T, ID extends Serializable> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BasicDaoImp.class);

	protected SessionFactory factory;

	private Class clazz;

	public BasicDaoImp(SessionFactory factory, Class<T> clazz) {
		this.factory = factory;
		this.clazz = clazz;
	}

	protected Session getCurrentSession() {
		return factory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T findById(final ID id) {
		return (T) getCurrentSession().get(clazz, id);

	}

	/**
     * reattaches a object from an older session to the current session. Uses
     * LockMode.Read to verifiy if the object does exist.
     * 
     * @param object
     */
	public void reattach(T object) {
		getCurrentSession().lock(object, LockMode.NONE);
	}

	public void save(T object) {
		getCurrentSession().saveOrUpdate(object);
	}

	public void update(T object) {
		getCurrentSession().update(object);
	}

	public T merge(T object) {
		return (T) getCurrentSession().merge(object);
	}

	public void saveOrUpdate(T object) {
		getCurrentSession().saveOrUpdate(object);
	}

	public void delete(T object) {
		getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) getCurrentSession().createCriteria(clazz).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criterion... criterion) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		for (Criterion c : criterion)
			criteria.add(c);
		return criteria.list();
	}

}
