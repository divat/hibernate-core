
package de.laliluna.component.collection2;

import java.util.List;


import org.hibernate.LockOptions;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hennebrueder
 */
public class TestComponentCollection2 {
	private static Logger log = LoggerFactory
			.getLogger(TestComponentCollection2.class);

	/**
     * @param args
     */
	public static void main(String[] args) {
		Pizza pizza = create();
		update(pizza);
		query();

	}

	private static void query() {
        log.debug("Querying");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List<Pizza> list = session
				.createQuery(
						"from Pizza c left join c.ingredients a where a.name " +
                                "= :ingr")
				.setString("ingr", "Tomato").list();
		for (Pizza client : list) {
			System.out.println(client);
		}

		session.getTransaction().commit();
	}

	private static void update(Pizza pizza) {
		log.debug("update");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
        log.debug("Removing an ingredient");
		session.beginTransaction();
		// reattach hedgehog as session could have been closed
		session.buildLockRequest(LockOptions.NONE).lock(pizza);
		// delete of ingredient will work
		Ingredient ingredient = (Ingredient) pizza.getIngredients()
				.iterator().next();
		pizza.getIngredients().remove(ingredient);
		session.flush();

        log.debug("Adding an ingredient");
		Ingredient ingredient1 = new Ingredient("Tomato");
		pizza.getIngredients().add(ingredient1);

		session.getTransaction().commit();

	}

	private static Pizza create() {
		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Pizza pizza = new Pizza("Speciale");
        Ingredient cheese = new Ingredient("Cheese");
        Ingredient salami = new Ingredient("Salami");
		Ingredient tomatoes = new Ingredient("Tomatoes");
		pizza.getIngredients().add(salami);
		pizza.getIngredients().add(cheese);
		pizza.getIngredients().add(tomatoes);
		session.save(pizza);
		session.getTransaction().commit();
		return pizza;
	}

}
