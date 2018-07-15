package de.laliluna.other.filter;

import org.hibernate.Session;
import de.laliluna.hibernate.InitSessionFactory;

import java.util.List;

public class FilterTest {
    public static void main(String[] args) {
        Session session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        Desert desert = new Desert("Western region");
        session.save(desert);
        desert = new Desert("Far west region");
        session.save(desert);
        desert = new Desert("eastern region");
        session.save(desert);


        session.getTransaction().commit();

        session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        session.enableFilter("region").setParameter("regionName", "%west%");
        List<Desert> deserts = session.createCriteria(Desert.class).list();
        for (Desert d : deserts) {
            System.out.println("Desert in the west: " + d);
        }
        session.getTransaction().commit();
    }
}
