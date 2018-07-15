
package de.laliluna.inheritance.joined;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import de.laliluna.hibernate.InitSessionFactory;


public class TestJoined {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestJoined.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Internetshop internetshop = create();
		update(internetshop);
		query();
	}

	private static void query() {
		log.debug("query");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		List list;
		list = session.createQuery("from PaymentMethod p ").list();
		list = session.createQuery("from CreditCardPayment p ").list();

		list = session.createQuery(
				"from PaymentMethod p where p.class = CreditCardPayment ").list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			PaymentMethod element = (PaymentMethod) iter.next();
			log.debug("{}", element);
		}

		list = session
				.createQuery(
						"from PaymentMethod p where p.class = CreditCardPayment and p.creditCardNumber like '1%'  ")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			PaymentMethod element = (PaymentMethod) iter.next();
			log.debug("{}", element);
		}
		/*
		 * select all shops allowing card payments
		 * 
		 * Important: we cannot use the condition p.class = CreditCardPayment
		 * because this needs a discriminator
		 */
		log.debug("select shops having creditcard payment");
		list = session
				.createQuery(
						"select i from Internetshop i left join i.paymentMethods p where p.id in (select p.id from CreditCardPayment  p )")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Internetshop element = (Internetshop) iter.next();
			log.debug("{}", element);
		}
		
		/* the last query is NOT working */
		log.debug("select shops having creditcard payment number 1 - This query will not work. Use the former one.");
		list = session
				.createQuery(
						"select i from Internetshop i left join i.paymentMethods p where p.creditCartNumber like '1%'")
				.list();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Internetshop element = (Internetshop) iter.next();
			log.debug("{}", element);
		}
		session.getTransaction().commit();

	}

	private static void update(Internetshop internetshop) {
		log.debug("update");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// we expect the session to be closed so reattach internetshop to move
		// it to persistent status
		session.buildLockRequest(LockOptions.NONE).lock(internetshop);
		// we stop offering cash payment in this shop
		for (Iterator iter = internetshop.getPaymentMethods().iterator(); iter
				.hasNext();)
		{
			PaymentMethod element = (PaymentMethod) iter.next();
			if (element instanceof CashPayment)
			{
				internetshop.getPaymentMethods().remove(element);
				break; // we must break here, because we cannot continue to
				// iterate over a collection we have just changed.
			}

		}
		session.getTransaction().commit();

	}

	private static Internetshop create() {
		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Internetshop internetshop = new Internetshop("Pencil Shop");
		CreditCardPayment cardPayment = new CreditCardPayment();
		cardPayment.setName("Visa and Mastercard");
		cardPayment.setCreditCardNumber("" + (new Random()).nextInt(20));
		CashPayment cashPayment = new CashPayment();
		cashPayment.setName("cash is always fine");
		internetshop.getPaymentMethods().add(cardPayment);
		internetshop.getPaymentMethods().add(cashPayment);

		// save the payment methods first, because the shop needs the primary
		// keys
		session.save(cardPayment);
		session.save(cashPayment);
		session.save(internetshop);
		session.getTransaction().commit();
		return internetshop;
	}

}
