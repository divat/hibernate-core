package de.laliluna.primarykey.complex;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.laliluna.hibernate.InitSessionFactory;

public class TestTurtles {

    public static void main (String args[]){
        TestTurtles testTurtles = new TestTurtles();
        testTurtles.testBoxTurtle();
        testTurtles.testSpottedTurtle();
    }

    public void testBoxTurtle() {
		Transaction tx = null;
		try {
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();

			tx = session.beginTransaction();
			/* delete all turtles first */
			session.createQuery("delete from BoxTurtle").executeUpdate();
			session.flush();

			/* create a box turtle */
			BoxTurtleId boxTurtleId = new BoxTurtleId("Bad Vilbel",
					"Roman salad");
			BoxTurtle boxTurtle = new BoxTurtle();
			boxTurtle.setId(boxTurtleId);
			boxTurtle.setName("Sebastian");
			session.save(boxTurtle);

			tx.commit();

			/* get a box turtle from db */
			session = InitSessionFactory.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			boxTurtleId = new BoxTurtleId("Bad Vilbel", "Roman salad");
			BoxTurtle boxTurtleReloaded = (BoxTurtle) session.get(
					BoxTurtle.class, boxTurtleId);
            System.out.println(boxTurtleReloaded.getName());

			tx.commit();

			/* find a box turtle */
			session = InitSessionFactory.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			List<BoxTurtle> turtles = session.createQuery(
					"from BoxTurtle b where b.id.favouriteSalad = :salad")
					.setString("salad", "Roman salad").list();
            System.out.println(turtles.size());
			
			
			tx.commit();

		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}

	}

	public void testSpottedTurtle() {
		Transaction tx = null;
		try {
			/* create a box turtle */
			Session session = InitSessionFactory.getInstance()
					.getCurrentSession();
			tx = session.beginTransaction();
			/* delete all turtles first */
			session.createQuery("delete from SpottedTurtle").executeUpdate();
			session.flush();

			SpottedTurtle spottedTurtle = new SpottedTurtle("Leipzig",
					"Greek salad", "Daniel");
			session.save(spottedTurtle);

			tx.commit();

			/* get a box turtle from db */
			session = InitSessionFactory.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			SpottedTurtleId spottedTurtleId = new SpottedTurtleId("Leipzig",
					"Greek salad");
			SpottedTurtle spottedTurtleReloaded = (SpottedTurtle) session.get(
					SpottedTurtle.class, spottedTurtleId);
			 System.out.println(spottedTurtleReloaded.getName());

			tx.commit();
			
			/* find a spotted turtle */
			session = InitSessionFactory.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			List<SpottedTurtle> turtles = session.createQuery(
					"from SpottedTurtle b where b.favouriteSalad = :salad")
					.setString("salad", "Roman salad").list();
			 System.out.println( turtles.size());
			tx.commit();
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw e;
		}

	}
}
