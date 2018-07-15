
package de.laliluna.component.ternary;

import java.util.List;


import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;


public class TestComponentTernary {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestComponentTernary.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Offer offer = create();
		query();
		update(offer);
		

	}

	private static void query() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		/* select offers where quantity of red flowers are 5 */
		List<Offer> list = session
				.createQuery(
						"from Offer o left join o.offerLines l where l.quantity = :q and l.offerItem.name = :n")
				.setInteger("q", 5).setString("n", "Red flowers").list();
		for (Offer offer : list) {
			System.out.println(offer);
		}
		session.getTransaction().commit();

	}

	/**
	 * will remove the past line from the offer and add a new one for the item
	 * Yellow flower
	 * 
	 * @param line
	 */
	/**
	 * @param offer
	 */
	private static void update(Offer offer) {
		log.debug("update");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.buildLockRequest(LockOptions.NONE).lock(offer);
		OfferLine lineToRemove = (OfferLine) offer.getOfferLines().iterator()
				.next();
		offer.getOfferLines().remove(lineToRemove);
		// select the first yellow flower we come across
		OfferItem newItem = (OfferItem) session.createQuery(
				"from OfferItem i where i.name='Yellow flowers' ").list()
				.iterator().next();
		OfferLine offerLine = new OfferLine();
		offerLine.setQuantity(2);
		offerLine.setOfferItem(newItem);
		offer.getOfferLines().add(offerLine);
		session.getTransaction().commit();
	}

	private static Offer create() {

		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		OfferItem item1 = new OfferItem(null, "Red flowers");
		OfferItem item2 = new OfferItem(null, "Blue  flowers");
		OfferItem item3 = new OfferItem(null, "Yellow flowers");
		session.save(item1);
		session.save(item2);
		session.save(item3);

		Offer offer = new Offer();
		OfferLine line1 = new OfferLine();
		line1.setQuantity(5);
		line1.setOfferItem(item1);
		offer.getOfferLines().add(line1);
		OfferLine line2 = new OfferLine();
		line2.setQuantity(15);
		line2.setOfferItem(item2);
		offer.getOfferLines().add(line2);
		

		session.save(offer);
		session.getTransaction().commit();
		return offer;
	}

}
