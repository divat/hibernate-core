package de.laliluna.other.mapkey;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;

public class MapKeyTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //clean();
        create();

        list();

    }

    private static void list() {
        Session session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        List<Beach> beaches = session.createCriteria(Beach.class).list();
        for (Beach beach : beaches) {
            System.out.println(beach);
            for (String key : beach.getCountries().keySet()) {
                System.out.println(MessageFormat.format("{0} = {1}", new Object[]{key,
                        beach.getCountries().get(key)}));
            }
            for (String key : beach.getOthercountries().keySet()) {
                System.out.println(MessageFormat.format("{0} = {1}", new Object[]{key,
                        beach.getOthercountries().get(key)}));
            }
        }


        session.getTransaction().commit();
    }

    private static void create() {
        Session session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        Country country = new Country("de", "Deutschland");
        session.save(country);
        Beach beach = new Beach("Beautiful beach");
        beach.getCountries().put(country.getIsoCode(), country);
        country.setBeach(beach);
        session.save(beach);

        Country country2 = new Country("en", "England");
        beach.getOthercountries().put("en", country2);

        session.save(country2);
        session.getTransaction().commit();

    }

    private static void clean() {
        Session session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from Country").executeUpdate();
        session.createQuery("delete from Beach").executeUpdate();
        session.getTransaction().commit();

    }

}
