
package de.laliluna.component.simple;

import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.laliluna.component.simple.Pullover.Color;
import de.laliluna.hibernate.InitSessionFactory;

/**
 * @author hennebrueder
 */
public class TestComponentSimple {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestComponentSimple.class);

	public static void main(String[] args) {
		clean();
		create();
		select();
		
		createSheep();

	}

	private static void clean() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from Soup").executeUpdate();

		session.getTransaction().commit();

	}

	private static void select() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List<Soup> list = session.createQuery(
				"from Soup s where s.taste.evaluation = ?").setString(0,
				"best eaten so far").list();
		for (Soup soup : list) {
			System.out.println(soup);
		}

		session.getTransaction().commit();

	}

	private static void create() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Soup soup = new Soup("Vegetable soup");
		Taste taste = new Taste();
		taste.setEvaluation("best eaten so far");
		taste.setFirstImpression("incredible");
		soup.setTaste(taste);
		Recipe recipe = new Recipe();
		recipe.setDescription("wash and cut vegetables\nadd water\ncook");
		recipe.setIngredients("choice of vegetables you like");
		soup.setRecipe(recipe);
		/*
         * We should set the soup property of recipe as well. We do need it for
         * saving but the recipe would not be updated. It is set, if we load the
         * soup fresh from db but it won't be automatically set in the current
         * session.
         */
		recipe.setSoup(soup);

		session.save(soup);

		session.getTransaction().commit();

	}

	private static void createSheep() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			/*
             * the Christine sheep will deliver the wool for a red pullover for
             * Sebastian
             */
			Sheep sheep = new Sheep();
			sheep.setName("Christine");
			Pullover pullover = new Pullover(Color.BLUE, "Sebastian");
			sheep.setPullover(pullover);
			session.save(sheep);
			tx.commit();
		} catch (RuntimeException e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
			}
			throw e;
		}
	}

}
