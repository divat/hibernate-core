
package de.laliluna.relation.ternary;

import java.util.Iterator;
import java.util.Set;


import org.hibernate.Session;
import de.laliluna.hibernate.InitSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestTernary {
	private static Logger log = LoggerFactory.getLogger(TestTernary.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer id = create();
		reuse(id);

	}

	private static void reuse(Integer id) {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Company company = (Company) session.createQuery(
				"from Company c where c.contracts.id = ?").setInteger(0, id)
				.uniqueResult();
		log.debug("{}", company);
		Set set = company.getContracts().keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();)
		{
			Workaholic workaholic = (Workaholic) iter.next();
			log.debug("{}", workaholic);
			Contract c = (Contract) company.getContracts().get(workaholic);
			log.debug("{}", c);

		}

		log.debug("remove contract");

		// loop to find entry
		for (Iterator iter = company.getContracts().keySet().iterator(); iter
				.hasNext();)
		{
			Workaholic element = (Workaholic) iter.next();
			Contract contract = (Contract) company.getContracts().get(element);
			// intValue compare is important as object compare does not mean that new
			// Integer(19) == new Integer(19)
			if (contract.getId().intValue() == id.intValue())
			{
				company.getContracts().remove(element);
				session.delete(contract);
				// we must break because we cannot continue to loop over a map from
				// which we have just deleted
				break;
			}
		}

		session.getTransaction().commit();
		
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// get the company fresh from the database
		session.refresh(company);

		set = company.getContracts().keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();)
		{
			Workaholic workaholic = (Workaholic) iter.next();
			log.debug("{}", workaholic);
			log.debug("contract is :" + company.getContracts().get(workaholic));

		}
		session.getTransaction().commit();

	}

	private static Integer create() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Workaholic workaholic1 = new Workaholic("Karl");
		Workaholic workaholic2 = new Workaholic("Susi");

		Company company = new Company("exploiter international");
		Contract contract1 = new Contract("slave 123");
		Contract contract2 = new Contract("no holiday");
		session.save(workaholic1);
		session.save(contract1);
		session.save(workaholic2);
		session.save(contract2);

		company.getContracts().put(workaholic1, contract1);
		company.getContracts().put(workaholic2, contract2);
		session.save(company);
		session.getTransaction().commit();
		return contract1.getId();
	}

}
