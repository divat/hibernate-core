package de.laliluna.other.query;

import de.laliluna.hibernate.InitSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.text.MessageFormat;
import java.util.List;

/**
 * User: Sebastian Hennebrueder Date: 17.06.2007
 */
public class QueryTest {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QueryTest.class);

    public static void main(String[] args) {
        Session session = InitSessionFactory.getInstance().getCurrentSession
            ();
        session.beginTransaction();
        clean(session);
        session.getTransaction().commit();
        session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        createSampleData(session);

        session.getTransaction().commit();

        session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        /* select queries */
//		firstExample(session);
//		createQueryDynamically(session, "Sebastian", null, "Introduction");
//		createQueryDynamically(session, "Sebastian", "big", "Introduction");
//		allBooks(session);
//		uniqueResult(session);
//		joinWithObjectArray(session);
//		joinWithoutObjectArray(session);
//		joinWithDistinctResult(session);
//		selectSingleColumn(session);
//		selectMultipleColumn(session);
//		selectEntitiesAndColumns(session);
//		selectSelectiveProperties(session);

        /* where condition */
//		simpleWhere(session);
//        whereLike(session);
//		whereIn(session);
//		whereWithAndOr(session);
//		whereWithRelations(session);
        whereWithCollectionConditions(session);
//		whereWithoutNamedParams(session);
//		whereWithObjectAsCondition(session);
//		whereWithAnyAllSomeExists(session);
//
//		/* sql based query */
//		sqlWithObjectArray(session);
//		session.getTransaction().commit();

    }

    private static void sqlWithObjectArray(Session session) {
        List<Object[]> results = session.createSQLQuery(
            "select id, name, pages from annotation.book").list();
        for (Object[] result : results) {
            log.info(MessageFormat.format("Book id:{0} name:{1} pages:{2}",
                result));
        }

    }

    private static void whereWithAnyAllSomeExists(Session session) {

        log
            .info("query with 'all' (we search books having more pages than " +
                "Java book(s) )");
        List<Book> books = session
            .createQuery(
                "from Book b where b.pages > all (select bo.pages from Book " +
                    "bo where bo.name like ?)")
            .setString(0, "Java%").list();

        DetachedCriteria subselect = DetachedCriteria.forClass(Book.class)
            .setProjection(Projections.property("pages")).add(
                Restrictions.like("name", "Java", MatchMode.START));
        /* faster approach */
        subselect = DetachedCriteria.forClass(Book.class).setProjection(
            Projections.max("pages")).add(
            Restrictions.like("name", "Java", MatchMode.START));
        books = session.createCriteria(Book.class).add(
            Property.forName("pages").gtAll(subselect)).list();
        for (Book book : books) {
            log.info("{}", book);
        }

        log
            .info("query with any/some (we search for books having at least " +
                "more pages than one java book");
        books = session
            .createQuery(
                "from Book b where b.pages > any (select bo.pages from Book " +
                    "bo where bo.name like ?)")
            .setString(0, "Java%").list();

        subselect = DetachedCriteria.forClass(Book.class).setProjection(
            Projections.property("pages")).add(
            Restrictions.like("name", "Java", MatchMode.START));
        books = session.createCriteria(Book.class).add(
            Property.forName("pages").gtSome(subselect)).list();
        for (Book book : books) {
            log.info("{}", book);
        }

        log
            .info("query with in (we search for books having the same number " +
                "of pages than a java book");
        books = session
            .createQuery(
                "from Book b where b.name not like :title and b.pages in " +
                    "(select bo.pages from Book bo where bo.name like :title)" +
                    "")
            .setString("title", "Java%").list();

        subselect = DetachedCriteria.forClass(Book.class).setProjection(
            Projections.property("pages")).add(
            Restrictions.like("name", "Java", MatchMode.START));
        books = session.createCriteria(Book.class).add(
            Restrictions.not(Restrictions.like("name", "Java",
                MatchMode.START))).add(
            Property.forName("pages").in(subselect)).list();
        for (Book book : books) {
            log.info("{}", book);
        }

        log
            .info("query with exist (we search for books not having any " +
                "chapters");

        books = session.createQuery(
            "from Book b where not exists elements(b.chapters)").list();

        books = session.createCriteria(Book.class).add(
            Restrictions.isEmpty("chapters")).list();
        for (Book book : books) {
            log.info("{}", book);
        }

    }

    private static void whereWithObjectAsCondition(Session session) {
        log
            .debug("select with a where condition using an object (all books " +
                "of author Sebastian)");
        /* get the first author named Sebastian */
        Author author = (Author) session.createQuery(
            "from Author a where a.name like 'Sebastian%'")
            .setMaxResults(1).uniqueResult();

        List<Book> books = session
            .createQuery("from Book b where b.author = ?").setEntity(0,
                author).list();

        books = session.createCriteria(Book.class).add(
            Restrictions.eq("author", author)).list();

        Chapter chapter = (Chapter) session.createQuery(
            "from Chapter c where c.name like 'Introduction%'")
            .setMaxResults(1).uniqueResult();
        log.debug("{}", chapter);

        books = session.createQuery(
            "select b from Book b left join b.chapters c where c = ?")
            .setEntity(0, chapter).list();

        /* an alternative approach */
        books = session.createQuery(
            "from Book b where ? in elements(b.chapters)").setEntity(0,
            chapter).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void whereWithoutNamedParams(Session session) {
        log
            .debug("select with a where condition using ? as parameter (all " +
                "books with id > 5 and name starting with 'Java')");
        List<Book> books = session.createQuery(
            "select b from Book b where b.name like ? and b.id > ?")
            .setString(0, "Java" + "%").setInteger(1, 5).list();

        /* not possible with criteria */
        for (Book book : books) {
            log.info("{}", book);
        }

    }

    private static void whereWithRelations(Session session) {
        log
            .debug("select with a where condition using relations (all books " +
                "having a author smoking a big cigar)");
        List<Book> books = session
            .createQuery(
                "select b from Book b where b.author.cigar.name like " +
                    ":cigarName")
            .setString("cigarName", "big" + "%").list();

        books = session.createCriteria(Book.class).createAlias("author" +
            ".cigar",
            "c").add(Restrictions.like("c.name", "big", MatchMode.START))
            .list();
        for (Book book : books) {
            log.info("{}", book);
        }

        /* walking through a 1:n or m:n relation requires always an alias */
        log
            .debug("select with a where condition using relations (all books " +
                "having a chapter titled 'Introduction')");
        books = session
            .createQuery(
                "select b from Book b left join b.chapters c where c.name " +
                    "like :title")
            .setString("title", "Introduction" + "%").list();

        books = session.createCriteria(Book.class).createAlias("chapters",
            "c")
            .add(
                Restrictions.like("c.name", "Introduction",
                    MatchMode.START)).list();
        for (Book book : books) {
            log.info("{}", book);
        }

    }

    private static void whereWithAndOr(Session session) {
        log
            .debug("select with a where condition using 'and' and 'or' (all " +
                "books where id is lower than 200 and title starts with " +
                "'Java' or 'Hibernate')");
        List<Book> books = session
            .createQuery(
                "select b from Book b where b.id < :maxId and (b.name = " +
                    ":title1 or b.name = :title2)")
            .setInteger("maxId", 200).setString("title1", "Java" + "%")
            .setString("title2", "Hibernate" + "%").list();

        /*
        * if we need multiple or, we can use disjunction, else we can use
        * Restrictions.or
        */
        books = session.createCriteria(Book.class).add(
            Restrictions.lt("id", 200)).add(
            Restrictions.disjunction().add(
                Restrictions.like("name", "UML", MatchMode.START)).add(
                Restrictions.like("name", "Java", MatchMode.START))
                .add(
                    Restrictions.like("name", "Hibernate",
                        MatchMode.START))).list();
        books = session.createCriteria(Book.class).add(
            Restrictions.lt("id", 200)).add(
            Restrictions.or(Restrictions.like("name", "Java",
                MatchMode.START), Restrictions.like("name",
                "Hibernate", MatchMode.START))).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void whereIn(Session session) {

        String titles[] = new String[]{"Java book 1",
            "Hibernate Developer Guide"};

        log
            .debug("select with a where 'in' condition (all books having " +
                "either 'Java book 1' or 'Hibernate Developer Guide' as " +
                "title)");
        List<Book> books = session.createQuery(
            "select b from Book b where b.name in ( :titles )")
            .setParameterList("titles", titles).list();

        books = session.createCriteria(Book.class).add(
            Restrictions.in("name", titles)).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void whereLike(Session session) {
        log
            .debug("select with a where like condition (all books having a " +
                "title starting with 'Java')");
        List<Book> books = session.createQuery(
            "select b from Book b where b.name = :title").setString(
            "title", "Java" + "%").list();

        books = session.createCriteria(Book.class).add(
            Restrictions.like("name", "Java", MatchMode.START)).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void simpleWhere(Session session) {
        log
            .debug("select with a simple where condition (all books having " +
                "the title 'Hibernate Developer Guide')");
        List<Book> books = session.createQuery(
            "select b from Book b where b.name = :title").setString(
            "title", "Hibernate Developer Guide").list();

        books = session.createCriteria(Book.class).add(
            Restrictions.eq("name", "Hibernate Developer Guide")).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void selectSelectiveProperties(Session session) {
        log
            .debug("select only some properties of the book as we do not need" +
                " the large LOB columns (this is a readonly query)");
        List<Book> books = session
            .createQuery(
                "select new de.laliluna.other.query.Book(b.id, " +
                    "b.name) from Book b")
            .setReadOnly(true).list();

        /*
        * we cannot force readonly with criteria, either be careful or create
         * a
        * new BookReport class holding the low memory version of book
        */
        List<BookReport> bookReport = session.createCriteria(Book.class)
            .setProjection(
                Projections.projectionList().add(
                    Projections.property("id"), "idInReport").add(
                    Projections.property("name"), "nameInReport"))
            .setResultTransformer(
                new AliasToBeanResultTransformer(BookReport.class))
            .list();

        for (BookReport book : bookReport) {
            log.info("{}", book);
        }

    }

    private static void selectEntitiesAndColumns(Session session) {
        log
            .debug("select entities and scalar values as result (reporting " +
                "query showing chapter object and total chapters with this " +
                "name)");
        List<Object[]> results = session
            .createQuery(
                "select ch, (select count(c.name) from Chapter c where c.name" +
                    " = ch.name) from Chapter ch")
            .list();

        /* not possible with criteria */

        for (Object element[] : results) {
            log.info("Chapter: {} count: {}", element[0] , element[1]);
        }
    }

    private static void selectMultipleColumn(Session session) {
        log
            .debug("select multiple columns (scalar value) as result (count " +
                "chapters with same name)");
        List<Object[]> results = session.createQuery(
            "select c.name, count(c.name) from Chapter c group by c.name")
            .list();

        results = session.createCriteria(Chapter.class).setProjection(
            Projections.projectionList().add(
                Projections.groupProperty("name")).add(
                Projections.count("name"))).list();

        for (Object element[] : results) {
            log.info("Chapter: {} count: {}", element[0] , element[1]);
        }
    }

    private static void selectSingleColumn(Session session) {
        log
            .debug("select with a single column (scalar value) as result (ids" +
                " of all authors)");
        List<Integer> authorIds = session.createQuery(
            "select a.id from Author a").list();

        authorIds = session.createCriteria(Author.class).setProjection(
            Projections.property("id")).list();

        for (Integer authorId : authorIds) {
            log.info("Author id: {}" , authorId);
        }
    }

    private static void joinWithDistinctResult(Session session) {
        log
            .debug("select with a join returning a distinc result list (all " +
                "books with a chapter named 'Introduction' or 'Summary')");
        List<Book> books = session
            .createQuery(
                "select b from Book b inner join b.chapters ch where ch.name " +
                    "like :intro or ch.name like :summary")
            .setString("intro", "Introduction").setString("summary",
                "Summary").setResultTransformer(
                Criteria.DISTINCT_ROOT_ENTITY).list();

        books = session.createCriteria(Book.class)
            .createAlias("chapters", "ch").add(
                Restrictions.or(Restrictions.like("ch.name",
                    "Introduction", MatchMode.START), Restrictions
                    .like("ch.name", "Summary", MatchMode.START)))
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void joinWithoutObjectArray(Session session) {
        log
            .debug("select with a join not returning an array (all books with" +
                " a chapter named 'Introduction')");
        List<Book> books = session
            .createQuery(
                "select b from Book b inner join b.chapters ch where ch.name " +
                    "like :intro")
            .setString("intro", "Introduction").list();

        books = session.createCriteria(Book.class)
            .createAlias("chapters", "ch").add(
                Restrictions.like("ch.name", "Introduction",
                    MatchMode.START)).list();

        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void joinWithObjectArray(Session session) {
        log.debug("select with a join returning an array");
        List<Object[]> results = session.createQuery(
            "select b, ch from Book b left join b.chapters ch").list();
        /* same in short notation */
        results = session.createQuery("from Book b left join b.chapters")
            .list();

        /* not possible with criteria queries */

        for (Object[] elements : results) {
            log.info(elements[0] + "--" + elements[1]);
        }
    }

    private static void uniqueResult(Session session) {
        log.debug("select a unique book");
        Book book = (Book) session.createQuery(
            "from Book b where b.id < :upperLimit").setInteger(
            "upperLimit", 2000).setMaxResults(1).uniqueResult();

        book = (Book) session.createCriteria(Book.class).add(
            Restrictions.lt("id", 2000)).setMaxResults(1).uniqueResult();
        log.info("{}", book);

    }

    private static void allBooks(Session session) {
        log.debug("select all books");
        List<Book> books = session.createQuery("select b from Book b").list()
            ;
        /* same in short notation */
        books = session.createQuery("from Book").list();

        books = session.createCriteria(Book.class).list();

        for (Book book : books) {
            log.info("{}", book);
        }

    }

    /**
     * shows how to use criteria queries to create a query dynamically
     *
     * @param authorName
     * @param cigarName
     * @param chapterTitle
     */
    private static void createQueryDynamically(Session session,
                                               String authorName,
                                               String cigarName,
                                               String chapterTitle) {
        log.info("Create a query dynamically ");

        Criteria criteria = session.createCriteria(Book.class);

        boolean authorAliasCreated = false;
        if (authorName != null) {
            criteria.createAlias("author", "a").add(
                Restrictions.like("a.name", authorName, MatchMode.START));
            authorAliasCreated = true;
        }
        if (cigarName != null) {
            if (!authorAliasCreated)
                criteria.createAlias("author", "a");
            criteria.createAlias("a.cigar", "c").add(
                Restrictions.like("c.name", cigarName, MatchMode.START));
        }

        if (chapterTitle != null) {
            criteria.createAlias("chapters", "ch")
                .add(
                    Restrictions.like("ch.name", chapterTitle,
                        MatchMode.START));
        }
        List<Book> books = criteria.list();
        for (Book book : books) {
            log.info("{}", book);
        }
    }

    private static void createSampleData(Session session) {
        BookCategory crimeStory = new BookCategory("Crime story");
        session.save(crimeStory);
        Book book = new Book("Java book 1", 250);
        book.getBookCategories().add(crimeStory);
        Author author = new Author("Sebastian");
        Cigar cigar = new Cigar("big one");
        author.setCigar(cigar);
        book.setAuthor(author);
        Chapter c1 = new Chapter("Introduction", "Welcome new Java expert");
        Chapter c2 = new Chapter("Summary",
            "Oh you are still awake. Thank you for reading.");
        book.getChapters().add(c1);
        book.getChapters().add(c2);
        session.save(book);

        book = new Book("Java book 2", 150);
        author = new Author("Alexander");
        cigar = new Cigar("light");
        author.setCigar(cigar);
        book.setAuthor(author);
        c1 = new Chapter("first steps", "This is a wise book about Java.");
        c2 = new Chapter("last steps", "You have learned everything you " +
            "need");
        book.getChapters().add(c1);
        book.getChapters().add(c2);
        session.save(book);

        book = new Book("Hibernate Developer Guide", 200);
        book.setAuthor(author);
        Chapter chapter = new Chapter("Introduction");
        book.getChapters().add(chapter);
        session.save(book);

        book = new Book("Spotted Turtle Guide", 500);
        book.getBookCategories().add(crimeStory);
        Author a = new Author("Emilio");
        book.setAuthor(a);
        chapter = new Chapter("Introduction");
        book.getChapters().add(chapter);

        session.save(book);

        book = new Book("Striped Turtle Guide", 250);
        book.setAuthor(a);
        book.getChapters().add(new Chapter(("First chapter")));

        session.save(book);

        book = new Book("Empty book", 0);
        book.setAuthor(author);
        session.save(book);

        /* some more Java books */
        book = new Book("Java and I", 10);
        book.getChapters().add(new Chapter("A chapter", "first word"));
        book.getChapters().add(new Chapter("Another chapter", "second word"));

        book.setAuthor(author);
        session.save(book);
        book = new Book("Java and you", 10);
        book.getChapters().add(new Chapter("A chapter", "first word"));
        book.getChapters().add(new Chapter("Another chapter", "second word"));

        book.setAuthor(author);
        session.save(book);
        book = new Book("Java and me", 10);
        book.getChapters().add(new Chapter("A chapter", "first word"));
        book.getChapters().add(new Chapter("Another chapter", "second word"));

        book.setAuthor(author);
        session.save(book);
        book = new Book("Java and us", 10);
        book.getChapters().add(new Chapter("A chapter", "first word"));
        book.getChapters().add(new Chapter("Another chapter", "second word"));

        book.setAuthor(author);
        session.save(book);

        author = new Author("Lazy Sebastian");
        session.save(author);

    }

    private static void clean(Session session) {
        List<Book> books = session.createCriteria(Book.class).list();
        for (Book book : books) {
            session.delete(book);
        }
    }

    /**
     * a first simple example
     *
     * @param session
     */
    private static void firstExample(Session session) {
        log.info("Books of author Sebastian, title starting with 'Java' ");
        List<Book> books = session
            .createQuery(
                "from Book b where b.author.name like :authorName and b.name like :title")
            .setString("authorName", "Sebastian").setString("title",
                "Java" + "%").list();
        for (Book book : books) {
            log.info("{}", book);
        }

        books = session.createCriteria(Book.class).createAlias("author", "a")
            .add(Restrictions.like("a.name", "Sebastian")).add(
                Restrictions.like("name", "Java", MatchMode.START))
            .list();
        for (Book book : books) {
            log.info("{}", book);
        }

        Long numberOfBooks = (Long) session.createQuery(
            "select count(b.id) from Book b where b.name like :title")
            .setString("title", "Java" + "%").uniqueResult();
        log.info("Number of java books: " + numberOfBooks);

    }

    private static void whereWithCollectionConditions(Session session) {
        log.info("Authors with more than two books");
        List<Author> authors = session.createQuery("from Author a where size(a.books) > 2").list();
        for (Author author : authors) {
            log.info("{}", author);
        }

        authors = session.createCriteria(Author.class).add(Restrictions.sizeGt("books", 2)).list();
        for (Author author : authors) {
            log.info("{}", author);
        }

        log.info("Authors with no books at all");
        authors = session.createQuery("from Author a where a.books is empty").list();
        for (Author author : authors) {
            log.info("{}", author);
        }

        authors = session.createCriteria(Author.class).add(Restrictions.isEmpty("books")).list();
        for (Author author : authors) {
            log.info("{}", author);
        }

        log.info("Books belonging to the \"Crime story\" category");
        BookCategory crimeStory = (BookCategory) session.createQuery("from BookCategory b where b.name = ?").setString(0, "Crime story")
            .setMaxResults(1).uniqueResult();
        List<Book> books = session.createQuery(
            "from Book b where ? in elements(b.bookCategories)")
            .setParameter(0, crimeStory).list();
        for (Book book : books) {
            log.info("{}", book);
        }

        books = session.createQuery(
            "from Book b where :x member of b.bookCategories")
            .setParameter("x", crimeStory).list();
        for (Book book : books) {
            log.info("{}", book);
        }


    }
}
