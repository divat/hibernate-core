package de.laliluna.example.domain;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class HedgehogDao extends BasicDaoImp<Hedgehog, Integer> {

	public HedgehogDao(SessionFactory factory) {
		super(factory, Hedgehog.class);
	}

	/**
     * Special finder should be placed here and not in the BasicDaoImp. This
     * example shows to reuse the findByCriteria method. It finds Hedgehog by
     * the given address.
     * 
     * @param address
     * @return
     */
	public Hedgehog findByWinterAddress(WinterAddress address) {
		List<Hedgehog> list = findByCriteria(Restrictions.eq("addresses", address));
		// relation is one to many, so we will get either no or one hedgehog
		if (list.size() == 0)
			return null;
		else
			return list.get(0);
	}

	/**
     * Special finder should be placed here and not in the BasicDaoImp. This
     * example finds Hedgehog by the given Name of the address.
     * 
     * @param address
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Hedgehog> findByWinterAddressName(final String addressName) {
		return (List<Hedgehog>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(Hedgehog.class).createAlias(
						"addresses", "a").add(
						Restrictions.eq("a.name", addressName)).list();
			}
		});
	}

}
