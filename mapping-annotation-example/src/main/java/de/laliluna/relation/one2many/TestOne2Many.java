
package de.laliluna.relation.one2many;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

import de.laliluna.hibernate.InitSessionFactory;


public class TestOne2Many {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestOne2Many.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JavaClubMember1 clubMember1 = create1();
		delete1(clubMember1);
		query1();
		cleanUp1();
		JavaClubMember2 clubMember2 = create2();
		delete2(clubMember2);
		query2();
		cleanUp2();

		JavaClubMember3 clubMember3 = create3();
		delete3(clubMember3);
		cleanUp3();
		
		JavaClubMember4 clubMember4 = create4();
		delete4(clubMember4);
		cleanUp4();

	}

	private static void cleanUp1() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from JavaClubMember1").executeUpdate();
		session.createQuery("delete from JavaClub1").executeUpdate();
		session.getTransaction().commit();
	}

	private static void query1() {
		log.debug("query1");
		log.debug("just create some entries");
		Random random = new Random();
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		for (int i = 0; i < 20; i++) {
			JavaClub1 club1 = new JavaClub1("name" + random.nextInt(30));
			JavaClubMember1 clubMember1 = new JavaClubMember1("name"
					+ random.nextInt(30));
			JavaClubMember1 clubMember2 = new JavaClubMember1("name"
					+ random.nextInt(30));
			club1.getMembers().add(clubMember1);
			club1.getMembers().add(clubMember2);
			session.save(club1);
			session.save(clubMember1);
			session.save(clubMember2);
		}
		session.getTransaction().commit();

		// of different queries
		log
				.debug("##### simple select which needs two queries, one for the club and one to initialize the members");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List list = session.createQuery("from JavaClub1").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClub1 club1 = (JavaClub1) iter.next();
			log.debug("{}", club1);
			for (Iterator iterator = club1.getMembers().iterator(); iterator
					.hasNext();) {
				log.debug("{}", iterator.next());
			}
		}
		session.getTransaction().commit();
		log
				.debug("#####  select using fetch to initialize everything with one query");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we use a DistinctRootEntityResultTransformer or you will receive one club instance for
		// each member
		list = session.createQuery(
				"from JavaClub1 c left join fetch c.members")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClub1 club1 = (JavaClub1) iter.next();
			log.debug("{}", club1);
			for (Iterator iterator = club1.getMembers().iterator(); iterator
					.hasNext();) {
				log.debug("{}", iterator.next());
			}
		}
		session.getTransaction().commit();

		log
				.debug("#####  select using criteria query  to initialize everything with one query");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		list = session.createCriteria(JavaClub1.class).setFetchMode(
				"members", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClub1 club1 = (JavaClub1) iter.next();
			log.debug("{}", club1);
			for (Iterator iterator = club1.getMembers().iterator(); iterator
					.hasNext();) {
				log.debug("{}", iterator.next());
			}
		}
		session.getTransaction().commit();
	
		log
				.debug("#####  select using criteria query  using selects to initialize the member");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		list = session.createCriteria(JavaClub1.class).setFetchMode("members",
				FetchMode.SELECT).list();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClub1 club1 = (JavaClub1) iter.next();
			log.debug("{}", club1);
			for (Iterator iterator = club1.getMembers().iterator(); iterator
					.hasNext();) {
				log.debug("{}", iterator.next());
			}
		}
		session.getTransaction().commit();
	}

	private static void delete1(JavaClubMember1 clubMember1) {
		log.debug("delete1");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we assume having a fresh empty session, so we can call update
		session.update(clubMember1);
		log.debug("{}", clubMember1);
		JavaClub1 club1 = (JavaClub1) session.createQuery(
				"from JavaClub1 c where ? in elements(c.members) ").setEntity(
				0, clubMember1).uniqueResult();
		// first take away the member from the club, than delete it.
		club1.getMembers().remove(clubMember1);
		session.delete(clubMember1);

		session.getTransaction().commit();

	}

	private static JavaClubMember1 create1() {
		log.debug("create1");
		Session session = InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		JavaClub1 club1 = new JavaClub1("Hib club");
		JavaClubMember1 member1 = new JavaClubMember1("Eric");
		JavaClubMember1 member2 = new JavaClubMember1("Peter");
		// relation is uni-directional => we can only set the relation on one
		// side
		club1.getMembers().add(member1);
		club1.getMembers().add(member2);
		session.save(club1);
		session.save(member1);
		session.save(member2);

		session.getTransaction().commit();
		// return a member we will delete later
		return member1;
	}

	private static void cleanUp2() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from JavaClubMember2").executeUpdate();
		session.createQuery("delete from JavaClub2").executeUpdate();
		session.getTransaction().commit();
	}

	private static void query2() {
		log.debug("query2");
		log.debug("just create some entries");
		Random random = new Random();
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		for (int i = 0; i < 20; i++) {
			JavaClub2 club = new JavaClub2("name" + random.nextInt(30));
			JavaClubMember2 clubMember1 = new JavaClubMember2("name"
					+ random.nextInt(30));
			JavaClubMember2 clubMember2 = new JavaClubMember2("name"
					+ random.nextInt(30));
			clubMember1.setClub(club);
			clubMember2.setClub(club);
			session.save(club);
			session.save(clubMember1);
			session.save(clubMember2);
		}
		session.getTransaction().commit();

		// of different queries
		log
				.debug("##### simple select which needs two queries, one for the club and one to initialize the members");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List list = session.createQuery("from JavaClubMember2").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClubMember2 clubMember = (JavaClubMember2) iter.next();
			log.debug("{}", clubMember);
			log.debug("{}", clubMember.getClub());
		}
		session.getTransaction().commit();
		log
				.debug("#####  select using fetch to initialize everything with one query");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// you must contruct a HashSet or you will receive one club instance for
		// each member
		list = session.createQuery(
				"from JavaClubMember2 m left join fetch m.club").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClubMember2 clubMember = (JavaClubMember2) iter.next();
			log.debug("{}", clubMember);
			log.debug("{}", clubMember.getClub());
		}
		session.getTransaction().commit();
	
		log
				.debug("#####  select using criteria query  to initialize everything with one query");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		list = session.createCriteria(JavaClubMember2.class).setFetchMode(
				"club", FetchMode.JOIN).list();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			JavaClubMember2 clubMember = (JavaClubMember2) iter.next();
			log.debug("{}", clubMember);
			log.debug("{}", clubMember.getClub());
		}
		session.getTransaction().commit();
	
	}

	private static void delete2(JavaClubMember2 clubMember2) {
		log.debug("delete2");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// just delete, we do not have to update or reconnect
		session.delete(clubMember2);
		session.getTransaction().commit();
	
	}

	private static JavaClubMember2 create2() {
		log.debug("create2");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		JavaClub2 club2 = new JavaClub2("Hib club");
		JavaClubMember2 member1 = new JavaClubMember2("Eric");
		JavaClubMember2 member2 = new JavaClubMember2("Peter");
		// relation is uni-directional => we can only set the relation on one
		// side
		member1.setClub(club2);
		member2.setClub(club2);
		session.save(club2);
		session.save(member1);
		session.save(member2);

		session.getTransaction().commit();
		// return a member we will delete later
		return member1;
	}

	private static void cleanUp3() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from JavaClubMember3").executeUpdate();
		session.createQuery("delete from JavaClub3").executeUpdate();
		session.getTransaction().commit();
	}

	private static void delete3(JavaClubMember3 clubMember3) {
		log.debug("delete3");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we must reattach the member (our session is closed)
		session.update(clubMember3);
		clubMember3.getClub().getMembers().remove(clubMember3);
		session.delete(clubMember3);
		session.getTransaction().commit();

	}

	private static JavaClubMember3 create3() {
		log.debug("create3");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		JavaClub3 club = new JavaClub3("Hib club");
		JavaClubMember3 member1 = new JavaClubMember3("Eric");
		JavaClubMember3 member2 = new JavaClubMember3("Peter");
		// relation is bi-directional => we must set the relation on both sides
		member1.setClub(club);
		member2.setClub(club);
		club.getMembers().add(member1);
		club.getMembers().add(member2);
		// no cascade configured so save everything
		session.save(club);
		session.save(member1);
		session.save(member2);

		session.getTransaction().commit();
		// return a member we will delete later
		return member1;
	}
	
	private static void delete4(JavaClubMember4 clubMember4) {
		log.debug("delete4");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we must reattach the member (our session is closed)
		session.update(clubMember4);
		clubMember4.getClub().getMembers().remove(clubMember4);
		session.delete(clubMember4);
		session.getTransaction().commit();

	}

	private static JavaClubMember4 create4() {
		log.debug("create4");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		JavaClub4 club = new JavaClub4("Hib club");
		JavaClubMember4 member1 = new JavaClubMember4("Eric");
		JavaClubMember4 member2 = new JavaClubMember4("Peter");
		// relation is bi-directional => we must set the relation on both sides
		member1.setClub(club);
		member2.setClub(club);
		club.getMembers().add(member1);
		club.getMembers().add(member2);
		// no cascade configured so save everything
		session.save(club);
		session.save(member1);
		session.save(member2);

		session.getTransaction().commit();
		// return a member we will delete later
		return member1;
	}
	private static void cleanUp4() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from JavaClubMember4").executeUpdate();
		session.createQuery("delete from JavaClub4").executeUpdate();
		session.getTransaction().commit();
	}
}
