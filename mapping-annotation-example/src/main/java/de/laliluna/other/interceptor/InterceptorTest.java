package de.laliluna.other.interceptor;

import de.laliluna.other.diverse.HeadGardener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class InterceptorTest {
  public static void main(String[] args) {
    testSessionFactoryBased();
  }



  private static void testSessionFactoryBased() {
    /* the Hibernate configuration should not be placed here.
    I just don't wanted to have the Interceptor be present for the rest of the application.
    You should move the code to a class like InitSessionFactory.
     */
    Configuration cfg = new Configuration();
    cfg.setInterceptor(new HistoryLogInterceptor());
      cfg.configure();
      final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
              .applySettings(cfg.getProperties())
              .buildServiceRegistry();
      SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();

    HeadGardener headGardener = new HeadGardener("Test2");
    session.save(headGardener);
    session.getTransaction().commit();

    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    HeadGardener reloaded = (HeadGardener) session.get(HeadGardener.class, headGardener.getId());
    reloaded.setName("new test2 name");
    session.getTransaction().commit();

  }

}
