
package de.laliluna.inheritance.tableperclass;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;


public class TestTableperClass {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestTableperClass.class);
	public static void main(String[] args) {
		MusicConsumer musicFan = create();
		update(musicFan);
		query();

	}

	private static void query() {
		log.debug("query");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		List list = null;
		session.beginTransaction();

		log.debug("select all SoftrockGroup");
		list = session.createQuery("from SoftrockGroup").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			SoftrockGroup element = (SoftrockGroup) iter.next();
			log.debug("{}", element);
		}

		log.debug("select all MusicBands");
		list = session.createQuery("from MusicBand").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MusicBand element = (MusicBand) iter.next();
			log.debug("{}", element);
		}

		log.debug("select all MusicBands beeing SoftrockGroup");
		list = session.createQuery(
				"from MusicBand mg where mg.class = SoftrockGroup").list();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MusicBand element = (MusicBand) iter.next();
			log.debug("{}", element);
		}

		log
				.debug("select all SoftrockGroup having destroyed more than 150 guitars");
		/*
		 * we do not need to express that we are only looking for SoftrockGroup
		 * mg.class = SoftrockGroup is optional but it makes the query
		 * considerably faster.
		 */
		list = session
				.createQuery(
						"from MusicBand mg where  mg.class = SoftrockGroup and mg.destroyedGuitars>150")
				.list();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MusicBand element = (MusicBand) iter.next();
			log.debug("{}", element);
		}

		/*
		 * select all musicFans of hardrockgroups having destroyed more than 150
		 */
		log.debug("select all  musicFans of softrockgroups");
		list = session
				.createQuery(
						"select mf from MusicConsumer mf left join mf.musicGroups mg where mg.class = SoftrockGroup and mg.destroyedGuitars > 150)")
				.list();
		System.exit(0);
		for (Iterator iter = list.iterator(); iter.hasNext();) {

			log.debug("{}", iter.next());
		}

		session.getTransaction().commit();

	}

	private static void update(MusicConsumer musicFan) {
		log.debug("update");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we expect the session to be closed so reattach internetshop to move
		// it to persistent status
		session.buildLockRequest(LockOptions.NONE).lock(musicFan);
		// we stop offering cash payment in this shop

		session.getTransaction().commit();
	}

	private static MusicConsumer create() {
		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		MusicConsumer musicFan = new MusicConsumer("Edwin");
		SoftrockGroup softrockGroup = new SoftrockGroup();
		softrockGroup.setName("very brutal");
		softrockGroup.setDestroyedGuitars(200);
		musicFan.getMusicGroups().add(softrockGroup);
		GirlGroup girlGroup = new GirlGroup();
		girlGroup.setName("nice guys");
		girlGroup.setCryingGroupies(true);
		musicFan.getMusicGroups().add(girlGroup);
		MusicBand musicGroup = new MusicBand("Classic Rock");
		musicFan.getMusicGroups().add(musicGroup);
		
		// save the payment methods first, because the shop needs the primary
		// keys
		session.save(girlGroup);
		session.save(softrockGroup);
		session.save(musicGroup);
		session.save(musicFan);
		session.getTransaction().commit();
		return musicFan;
	}

}
