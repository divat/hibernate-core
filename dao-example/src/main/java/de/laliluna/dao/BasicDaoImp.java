
package de.laliluna.dao;


import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BasicDaoImp<T>  implements BasicDao<T>{

	private SessionFactory factory;

    private Class<T> type;
	
	public BasicDaoImp(SessionFactory factory, Class<T> type) {
		this.factory = factory;
        this.type = type;
	}
	
	protected  Session getSession() {
		return factory.getCurrentSession();
	}

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public List findAll() {
        return  getSession().createCriteria(type).list();
    }


    public T findById(Integer id) {
        return (T) getSession().get(type, id);
    }

    public void reattach(T entity) {
        getSession().buildLockRequest(LockOptions.READ).lock(entity);
    }

    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void update(T entity) {
        getSession().update(entity);

    }
	
}
