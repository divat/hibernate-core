package de.laliluna.other.query;

import de.laliluna.hibernate.InitSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;


public class PerformanceTest {

  private static Logger log = LoggerFactory.getLogger(QueryTest.class);

  public static void main(String[] args) {
    Session session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    clean(session);
    session.getTransaction().commit();
    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    createSampleData(session);

    session.getTransaction().commit();

    /* performance tips, we commit the tranaction to get a new session for each test */
    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    loadingBehaviourOfRelation(session);
    session.getTransaction().commit();

    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    efficientQueryForRelation(session);
    session.getTransaction().commit();

    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    efficientBatchSizeForRelation(session);

    session.getTransaction().commit();

    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    reportingQueries(session);
    session.getTransaction().commit();

//    session = InitSessionFactory.getInstance().getCurrentSession();
//    session.beginTransaction();
//    iteratingDataWithLowMemory(session);
//    session.getTransaction().commit();
//
//    //writingDataChangeTransactionStrategie(null);
//    //writingDataChangeFlushStrategie(null);
//
//    session = InitSessionFactory.getInstance().getCurrentSession();
//    session.beginTransaction();
//    fieldBasedLazyLoading(session);
//    session.getTransaction().commit();


  }

  private static void fieldBasedLazyLoading(Session session) {
    Chapter chapter = (Chapter) session.createCriteria(Chapter.class).setMaxResults(1).uniqueResult();
    log.debug("chapter is loaded");
    chapter.getContent();
    log.debug("Content LOB was called ");
  }

  /**
   * * improving performance when writing huge amounts of data by using flush and clear of the Hibernate Session
   *
   * @param session
   */
  private static void writingDataChangeFlushStrategie(Session session) {
    int maxBooks = 100;
    int maxChapters = 10;

    /* we simulate importing books from XML and just create the objects with random values */
    long start = System.currentTimeMillis();
    for (int i = 0; i < maxBooks; i++) {
      Book b = new Book("name " + i);
      for (int i2 = 0; i2 < maxChapters; i2++)
        b.getChapters().add(new Chapter("Title " + i2));
      session = InitSessionFactory.getInstance().getCurrentSession();
      session.beginTransaction();
      session.save(b);
      session.getTransaction().commit();
    }

    log.info("Performance with one book per transaction: " + (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();

    for (int i = 0; i < maxBooks; i++) {
      Book b = new Book("name " + i);
      for (int i2 = 0; i2 < maxChapters; i2++)
        b.getChapters().add(new Chapter("Title " + i2));
      session.save(b);
      /* force writing of all queries and clear the session to reduce memory usage */
      if ((i + 1) % 50 == 0) {
        session.flush();
        session.clear();

      }

    }
    session.getTransaction().commit();

    log.info("Performance with flush and only one transaction: " + (System.currentTimeMillis() - start));
  }

  /**
   * improving performance when writing huge amounts of data by reducing the number of transactions
   *
   * @param session
   */
  private static void writingDataChangeTransactionStrategie
          (Session
                  session) {
    int maxBooks = 100;
    int maxChapters = 10;

    /* we simulate importing books from XML and just create the objects with random values */
    long start = System.currentTimeMillis();
    for (int i = 0; i < maxBooks; i++) {
      Book b = new Book("name " + i);
      for (int i2 = 0; i2 < maxChapters; i2++)
        b.getChapters().add(new Chapter("Title " + i2));
      session = InitSessionFactory.getInstance().getCurrentSession();
      session.beginTransaction();
      session.save(b);
      session.getTransaction().commit();
    }

    log.info("Performance with one book per transaction: " + (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    session = InitSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();

    for (int i = 0; i < maxBooks; i++) {
      Book b = new Book("name " + i);
      for (int i2 = 0; i2 < maxChapters; i2++)
        b.getChapters().add(new Chapter("Title " + i2));
      session.save(b);
      if ((i + 1) % 50 == 0) {
        session.getTransaction().commit();
        session = InitSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

      }

    }
    if (session.getTransaction().isActive())
      session.getTransaction().commit();

    log.info("Performance with 100 books per transaction: " + (System.currentTimeMillis() - start));


  }

  /**
   * shows how to iterate through a large amount of data
   *
   * @param session
   */
  private static void iteratingDataWithLowMemory
          (Session
                  session) {
    log.info("iterating through a large amount of data");
    ScrollableResults results = session.createQuery("select b from Book b left join fetch b.chapters")
            .scroll(ScrollMode.FORWARD_ONLY);

    while (results.next()) {
      Book book = (Book) results.get(0);

      /* display entities and collection entries in the session */
      log.debug("{}", session.getStatistics().toString());
      // create XML for book
      for (Chapter chapter : book.getChapters()) {
        chapter.getContent();
        // create XML for chapter
      }
      session.clear();
    }
    results.close();


  }

  /**
   * shows how to create a complex report over author, book and chapter without loading entites
   *
   * @param session
   */
  private static void reportingQueries
          (Session
                  session) {
    log.info("Slow approach looping through all entities and counting");
    List<Author> authors = session.createCriteria(Author.class).list();
    for (Author author : authors) {
      long totalBooks = (Long) session.createQuery("select count(b) from Book b where b.author = ?")
              .setEntity(0, author).uniqueResult();
      long totalChapters = (Long) session
              .createQuery("select count(c.id) from Book b left join b.chapters c where b.author = ?")
              .setEntity(0, author).uniqueResult();
      log.info(MessageFormat.format("Author: {0} totalBooks: {1} totalChapters: {2}", new Object[]{author.getName(), totalBooks, totalChapters}));
    }
    
    log.info("Creating a report using object arrays");
    List<Object[]> authorReportObjects = session.createQuery("select a.name, " +
                    "count(b) as totalBooks, count(c) as totalChapters " +
                    "from Author a join a.books b join b.chapters c group by a.name").list();
    for (Object[] objects : authorReportObjects) {
		log.info(String.format("Report: Author %s, total books %d, total chapters %d", objects[0], objects[1], objects[2]));
	}
    
    log.info("Creating a complex report with one query");
    List<AuthorReport> authorReports = session
            .createQuery("select new de.laliluna.other.query.AuthorReport(a.id, a.name, " +
                    "count(b), count(c)) " +
                    "from Author a join a.books b join b.chapters c group by a.id, a.name").list();

    for (AuthorReport authorReport : authorReports) {
      log.info("{}", authorReport);
    }

    log.info("Creating a complex report with a aliasToBeanResultTransformer");
    authorReports = session
            .createQuery("select a.name as name, count(b) as totalBooks, count(c) as totalChapters " +
                    "from Author a join a.books b join b.chapters c group by a.name")
                    .setResultTransformer(new AliasToBeanResultTransformer(AuthorReport.class)).list();

    for (AuthorReport authorReport : authorReports) {
      log.info("{}", authorReport);
    }
    
  }

  /* shows how to reduce number of generated queries */
  private static void efficientQueryForRelation
          (Session
                  session) {
    log.info("selecting books in a non efficienct way");
    List<Book> books = session.createQuery("from Book b where b.name like ?").setString(0, "Java%").list();

    for (Book book : books) {
      int totalLength = 0;
      for (Chapter chapter : book.getChapters()) {
        totalLength += (chapter.getContent() != null ? chapter.getContent().length() : 0);
      }
      log.info("Length of all chapters: " + totalLength);

    }

    /* we must clear the session to see the effect of efficient loading */
    session.clear();

    log.info("selecting books in a very efficienct way");
    books = session.createQuery("select b from Book b left join fetch b.chapters where b.name like ?")
            .setString(0, "Java%")
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    for (Book book : books) {
      int totalLength = 0;
      for (Chapter chapter : book.getChapters()) {
        totalLength += (chapter.getContent() != null ? chapter.getContent().length() : 0);
      }
      log.info("Length of all chapters: " + totalLength);

    }

    log.info("selecting books in a very efficienct way with criteria queries");
    books = session.createCriteria(Book.class).setFetchMode("chapters", org.hibernate.FetchMode.JOIN)
            .add(Restrictions.like("name", "Java", MatchMode.START))
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    for (Book book : books) {
      int totalLength = 0;
      for (Chapter chapter : book.getChapters()) {
        totalLength += (chapter.getContent() != null ? chapter.getContent().length() : 0);
      }
      log.info("Length of all chapters: " + totalLength);

    }


  }

  /**
   * shows how to reduce number of generated queries .
   * To test this method change the @BatchSize in the Book method in front of private Set<Chapters> chapters.
   * a BatchSize of 4 instead of 1 will load the chapters for four books at a time.
   *
   * @param session
   */
  private static void efficientBatchSizeForRelation
          (Session
                  session) {
    log.info("selecting books and the effect of chaning the batchsize");
    List<Book> books = session.createQuery("from Book b where b.name like ?").setString(0, "Java%").list();

    for (Book book : books) {
      int totalLength = 0;
      for (Chapter chapter : book.getChapters()) {
        totalLength += (chapter.getContent() != null ? chapter.getContent().length() : 0);
      }
      log.info("Length of all chapters: " + totalLength);

    }


  }

  /* activate sql debugging to see how Cigars are loaded, when you load an author */
  private static void loadingBehaviourOfRelation
          (Session
                  session) {
    log.info("Loading with criteria");
    List<Author> authors = session.createCriteria(Author.class).list();

    log.info("Loading with HQL");
    authors = session.createQuery("from Author").list();

  }

  private static void createSampleData
          (Session
                  session) {
    Book book = new Book("Java book 1", 250);
    Author author = new Author("Sebastian");
    Cigar cigar = new Cigar("big one");
    author.setCigar(cigar);
    book.setAuthor(author);
    Chapter c1 = new Chapter("Introduction", "Welcome new Java expert");
    Chapter c2 = new Chapter("Summary", "Oh you are still awake. Thank you for reading.");
    book.getChapters().add(c1);
    book.getChapters().add(c2);
    session.save(book);

    book = new Book("Java book 2", 150);
    author = new Author("Alexander");
    cigar = new Cigar("light");
    author.setCigar(cigar);
    book.setAuthor(author);
    c1 = new Chapter("first steps", "This is a wise book about Java.");
    c2 = new Chapter("last steps", "You have learned everything you need");
    book.getChapters().add(c1);
    book.getChapters().add(c2);
    session.save(book);

    book = new Book("Hibernate Developer Guide", 200);
    book.setAuthor(author);
    Chapter chapter = new Chapter("Introduction");
    book.getChapters().add(chapter);
    session.save(book);

    book = new Book("Spotted Turtle Guide", 500);


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

    book = new Book("all about everything", 100000);
    for (int i = 0; i < 500; i++)
      book.getChapters().add(new Chapter("chapter " + i, "first word"));

    book.setAuthor(author);
    session.save(book);


  }

  private static void clean
          (Session
                  session) {
    List<Book> books = session.createCriteria(Book.class).list();
    for (Book book : books) {
      session.delete(book);
    }
  }

}