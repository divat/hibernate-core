
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

	protected HibernateTemplate template;

	private Class clazz;

	public BasicDaoImp(SessionFactory factory, Class<T> clazz) {
		this.template = new HibernateTemplate(factory);
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T findById(final ID id) {
		return (T) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.get(clazz, id);
			}
		});

	}

	/**
     * reattaches a object from an older session to the current session. Uses
     * LockMode.Read to verifiy if the object does exist.
     * 
     * @param object
     */
	public void reattach(T object) {
		template.lock(object, LockMode.NONE);
	}

	public void save(T object) {
		template.saveOrUpdate(object);
	}

	public void update(T object) {
		template.update(object);
	}

	public T merge(T object) {
		return (T) template.merge(object);
	}

	public void saveOrUpdate(T object) {
		template.saveOrUpdate(object);
	}

	public void delete(final T object) {
		template.delete(object);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(clazz).list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final Criterion... criterion) {
		return (List<T>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				for (Criterion c : criterion)
					criteria.add(c);
				return criteria.list();
			}
		});
	}

}
