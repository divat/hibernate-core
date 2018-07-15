
package de.laliluna.dao;

import de.laliluna.domain.Article;


public interface ArticleDao  extends BasicDao{


	/**
	 * lock the article and decrease the quantity.  If the quantity is not available, then the available will not be changed.
	 * @param article
	 * @param quantity
	 * @return true when stock quantity could be decreased or false when this was not possible
	 */
	public boolean lockAndDecrease(Article article, int quantity);


}
