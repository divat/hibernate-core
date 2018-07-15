package de.laliluna.other.collectionfilter;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;

public class CollectionFilterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Child child = new Child();
		child.setName("Emilio");
		Pet pet = new Pet();
		pet.setName("Tuna");
		child.addPet(pet);
		Pet p2 = new Pet("Tina");
		child.addPet(p2);
		
		session.save(child);		
		session.getTransaction().commit();
		
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Child childReloaded = (Child) session.get(Child.class, child.getId());
		System.out.println("Pets starting with T");
		Query query = session.createFilter(childReloaded.getPets(), "where name like ?").setString(0, "T%");
		List<Pet> pets =  query.list();
		for (Pet p : pets) {
			System.out.println(p);
		}
		System.out.println("Pets starting with Ti");
		query = session.createFilter(childReloaded.getPets(), "where name like ?").setString(0, "Ti%");
		pets =  query.list();
		for (Pet p : pets) {
			System.out.println(p);
		}
		
		session.getTransaction().commit();

	}

}
