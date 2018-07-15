
package de.laliluna.relation.many2many;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.hibernate.*;

import de.laliluna.hibernate.InitSessionFactory;


public class TestMany2many {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestMany2many.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Visitor1 visitor1 = create1();
		addAndRemove1(visitor1);
		delete1(visitor1);
		query1();
		cleanUp1();
		Visitor2 visitor2 = create2();
		addAndRemove2(visitor2);
		delete2(visitor2);
		cleanUp2();
	}

		private static void addAndRemove1(Visitor1 visitor1) {
		log.debug("AddAndRemove1");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we expect that the visitor is not yet in the session and that no
		// changes have been made to it. => we move it to persistent state 
		session.buildLockRequest(LockOptions.NONE).lock(visitor1);
		// remove the visitor from the first concert we come across
		List list = session.createQuery(
				"from Concert1 c where ? in elements(c.visitors)").setEntity(0,
				visitor1).list();

		Concert1 concert1 = (Concert1) list.iterator().next();
		log.debug("remove in " + concert1);
		concert1.getVisitors().remove(visitor1);
		Visitor1 newVisitor = new Visitor1(null, "Christine");
		concert1.getVisitors().add(newVisitor);
		session.save(newVisitor);
		session.getTransaction().commit();
	}

	private static void cleanUp1() {
		log.debug("clean up1");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List<Concert1> result = session.createQuery(
						"from Concert1 c left join fetch c.visitors")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		for (Iterator iter = result.iterator(); iter.hasNext();)
		{
			Concert1 element = (Concert1) iter.next();
			element.getVisitors().clear();
		}
		session.flush();
		session.createQuery("delete from Concert1").executeUpdate();
		session.createQuery("delete from Visitor1").executeUpdate();
		session.getTransaction().commit();
	}

	private static void query1() {
		log.debug("query1");
		log
				.debug("just create some entries (all concerts are named u123 and visitors are Carmen12");
		Random random = new Random();
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		for (int i = 0; i < 20; i++)
		{
			Visitor1 visitor1 = new Visitor1(null, "Carmen" + random.nextInt(20));
			Visitor1 visitor2 = new Visitor1(null, "Carmen" + random.nextInt(20));
			Visitor1 visitor3 = new Visitor1(null, "Carmen" + random.nextInt(20));
			Concert1 concert1 = new Concert1(null, "u" + random.nextInt(50));
			concert1.getVisitors().add(visitor1);
			concert1.getVisitors().add(visitor2);
			concert1.getVisitors().add(visitor3);

			session.save(visitor1);
			session.save(visitor2);
			session.save(visitor3);
			session.save(concert1);
		}
		session.getTransaction().commit();

		log
				.debug("### show all concerts which have a visitor which name starts with Carmen2");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List list = session
				.createQuery(
						"select distinct c from Concert1 c left join c.visitors v where v.name like 'Carmen1%' order by c.id ")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Concert1 element = (Concert1) iter.next();
			log.debug("{}", element);
		}
		session.getTransaction().commit();
		log
				.debug("### show all visitors of concerts starting with name start with u1");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		list = session
				.createQuery(
						"select v from Concert1 c left join c.visitors v where c.name like 'u1%' order by v.name ")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Visitor1 element = (Visitor1) iter.next();
			log.debug("{}", element);
		}
		session.getTransaction().commit();

	}

	private static void delete1(Visitor1 visitor1) {
		log.debug("delete1");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// reattach the visitor as our last session is closed.
		session.buildLockRequest(LockOptions.NONE).lock(visitor1);
		// remove visitor from relations
		List list = session.createQuery(
				"from Concert1 c where ? in elements(c.visitors)").setEntity(0,
				visitor1).list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Concert1 element = (Concert1) iter.next();
			element.getVisitors().remove(visitor1);
		}
		// delete visitor
		session.delete(visitor1);

		session.getTransaction().commit();

	}

	private static Visitor1 create1() {
		log.debug("create1");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Visitor1 visitor1 = new Visitor1(null, "Edgar");
		Visitor1 visitor2 = new Visitor1(null, "Marie");
		Visitor1 visitor3 = new Visitor1(null, "Dima");
		Concert1 concert1 = new Concert1(null, "Udo Juergens");
		Concert1 concert2 = new Concert1(null, "U2");

		concert1.getVisitors().add(visitor1);
		concert1.getVisitors().add(visitor2);
		concert2.getVisitors().add(visitor3);
		concert2.getVisitors().add(visitor1);
		session.save(visitor1);
		session.save(visitor2);
		session.save(visitor3);
		session.save(concert1);
		session.save(concert2);
		session.getTransaction().commit();
		// return a member we will delete later
		return visitor1;
	}

	private static void cleanUp2() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from JavaClubMember2").executeUpdate();
		session.createQuery("delete from JavaClub2").executeUpdate();
		session.getTransaction().commit();
	}

	private static void delete2(Visitor2 visitor2) {
		log.debug("delete2");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		/*
		 * we expect the last session to be closed, so we need to reattach the
		 * visitor to bring it to persistent state this time we reread the object to
		 * have a fresh one.
		 */
		log.debug("attach to current session");
		session.update(visitor2);
		log.debug("remove relation");
		for (Iterator iter = visitor2.getConcerts().iterator(); iter.hasNext();)
		{
			Concert2 element = (Concert2) iter.next();			
			// visitor is the inverse side and will not update the concert, so we do
			// this explicitly
			session.update(element);
			element.getVisitors().remove(visitor2);
			
		}
		visitor2.getConcerts().clear();

		session.getTransaction().commit();
		// just delete, we do not have to update or reconnect
		log.debug("delete visitor");
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.delete(visitor2);
		session.getTransaction().commit();

	}

	private static Visitor2 create2() {
		log.debug("create2");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Visitor2 visitor1 = new Visitor2(null, "Edgar");
		Visitor2 visitor2 = new Visitor2(null, "Marie");
		Visitor2 visitor3 = new Visitor2(null, "Dima");
		Concert2 concert1 = new Concert2(null, "Udo Juergens");
		Concert2 concert2 = new Concert2(null, "U2");

		concert1.getVisitors().add(visitor1);
		visitor1.getConcerts().add(concert1);
		concert1.getVisitors().add(visitor2);
		visitor2.getConcerts().add(concert1);
		concert2.getVisitors().add(visitor3);
		visitor3.getConcerts().add(concert2);
		concert2.getVisitors().add(visitor1);
		visitor1.getConcerts().add(concert2);

		session.save(visitor1);
		session.save(visitor2);
		session.save(visitor3);
		session.save(concert1);
		session.save(concert2);
		// here is of course no more save. Hibernate tracks changes to objects in
		// persistent state

		session.getTransaction().commit();
		// return a member we will delete later
		return visitor1;
	}
	private static void addAndRemove2(Visitor2 visitor) {
		// we expect the former session to be closed, so we call a fresh session
		// know
		log.debug("addAndRemove2");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		
		/*
		 * update to bind the visitor to the current session = move it to persistent
		 * state
		 */
		session.update(visitor);//(visitor, LockMode.NONE);

		/*
		 * remove the visitor from the first concert we come across the relation is
		 * bi-directional so we must set this on both sides.
		 */
		Concert2 concert = visitor.getConcerts().get(0);
		/*
		 * IMPORTANT: we defined the relation as inverse = true on the visitor side.
		 * Our concert is not yet in persistent state !! we must make it persistent
		 * here or we must specify a cascade for update in the visitor mapping
		 */
	//	session.buildLockRequest(LockOptions.NONE).lock(concert);
		
		concert.getVisitors().remove(visitor);
		visitor.getConcerts().remove(concert);

		session.getTransaction().commit();
	}


}
