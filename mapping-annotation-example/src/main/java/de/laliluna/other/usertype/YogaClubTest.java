package de.laliluna.other.usertype;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.laliluna.hibernate.InitSessionFactory;

public class YogaClubTest {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YogaClubTest.class);

	/**
     * @param args
     */
	public static void main(String[] args) {

		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		YogaClub c1 = new YogaClub("WonderFulYoga");
		session.save(c1);
		YogaClub c2 = new YogaClub("WonderFulyoga");
		session.save(c2);
		
		Integer id = c1.getId();
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		YogaClub reloaded = (YogaClub) session.get(YogaClub.class, id);
		log.debug("{}", reloaded.getName());

		List<YogaClub> list = session.createCriteria(YogaClub.class).add(
				Restrictions.eq("name", "wonderfulyoga")).list();
		log.debug("Found entries for wonderfulyoga: " + list.size());
		
		list = session.createCriteria(YogaClub.class).add(
				Restrictions.eq("name", "WonderfulYoga")).list();
		log.debug("Found entries for WonderfulYoga: " + list.size());
		session.getTransaction().commit();

	}

}
