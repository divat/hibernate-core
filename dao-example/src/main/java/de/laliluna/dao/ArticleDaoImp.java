
package de.laliluna.dao;

import de.laliluna.domain.Article;
import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;


public class ArticleDaoImp extends BasicDaoImp implements ArticleDao {


    public ArticleDaoImp(SessionFactory factory) {
        super(factory, Article.class);
    }

    public boolean lockAndDecrease(Article article, int quantity) {
        getSession().buildLockRequest(LockOptions.UPGRADE).lock(article);
        return article.decreaseStock(quantity);
    }

}
