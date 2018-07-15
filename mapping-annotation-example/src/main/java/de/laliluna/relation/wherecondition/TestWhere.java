package de.laliluna.relation.wherecondition;

import de.laliluna.hibernate.InitSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Sebastian Hennebrueder
 *         Date: 07.03.2010
 */
public class TestWhere {
	public static void main(String[] args) {
		cleanUp();
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();


		Person person = new Person("Holger");
		session.save(person);
		Car firstCar = new Car("Peugeot");
		person.getCars().add(firstCar);
		Car secondCar = new Car("Citroen");
		person.getCars().add(secondCar);
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		firstCar.setDeleted(true);
		session.update(firstCar);
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.refresh(person);

		System.out.println("Cars of "+person.getName());
		for (Car c : person.getCars()) {
			System.out.println("--"+c);
		}
		session.getTransaction().commit();



		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(person);
		final Bike foo = new Bike("Foo");
		person.getBikes().add(foo);
		person.getBikes().add(new Bike("Bar"));
		session.flush();
		session.clear();

		// Simulate a legacy system which updates the join column (not possible with Hibernate)
		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				connection.createStatement().executeUpdate("update Person_Bike set deleted = true where bikes_id="+foo.getId());
			}
		});

		session.refresh(person);
		System.out.println("Person "+person);
		for (Bike b : person.getBikes()) {
			System.out.println(b);
		}


		session.getTransaction().commit();

	}

	private static void cleanUp() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
			session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				connection.createStatement().executeUpdate("delete from Person_Bike");
			}
		});
		session.createQuery("delete from Bike").executeUpdate();
		session.createQuery("delete from Car").executeUpdate();
		session.createQuery("delete from Person").executeUpdate();

		// Create deleted column, as it is not generated by Hibernate
		try {
			session.doWork(new Work() {
				public void execute(Connection connection) throws SQLException {
					connection.createStatement().execute("alter table Person_Bike add column deleted boolean DEFAULT false,");
				}
			});
		} catch (HibernateException e) {
			e.printStackTrace();
			// May fail if the column exists
		}
		session.getTransaction().commit();
	}
}
