
package de.laliluna.inheritance.singletable;

import java.util.Iterator;
import java.util.List;


import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;

public class TestSingleTable {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestSingleTable.class);
	public  static void main(String args[]){
		Plant plant = create();
		update(plant);
		query();
	}

	private static Plant create() {
		Session session =  InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		
		Plant plant = new Plant(null, "some type of bush");
		Flower flower = new Flower(null, "rose", "red");
		Tree appleTree = new Tree(null, "apple tree", true);
		Tree cherryTree = new Tree(null, "cherry tree", true);
		Garden garden = new Garden(null, "my mixed garden");
		/* we can add any kind of Plant, Tree or Flower to plants */
		garden.getPlants().add(appleTree);
		garden.getPlants().add(cherryTree);
		garden.getPlants().add(flower);
		garden.getPlants().add(plant);
		
		garden.getFlowers().add(flower);
		/*the following will not work
		 * garden.getFlowers().add(plant);
		*/
		session.save(plant);
		session.save(cherryTree);
		session.save(flower);
		session.save(appleTree);
		session.save(garden);
		session.getTransaction().commit();
		/* in case you did not configure autoclose session on commit */
		if (session.isOpen())
			session.close();
		
		session =  InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		garden = (Garden) session.get(Garden.class, garden.getId());
		System.out.println("## Flowers ##");
		for (Iterator iter = garden.getFlowers().iterator(); iter.hasNext();)
			System.out.println(iter.next());
		System.out.println("## Plants ##");
		for (Iterator iter = garden.getPlants().iterator(); iter.hasNext();)
			System.out.println(iter.next());
		session.getTransaction().commit();
		/* in case you did not configure autoclose session on commit */
		if (session.isOpen())
			session.close();
		// we return a flower as plant
		return flower;
	}

	private static void update(Plant plant) {
		Session session =  InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		// we expect the session to be closed and the plant to be in status detached, so attach it first to move it to status persistent
		session.buildLockRequest(LockOptions.NONE).lock(plant);
		// when we cast to flower we can set the color
		((Flower)plant).setColor("blue");
		session.getTransaction().commit();
		/* in case you did not configure autoclose session on commit */
		if (session.isOpen())
			session.close();
	}

	private static void query() {
		Session session =  InitSessionFactory.getInstance().openSession();
		session.beginTransaction();
		// list plants and all subclasses
		List list = session.createQuery("from Plant").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Plant element = (Plant) iter.next();
			log.debug("{}", element);
		}
		/* select all flowers with blue color, the following work but I consider this as untidy
		 * may be Hibernate will not support this later, so do not use this.
		 */
		list = session.createQuery("from Plant p where p.color = 'blue' ").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Plant element = (Plant) iter.next();
			log.debug("{}", element);
		}
		// the better solution
		list = session.createQuery("from Flower f where f.color = 'blue' ").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Plant element = (Plant) iter.next();
			log.debug("{}", element);
		}
		// select all gardens with blue colors
		list = session.createQuery("select g from Garden g left join g.plants p where p.color = 'blue' ").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Garden element = (Garden) iter.next();
			log.debug("{}", element);
		}
		
		// select all gardens with trees
		list = session.createQuery("select g from Garden g  left join g.plants p where p.class=Tree").list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Garden element = (Garden) iter.next();
			log.debug("{}", element);
		}
		
		
		session.getTransaction().commit();
		/* in case you did not configure autoclose session on commit */
		if (session.isOpen())
			session.close();
		
	}
}