
package de.laliluna.other.diverse;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import de.laliluna.other.diverse.AllExample.DeveloperType;
import de.laliluna.hibernate.InitSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestAllExample {
	final static Logger log = LoggerFactory.getLogger(TestAllExample.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session = InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		AllExample allExample = new AllExample();
		allExample.setBuffer("test".getBytes());
		allExample.setDeveloperType(DeveloperType.BRILLIANT);
		allExample.setLongText("a long text");
		allExample.setMyDate(new Date());
		allExample.setMyTime(new Date());
		allExample.setMyTimeStamp(new Date());
		allExample.setPreciseField("abc");
		session.save(allExample);
		session.getTransaction().commit();

		session = InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		List<AllExample> examples = session.createCriteria(AllExample.class).list();
		for (Iterator iter = examples.iterator(); iter.hasNext();)
		{
			AllExample element = (AllExample) iter.next();
			log.debug("{}", element);
		}
		session.getTransaction().commit();

	}

}
