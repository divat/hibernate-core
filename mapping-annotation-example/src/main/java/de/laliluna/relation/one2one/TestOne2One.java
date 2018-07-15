
package de.laliluna.relation.one2one;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;


public class TestOne2One {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestOne2One.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Order1 order1 = createCascade1();
		delete1(order1);
		query1();
		clean1();

		Order2 order2 = createCascade2();
		delete2(order2);
		query2();
		clean2();

		test3();
		clean3();
	}

	private static void clean1() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from Order1").executeUpdate();
		session.createQuery("delete from Invoice1").executeUpdate();
		
		session.getTransaction().commit();

	}

	private static void query1() {
		log.debug("query1");
		// create some orders and invoices
		Random random = new Random();
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		for (int i = 0; i < 20; i++)
		{
			Order1 order = new Order1(null, "" + random.nextInt(30));
			Invoice1 invoice = new Invoice1(null, "" + random.nextInt(30));
			order.setInvoice(invoice);
			session.save(order);
		}
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		log.debug("########### all order and initialize the invoices with one join (very fast)");
		List<Order1> list = session.createQuery("from Order1 o inner join fetch o.invoice")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Order1 element = (Order1) iter.next();
			log.debug("{}", element);
			log.debug("{}", element.getInvoice());
		}
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		log
				.debug("########### all invoices but do not initialize the order(does not work for this kind of relation)");
		list = session.createQuery("from Order1").list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Order1 element = (Order1) iter.next();
			log.debug("{}", element);
			log.debug("{}", element.getInvoice());
		}

		session.getTransaction().commit();
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

			session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		log.debug("########### orders where invoice number starts with 2 ");
		list = session.createQuery(
				"from Order1 o where o.invoice.number like '2%' ").list();
		/* alternative with one join instead of fetching invoice with a separate query for each order
		list = session.createQuery(
		"select i from Invoice1 i inner join i.order o where o.number like '2%' ").list();
 */
		
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Order1 element = (Order1) iter.next();
			log.debug("{}", element);
			log.debug("{}", element.getInvoice());
		}

		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		log
				.debug("########### select some invoices where order number starts with 1 ");
		List<Invoice1> invoices = session.createQuery(
				"select o.invoice from Order1 o where o.number like '1%' ").list();
		for (Iterator iter = invoices.iterator(); iter.hasNext();)
		{
			Invoice1 element = (Invoice1) iter.next();
			log.debug("{}", element);
		}

		session.getTransaction().commit();

	}

	private static void delete1(Order1 order1) {
		log.debug("delete the order (not very logically)");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// order is detachted because the old session is closed.
		session.update(order1); // cascade also updates the invoice
		session.delete(order1.getInvoice());
		order1.setInvoice(null);
		session.getTransaction().commit();
		/*
		 * we do not need to issue a session.save(order) order is in persistent
		 * state, os it will be saved when we commit
		 */

	}

	private static Order1 createCascade1() {
		log.debug("createCascade");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Order1 order = new Order1(null, "123");
		Invoice1 invoice = new Invoice1(null, "456");
		order.setInvoice(invoice);// cascade will save invoice as well
		session.save(order); 
		session.getTransaction().commit();
		return order;
	}

	private static void clean2() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from Order2").executeUpdate();
		session.createQuery("delete from Invoice2").executeUpdate();
		session.getTransaction().commit();
	}

	private static void query2() {
		log.debug("query2");
		// create some orders and invoices
		Random random = new Random();
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		for (int i = 0; i < 20; i++)
		{
			Order2 order = new Order2(null, "" + random.nextInt(30));
			Invoice2 invoice = new Invoice2(null, "" + random.nextInt(30));
			order.setInvoice(invoice);
			session.save(order);
		}
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		log.debug("########### orders where invoice number starts with 2 ");
		List<Order2> list = session.createQuery(
				"from Order2 o where o.invoice.number like '2%' ").list();
		for (Iterator<Order2> iter = list.iterator(); iter.hasNext();)
		{
			Order2 element = (Order2) iter.next();
			log.debug("{}", element);
			log.debug("{}", element.getInvoice());
		}

		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		log
				.debug("########### select some invoices where order number starts with 1 ");
		List<Invoice2> invoices = session.createQuery(
				"select i from Invoice2 i where i.order.number like '1%' ").list();
		for (Iterator<Invoice2 > iter = invoices.iterator(); iter.hasNext();)
		{
			Invoice2 element = (Invoice2) iter.next();
			log.debug("{}", element);
			log.debug("{}", element.getOrder());
		}

		session.getTransaction().commit();

	}

	private static void delete2(Order2 order) {
		log.debug("delete");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// order is detached because the old session is closed, so reattach it using update
		session.update(order); // cascade also updates the invoice
		session.delete(order.getInvoice());
		order.setInvoice(null);
		session.getTransaction().commit();
		/*
		 * we do not need to issue a session.save(order) order is in persistent
		 * state, os it will be saved when we commit
		 */

	}

	private static Order2 createCascade2() {
		log.debug("createCascade");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Order2 order = new Order2(null, "123");
		Invoice2 invoice = new Invoice2(null, "456");
		// bi-directional set on both sides !!!
		order.setInvoice(invoice);
		invoice.setOrder(order);
		session.save(order); // cascade will save order as well
		session.getTransaction().commit();
		return order;
	}

	private static void test3() {
		log.debug("Creating order3, invoice3");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Order3 order = new Order3(null, "123");
		Invoice3 invoice3 = new Invoice3(null, "456");
		invoice3.setOrder(order);
		session.save(order);
		session.save(invoice3);
		session.getTransaction().commit();
		
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.buildLockRequest(LockOptions.NONE).lock(invoice3);
		session.delete(invoice3);
		session.getTransaction().commit();
		
	}

	private static void clean3( ) {
		Session session;
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		/* important: Delete invoices first, because there is a constraint (optional=false) */
		session.createQuery("delete from Invoice3").executeUpdate();
		session.createQuery("delete from Order3").executeUpdate();
		session.getTransaction().commit();
	}
}
