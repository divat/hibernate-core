package de.laliluna.other.diverse;

import java.util.Date;
import java.util.List;
import java.text.MessageFormat;



import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import de.laliluna.other.diverse.EnglishGarden.GardenSize;
import de.laliluna.other.diverse.EnglishGarden.GardenType;
import de.laliluna.hibernate.InitSessionFactory;
import org.slf4j.LoggerFactory;

public class TestGarden {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(TestGarden.class);

    public static void main(String args[]) {
        TestGarden testGarden = new TestGarden();

        testGarden.testOrderBy();
        testGarden.testBatchLoad();
        testGarden.testCheckConstraint();
        testGarden.testExtraLazy();
        testGarden.testLazyOne2One();

        //testGarden.testVersion();

    }

    private void testLazyOne2One() {
           Session session = InitSessionFactory.getInstance()
                .getCurrentSession();

        Transaction tx = session.beginTransaction();

       EnglishGarden englishGarden = new EnglishGarden();
        HeadGardener headGardener = new HeadGardener("Christine");
        englishGarden.setHeadGardener(headGardener);
        session.save(englishGarden);
        tx.commit();


        session = InitSessionFactory.getInstance()
                        .getCurrentSession();

        tx = session.beginTransaction();

               EnglishGarden reloadGarden = (EnglishGarden) session.get(EnglishGarden.class, englishGarden.getId());
        log.debug("Loaded garden");
        log.debug("garden: "+reloadGarden);
        log.debug("Head gardener: "
        +reloadGarden.getHeadGardener());
                tx.commit();


    }

    /**
     * shows that the Check constraint was generaated. We do not allow blue tulips.S
     */
    private void testCheckConstraint() {
         Session session = InitSessionFactory.getInstance()
                .getCurrentSession();
              Transaction tx = null;
        try {
            tx = session.beginTransaction();
            EnglishGarden englishGarden = new EnglishGarden();
            session.save(englishGarden);
            Tulip tulip = new Tulip("Blue");
            englishGarden.getTulips().add(tulip);

            tx.commit();
        } catch (HibernateException e) {
            System.out.println("fine, we expected this exception");
            tx.rollback();
        }
    }

    /**
     * shows that the tulips are loaded in batches and not per garden. Requires batch size to be set either in hibernate.cfg.xml
     * or in the relation with @BatchSize
     */
    private void testBatchLoad() {
        Session session = InitSessionFactory.getInstance()
                .getCurrentSession();

        Transaction tx = session.beginTransaction();

        List<EnglishGarden> gardens = session.createCriteria(EnglishGarden.class).list();
        for (EnglishGarden garden : gardens) {
            System.out.println("Garden: " + garden);
            for (Tulip t : garden.getTulips()) {
                System.out.println("Tulip: " + t);
            }
        }
        tx.commit();
    }
    /**
     * shows that size does not trigger lazy loading, if LazyCollectionOption.EXTRA is set
     */
    private void testExtraLazy() {
        System.out.println("Testing extra lazy");
        Session session = InitSessionFactory.getInstance()
                .getCurrentSession();

        Transaction tx = session.beginTransaction();

        List<EnglishGarden> gardens = session.createCriteria(EnglishGarden.class).list();
        for (EnglishGarden garden : gardens) {
            System.out.println(MessageFormat.format("Garden: {0} Number of tulips: {1}", new Object[]{garden, garden.getTulips().size()}));

        }
        tx.commit();
    }

    /**
     * shows the use of ordering with database order by clause
     */
    private void testOrderBy() {
        Session session = InitSessionFactory.getInstance()
                .getCurrentSession();

        Transaction tx = session.beginTransaction();
        EnglishGarden englishGarden = new EnglishGarden();
        session.save(englishGarden);
        Tulip tulip = new Tulip("lightblue");
        englishGarden.getTulips().add(tulip);

        Tulip tulip2 = new Tulip("Red");
        englishGarden.getTulips().add(tulip2);

        Tulip tulip3 = new Tulip("Green");
        englishGarden.getTulips().add(tulip3);


        tx.commit();


        session = InitSessionFactory.getInstance()
                .getCurrentSession();

        tx = session.beginTransaction();
        englishGarden = (EnglishGarden) session.get(EnglishGarden.class, englishGarden.getId());
        for (Tulip t : englishGarden.getTulips()) {
            System.out.println("Tulip: " + t);
        }
        tx.commit();


    }

    /**
     * tests if the version column works, i.e. updates from older versions
     * are not allowed
     */
    public void testVersion() {
        Transaction tx = null;
        Transaction tx2 = null;
        try {
            Session session = InitSessionFactory.getInstance()
                    .getCurrentSession();

            tx = session.beginTransaction();
            /* delete all garden first */
            session.createQuery("delete from Garden").executeUpdate();
            session.flush();

            /* create a Garden */
            EnglishGarden garden = new EnglishGarden();
            garden.setGardenType(GardenType.FARM_GARDEN);
            garden.setGardenSize(GardenSize.MEDIUM);
            session.save(garden);
            // flush and clear, just in case a reader changes the auto close
            // session in the hibernate.cfg.xml
            session.flush();
            session.clear();
            tx.commit();
            log.debug("Version: " + garden.getVersion());
            tx = null;
            // check that the id is generated
            System.out.println(garden.getId());

            // we open two sessions now
            session = InitSessionFactory.getInstance().openSession();
            Session session2 = InitSessionFactory.getInstance().openSession();

            tx = session.beginTransaction();
            tx2 = session2.beginTransaction();

            EnglishGarden gard1 = (EnglishGarden) session.get(EnglishGarden.class, garden.getId());
            log.debug("Version garden 1: " + gard1.getVersion());
            EnglishGarden gard2 = (EnglishGarden) session2.get(EnglishGarden.class, garden.getId());
            log.debug("Version garden 2: " + gard2.getVersion());
            gard1.setTimestampField(new Date());
            tx.commit();
            tx = null;
            log.debug("Version garden 1 after change: " + gard1.getVersion());
            log.debug("Version garden 2 after change: " + gard2.getVersion());
            gard2.setTimestampField(new Date());
            // gard1 was updated and should have a new version now. The
            // following commit fails as the gard2 has an outdated version
            tx2.commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            if (tx2 != null)
                tx2.rollback();

            if (e instanceof StaleObjectStateException)
                System.out.println("We expected this exception");
            else
                throw e;

        }
    }
}
