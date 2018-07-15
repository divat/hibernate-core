
package de.laliluna.relation.typed;

import java.util.Iterator;
import java.util.List;


import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;


public class TestTypedRelation  {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestTypedRelation.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		clean();
		create();

		query();

	}

	private static void clean() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		
		session.beginTransaction();

		session.createQuery("delete from TutorialReader").executeUpdate();

		session.getTransaction().commit();
		
	}

	private static void query() {
		log.debug("query");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		List list = null;
		session.beginTransaction();

		log.debug("select all billing addresses");
		list = session.createQuery("from BillingAddress").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ReaderAddress element = (ReaderAddress) iter.next();
			log.debug("{}", element);
		}

		log.debug("select tutorial reader with billing address in Frankfurt");
		list = session.createQuery("from TutorialReader r where r.billingAddress.address='Alte Landstrasse'")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TutorialReader reader = (TutorialReader) iter.next();
			log.debug("{}", reader);
			log.debug("-"+reader.getBillingAddress());
			log.debug("-"+reader.getDeliveryAddress());
		}
		log.debug("select all tutorial reader ");
		list = session.createQuery("from TutorialReader")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TutorialReader reader = (TutorialReader) iter.next();
			log.debug("{}", reader);
			log.debug("-"+reader.getBillingAddress());
			log.debug("-"+reader.getDeliveryAddress());
		}
		session.getTransaction().commit();

	}

	private static void create() {
		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		TutorialReader reader = new TutorialReader();
		reader.setName("Sebastian");
		session.save(reader);
		BillingAddress billing = new BillingAddress(null, "Alte Landstrasse",
				"Frankfurt");
		DeliveryAddress delivery = new DeliveryAddress(null, "Neue Landstrasse",
				"Frankfurt");
		reader.setBillingAddress(billing);
		reader.setDeliveryAddress(delivery);
		session.getTransaction().commit();

		
		
	}

}
