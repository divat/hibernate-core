
package de.laliluna.component.collection1;

import java.util.Iterator;


import org.hibernate.*;

import de.laliluna.hibernate.InitSessionFactory;

/**
 * @author hennebrueder
 */
public class TestComponent2 {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestComponent2.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        Hedgehog hedgeHog = create();
        update(hedgeHog);

        remove();

    }

    private static void update(Hedgehog hedgehog) {
        log.debug("update");
        Session session = InitSessionFactory.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // reattach hedgehog as session could have been closed
            session.buildLockRequest(LockOptions.NONE).lock(hedgehog);
            // delete first and add a new address
            hedgehog.getAddresses().remove(0);

            WinterAddress newAddress = new WinterAddress("first class hotel",
                    "down the road");
            hedgehog.getAddresses().add(newAddress);

            tx.commit();
            tx = null;
            session = InitSessionFactory.getInstance().getCurrentSession();
            tx = session.beginTransaction();
            session.buildLockRequest(LockOptions.NONE).lock(hedgehog);
            // delete first and add a new address
            log.debug("{}", hedgehog);
            for (Iterator iter = hedgehog.getAddresses().iterator(); iter
                    .hasNext();) {
                WinterAddress element = (WinterAddress) iter.next();
                log.debug("{}", element);
            }
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

    private static void remove() {
        log.debug("remove");
        Hedgehog hedgehog = create();

        Session session = InitSessionFactory.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            session = InitSessionFactory.getInstance().getCurrentSession();
            session.beginTransaction();
            session.buildLockRequest(LockOptions.NONE).lock(hedgehog);
            hedgehog.getAddresses().remove(1);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
            }
            throw e;
        }
    }

    private static Hedgehog create() {
        log.debug("create");
        Session session = InitSessionFactory.getInstance().getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Hedgehog hedgehog = new Hedgehog("Peter");
            WinterAddress address1 = new WinterAddress("stack of wood",
                    "close to the apple tree");
            WinterAddress address2 = new WinterAddress("shelter",
                    "old shelter of the neighbour");
            WinterAddress address3 = new WinterAddress("garden house",
                    "A green garden house");
            hedgehog.getAddresses().add(address1);
            hedgehog.getAddresses().add(address2);
            hedgehog.getAddresses().add(address3);
            session.save(hedgehog);
            tx.commit();
            return hedgehog;
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
