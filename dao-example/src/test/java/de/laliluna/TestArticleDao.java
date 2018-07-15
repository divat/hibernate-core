package de.laliluna;

import org.hamcrest.Matchers;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;
import de.laliluna.domain.Article;
import de.laliluna.dao.ArticleDao;
import de.laliluna.dao.DaoFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestArticleDao {


    private ArticleDao articleDao;

    @Before
    public void setup(){
        articleDao = DaoFactory.getDao(ArticleDao.class);
    }
    @Test
	public void testLockAndDecrease() {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		Article article = new Article("test", 5.5f, 50);
		articleDao.save(article);

		session.getTransaction().commit();
		session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
        assertFalse("quantity should not be available", articleDao.lockAndDecrease(article, 100));
		assertTrue(articleDao.lockAndDecrease(article, 30));
		assertThat("quantity is now 20", article.getAvailable(), Matchers.is(20));
		session.getTransaction().commit();
	}

}
