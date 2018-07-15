
package de.laliluna.dao;

import de.laliluna.hibernate.InitSessionFactory;

import java.util.HashMap;
import java.util.Map;


public class DaoFactory {

    static Map<Class, Object> daoMap = new HashMap<>();

    static {
        daoMap.put(ArticleDao.class, new ArticleDaoImp(InitSessionFactory.getInstance()));
        daoMap.put(OrderDao.class, new OrderDaoImp(InitSessionFactory.getInstance()));
    }
	

	private DaoFactory() {
		
	}

	public static <T> T getDao(Class<T> daoInterface){
        return (T) daoMap.get(daoInterface);
    }


}
