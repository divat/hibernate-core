/**
 * Test application for example 
 * @author Sebastian Hennebrueder
 * created Jan 16, 2006
 * copyright 2007 by http://www.laliluna.de
 */

package de.laliluna.example;

import de.laliluna.hibernate.InitSessionFactory;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class TestExample {

	private static Logger log = LoggerFactory.getLogger(TestExample.class);

	public static void main(String[] args) {
		/* clean tables */
		clean();

		/* simple create example */
		createHoney();

		/* relation example */
		createRelation();

		/* delete example */
		delete();
		/* update example */
		update();

		/* query example */
		query();

		/* show how to initialize data */
		initBees();
	}

	/**
	 * Creates a honey object
	 *
	 * @return a fresh created Honey
	 */
	private static Honey createHoney() {
		Honey forestHoney = new Honey();
		forestHoney.setName("forest honey");
		forestHoney.setTaste("very sweet");

		Session session = InitSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(forestHoney);
		tx.commit();
		session.close();

		return forestHoney;
	}

	/**
	 * creates and updates a honey
	 */
	private static void update() {
		Honey honey = createHoney();

		Session session = InitSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		honey.setName("Modern style");
		session.update(honey);
		tx.commit();
		session.close();
	}

	/**
	 * creates a honey and deletes it afterwards
	 */
	private static void delete() {
		Honey honey = createHoney();

		Session session = InitSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(honey);
		tx.commit();
		session.close();
	}

	/**
	 * deletes all bees and honeys.
	 */
	private static void clean() {
		Session session = InitSessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		session.createQuery("delete from Bee").executeUpdate();
		session.createQuery("delete from Honey").executeUpdate();
		tx.commit();
		session.close();
	}

	/**
	 * create a Honey and a Bee and a relation beween them
	 */
	private static void createRelation() {
		Session session = InitSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Honey honey = new Honey();
		honey.setName("country honey");
		honey.setTaste("Delicious");
		session.save(honey);
		Bee bee = new Bee("Sebastian");
		session.save(bee);

		/* create the relation on both sides */
		bee.setHoney(honey);
		honey.getBees().add(bee);
		tx.commit();
		session.close();
	}

	/**
	 * Find and list Honey entries in the database
	 */
	private static void query() {
		Session session = InitSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List honeys = session.createQuery("select h from Honey as h").list();
		for (Iterator iter = honeys.iterator(); iter.hasNext();) {
			Honey element = (Honey) iter.next();
			log.debug("{}", element);
		}
		tx.commit();
		session.close();
	}

	/**
	 * load honey and init bees
	 */
	private static void initBees() {
		Session session = InitSessionFactory.openSession();
		// appraoch a) generates 1 additional query for each honey
		Transaction tx = session.beginTransaction();
		List honeys = session.createQuery("select h from Honey as h").list();
		for (Iterator iter = honeys.iterator(); iter.hasNext();) {
			Honey element = (Honey) iter.next();
            log.debug("{}", element);
			Hibernate.initialize(element.getBees());
		}

		// appraoch b) generates 1 query with a join
		honeys = session.createQuery(
			"select h from Honey as h left join fetch h.bees")
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.createCriteria(Honey.class)
			.setFetchMode("bees", FetchMode.JOIN).setResultTransformer(
			Criteria.DISTINCT_ROOT_ENTITY).list();

		tx.commit();
		session.close();

	}


}
