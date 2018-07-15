package de.laliluna.relation.overview;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import de.laliluna.hibernate.InitSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExample1 {

	private static Logger log = LoggerFactory.getLogger(TestExample1.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testCreateLovedCountry();
		testCreateComputer();
		testCreateHobby();
		testCreateFavouriteNumber();
		testCreateDevelopmentLanguage();
		testCreateSport();
		testCreateJuneBeetle();
		testCreateDream();
		testCreateIdea();
		
	}

	private static void testCreateIdea() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		Idea idea = new Idea("Really nice one");
		d.getIdeas().add(idea);
		session.save(d);
		tx.commit();
		
	}

	private static void testCreateJuneBeetle() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		JuneBeetle beetles[] = new JuneBeetle[1];
		beetles[0] = new JuneBeetle();
		beetles[0].setName("Edgar");
		d.setJuneBeetles(beetles);
		session.save(d);
		tx.commit();

	}

	private static void testCreateDream() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		Dream dream = new Dream();
		dream.setName("a good one");
		d.getDreams().add(dream);
		dream = new Dream();
		dream.setName("nightmare");
		d.getDreams().add(dream);
		session.save(d);
		tx.commit();

	}

	private static void testCreateSport() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		Sport sport = new Sport();
		sport.setName("Swimming");
		d.getSports().add(sport);
		sport = new Sport();
		d.getSports().add(sport);
		sport = new Sport();
		sport.setName("Football");
		d.getSports().add(sport);
		session.save(d);
		tx.commit();
		/* output will be sorted by name */
		for (Iterator iter = d.getSports().iterator(); iter.hasNext();)
			System.out.println(iter.next());

	}

	private static void testCreateHobby() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		Hobby hobby = new Hobby();
		hobby.setName("Jogging");
		d.getHobbies().add(hobby);
		session.save(d);
		tx.commit();

	}

	private static void testCreateFavouriteNumber() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		int numbers[] = new int[1];
		numbers[0] = 111;
		d.setFavouriteNumbers(numbers);
		session.save(d);
		tx.commit();

	}

	private static void testCreateLovedCountry() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Developer d = new Developer();

		LovedCountry lovedCountry = new LovedCountry();
		lovedCountry.setIsocode("de");
		lovedCountry.setName("Germany");
		d.getLovedCountries().put(lovedCountry.getIsocode(), lovedCountry);
		session.save(d);
		session.getTransaction().commit();
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.get(Developer.class, d.getId());
		for (Iterator iter = d.getLovedCountries().keySet().iterator(); iter
				.hasNext();)
		{
			String key = (String) iter.next();
			log.debug("{}", d.getLovedCountries().get(key));
		}
		session.getTransaction().commit();

	}

	private static void testCreateDevelopmentLanguage() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();

		d.getDevelopmentLanguages().put("java", "Java language from sun.com");
		session.save(d);
		tx.commit();

	}

	private static void testCreateComputer() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Developer d = new Developer();
		Computer c1 = new Computer();
		c1.setName("Dual Power");
		Computer c2 = new Computer();
		c2.setName("Notebook");
		d.getComputers().add(c1);
		d.getComputers().add(c2);

		session.persist(d);
		tx.commit();
		
		

	}

}
