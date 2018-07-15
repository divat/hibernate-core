package de.laliluna.other.namedquery;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.FlushMode;
import de.laliluna.hibernate.InitSessionFactory;
import org.hibernate.type.StandardBasicTypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.List;


public class QueryTest {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QueryTest.class);


    public static void main(String[] args) {
//        generateData();
//        nativeSqlQueryEntity();
//        nativeSqlResultSet();
        performanceTest();
    }

    private static void nativeSqlQueryEntity() {

        List<ComputerBook> reports;

        Session session = InitSessionFactory.getInstance().getCurrentSession
            ();
        session.beginTransaction();
        reports = session.createSQLQuery("select id, " +
            "name as book_name from computerbook")
            .addEntity
                (ComputerBook.class).list();
        for (ComputerBook report : reports) {
            System.out.println(report);
        }

        System.out.println("Updating an SQL loaded entity");
        Lab aLab = (Lab) session.createSQLQuery("select l.* from lab l limit " +
            "1")
            .addEntity(Lab.class)
            .uniqueResult();
        aLab.setTitle("My first Hibernate Lab");


        System.out.println("Loading two entities");
        List<Object[]> result = session.createSQLQuery(
            "select {c.*}, {l.*} from computerbook c join lab l on l" +
                ".computerbook_fk=c.id")
            .addEntity("c", ComputerBook.class)
            .addEntity("l", Lab.class)
            .list();

        System.out.println("----");
        for (Object report[] : result) {
            ComputerBook book = (ComputerBook) report[0];
            Lab lab = (Lab) report[1];
            System.out.println(book + "-" + lab);
        }
        System.out.println("Loading two entities and being explicit with " +
            "alias");
        result = session.createSQLQuery(
            "select c.id as {c.id}, c.book_name as {c.name}, " +
                "l.id as {l.id}, l.title as {l.title} from computerbook c " +
                "join lab l on l.computerbook_fk=c.id")
            .addEntity("c", ComputerBook.class)
            .addEntity("l", Lab.class)
            .list();

        for (Object report[] : result) {
            ComputerBook book = (ComputerBook) report[0];
            Lab lab = (Lab) report[1];
            System.out.println(book + "-" + lab);
        }

        System.out.println("Loading computerbooks and eager loading labs with" +
            " a join");
        result = session.createSQLQuery(
            "select {c.*}, {l.*} from computerbook c join lab l on l" +
                ".computerbook_fk=c.id")
            .addEntity("c", ComputerBook.class).addJoin
                ("l", "c.labs").list();

        for (Object report[] : result) {
            ComputerBook book = (ComputerBook) report[0];
            Lab lab = (Lab) report[1];
            System.out.println(book + "-" + lab);
        }
        session.getTransaction().commit();
    }

    /**
     * shows mixed use of entitiy and scalar results
     */
    private static void nativeSqlResultSet() {
        Session session;
        session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        System.out.println("Native Query with Result set mapping");
        List<ComputerBook> books = session.createSQLQuery
            ("select id, book_name from computerbook")
            .setResultSetMapping("bookReport").list();
        for (ComputerBook report : books) {
            System.out.println(report);
        }

        System.out.println("Mixed entity and scalar values in result set");
        List<Object[]> list = session.createSQLQuery(
            "select b.id, b.book_name, (select count(1) as count_group " +
                "from computerbook where book_name = b.book_name) as " +
                "count_group " +
                "from computerbook b")
            .setResultSetMapping("bookReport2")
            .list();
        for (Object o[] : list) {
            ComputerBook computerBook = (ComputerBook) o[0];
            long total = ((BigInteger) o[1]).longValue();
            log.debug("{}", computerBook + "-- Books with same name: " + total);
        }


        session.getTransaction().commit();
    }

    /**
     * shows mixed use of entitiy and scalar results
     */
    private static void namedQueries() {
        Session session;
        session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        System.out.println("Named Query with Result set mapping");
        List<Object[]> list = session.getNamedQuery("reportSql").list();
        for (Object o[] : list) {
            ComputerBook computerBook = (ComputerBook) o[0];
            long total = ((BigInteger) o[1]).longValue();
            log.debug("{}", computerBook + "-- Books with same name: " + total);
        }

        session.getTransaction().commit();
    }

    private static void generateData() {
        Session session = InitSessionFactory.getInstance().getCurrentSession
            ();
        session.beginTransaction();
        ComputerBook computerBook = new ComputerBook("Java book");
        session.save(computerBook);
        computerBook.getLabs().add(new Lab("A"));
        computerBook.getLabs().add(new Lab("B"));
        computerBook = new ComputerBook("Java book");
        session.save(computerBook);
        computerBook = new ComputerBook("Hibernate book");
        session.save(computerBook);
        session.getTransaction().commit();
    }

    /**
     * shows that named queries are a little bit faster
     */
    private static void performanceTest() {
        int maxQueries = 1000;
        Random r = new Random();


        long start = System.currentTimeMillis();


        for (int i = 0; i < maxQueries; i++) {
            Session session = InitSessionFactory.getInstance()
                .getCurrentSession();
            session.beginTransaction();
            List<ComputerBook> list = session.getNamedQuery("bookQuery").setString("name",
                "" + r.nextInt(10)).setInteger("minId",
                r.nextInt(500)).list();

            session.getTransaction().commit();
        }
        log.debug("Time with named namedquery: " + (System.currentTimeMillis
            () - start)+" ms");


        start = System.currentTimeMillis();

        for (int i = 0; i < maxQueries; i++) {
            Session session = InitSessionFactory.getInstance()
                .getCurrentSession();
            session.beginTransaction();
            List list = session.createQuery("from ComputerBook b where b.id >" +
                " :minId and b.name = :name").setString("name", "" + r.nextInt(10)).setInteger("minId", r.nextInt(500)).list();

            session.getTransaction().commit();
        }
        log.debug("Time with normal query: " + (System.currentTimeMillis() - start)+" ms");


    }
}
