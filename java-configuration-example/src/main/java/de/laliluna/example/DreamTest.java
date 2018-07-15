package de.laliluna.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DreamTest {

	/**
     * @param args
     */
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Dream.class);

		configuration.setProperty("hibernate.connection.url",
				"jdbc:postgresql://localhost:5432/learninghibernate");
		configuration.setProperty("hibernate.connection.username", "postgres");
		configuration.setProperty("hibernate.connection.password", "p");
		configuration.setProperty("hibernate.connection.driver_class",
				"org.postgresql.Driver");
		configuration.setProperty("hibernate.dialect",
				"org.hibernate.dialect.PostgreSQLDialect");
		configuration.setProperty("cache.use_second_level_cache",
				"false");
		configuration.setProperty("hibernate.current_session_context_class",
				"thread");
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Dream dream = new Dream();
		dream.setName("my dream");
		session.save(dream);
		session.getTransaction().commit();
	}

}
