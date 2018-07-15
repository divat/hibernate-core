
package de.laliluna.primarykey.simple;



import org.hibernate.Session;
import org.hibernate.Transaction;

import de.laliluna.hibernate.InitSessionFactory;

/**
 * @author hennebrueder
 */
public class TestWildCats {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestWildCats.class);

	public static void main(String[] args) {
		testLion();
		testGeneric();
		testCheetah();
		testPuma();
		testTiger();
	}
	public static void testLion() {
		Transaction tx = null;
		try {
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();

			tx = session.beginTransaction();
			/* delete all wildcats first */
			session.createQuery("delete from Lion").executeUpdate();
			session.flush();

			/* create a lion */
			// we must the primary key on hour own.
			Integer id = 4711; // a well chosen id ;-)
			Lion lion = new Lion(id, "Willi");

			session.save(lion);

			tx.commit();

		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}

	public static void testPuma() {
		Transaction tx = null;
		try {
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();

			tx = session.beginTransaction();
			/* delete all wildcats first */
			session.createQuery("delete from Puma").executeUpdate();
			session.flush();

			/* create a lion */
			Puma puma = new Puma("Marcel");

			session.save(puma);

			tx.commit();
			// check that the id is generated
			System.out.println(puma.getId());

		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}

	public static void testTiger() {
		Transaction tx = null;
		try {
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();

			tx = session.beginTransaction();
			/* delete all wildcats first */
			session.createQuery("delete from Tiger").executeUpdate();
			session.flush();

			/* create a Tiger */
			Tiger tiger = new Tiger("Brigitte");
			session.save(tiger);
			tx.commit();
			// check that the id is generated
			System.out.println(tiger.getId());

		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}

	public static void testCheetah() {
		Transaction tx = null;
		try {
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();

			tx = session.beginTransaction();
			/* delete all wildcats first */
			session.createQuery("delete from Cheetah").executeUpdate();
			session.flush();

			/* create a Cheetah */
			Cheetah cheetah = new Cheetah("Kristina");
			session.save(cheetah);
			tx.commit();
			// check that the id is generated
			System.out.println(cheetah.getId());

		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}
	
	public static void testGeneric(){
		Transaction tx = null;
		try {
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();

			tx = session.beginTransaction();
			/* delete all wildcats first */
			session.createQuery("delete from Generic").executeUpdate();
			session.flush();

			/* create a Cheetah */
			Generic generic = new Generic("some generic");
			session.save(generic);
			tx.commit();
			// check that the id is generated
			System.out.println(generic.getId());

		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}
}
