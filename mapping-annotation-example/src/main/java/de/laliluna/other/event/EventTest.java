package de.laliluna.other.event;

import de.laliluna.other.diverse.HeadGardener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EventTest {
  private static Logger log = LoggerFactory.getLogger(EventTest.class);

  public static void main(String[] args) {
    testLoadEvent();



  }


  private static void testLoadEvent() {
      final Configuration cfg = new Configuration();
      cfg.configure();
      final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
              .applySettings(cfg.getProperties())
              .buildServiceRegistry();
      EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
      eventListenerRegistry.prependListeners(EventType.LOAD, new LogAccessEventListener());
      SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);

    SessionFactory sessionFactory = cfg.buildSessionFactory();

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();

    HeadGardener headGardener = new HeadGardener("Holger");
    session.save(headGardener);
    session.getTransaction().commit();

    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    HeadGardener reloaded = (HeadGardener) session.get(HeadGardener.class, headGardener.getId());
    log.debug("{}", reloaded);
    session.getTransaction().commit();
  }



}
