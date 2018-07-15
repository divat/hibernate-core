
package de.laliluna.inheritance.mappedsuperclass;

import de.laliluna.hibernate.InitSessionFactory;
import de.laliluna.inheritance.mappedsuperclass.KitchenMouse.FavouriteCheese;
import de.laliluna.inheritance.mappedsuperclass.LibraryMouse.FavouriteBook;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * @author hennebrueder
 */
public class TestMappedSuperClass {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestMappedSuperClass.class);

	/**
     * @param args
     */
	public static void main(String[] args) {
		House musicConsumer = create();
		query();
		update(musicConsumer);
		

	}

	private static void query() {
		log.debug("query");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		List list = null;
		session.beginTransaction();

		log.debug("select all library mice");
		list = session.createQuery("from LibraryMouse").list();

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Mouse element = (Mouse) iter.next();
			log.debug("{}", element);
		}

		// 
		log.debug("select all  houses with kitchen mice");
		list = session.createQuery(
				"select h from House h where size(h.kitchenMice) > 0").list();
		/*
         * select all houses with a kitchen mouse which favourite cheese is
         * Gouda.
         */
		log.debug("select all houses with a kitchenMice having gouda as favourite cheese");
		list = session
				.createQuery(
						"select h from House h left join h.kitchenMice  k where  k.favouriteCheese = :cheese)")
				.setParameter("cheese", FavouriteCheese.GOUDA).list();
		for (Iterator iter = list.iterator(); iter.hasNext();) {

			log.debug("{}", iter.next());
		}

		session.getTransaction().commit();

	}

	private static void update(House house) {
		log.debug("update");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// reattach house to current session, cascade will reattach the mice as
        // well
		session.buildLockRequest(LockOptions.NONE).lock(house);

		// all mice in the library were catched by our cat
		for (LibraryMouse libraryMouse : house.getLibraryMice()) {
			session.delete(libraryMouse);
		}
		house.getLibraryMice().clear();
		session.getTransaction().commit();

	}

	private static House create() {
		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		House house = new House("Edwin");
		LibraryMouse libraryMouse = new LibraryMouse();
		libraryMouse.setName("sensitive");
		libraryMouse.setFavouriteBook(FavouriteBook.IN_LOVE_WITH_DR_CHIWAGO);
		house.getLibraryMice().add(libraryMouse);
		KitchenMouse kitchenMouse = new KitchenMouse();
		kitchenMouse.setName("Mouse from Amsterdam");
		kitchenMouse.setFavouriteCheese(FavouriteCheese.GOUDA);
		house.getKitchenMice().add(kitchenMouse);
		/*
         * Mouse mouse = new Mouse(); Sorry, but we can not use the Mouse
         * directly.
         */
		// save the mouse first befor we set the relation
		// keys
		session.save(kitchenMouse);
		session.save(libraryMouse);
		session.save(house);
		session.getTransaction().commit();
		return house;
	}

}
