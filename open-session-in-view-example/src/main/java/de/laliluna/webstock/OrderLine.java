
package de.laliluna.webstock;

import javax.persistence.*;

@Entity
public class OrderLine {

    @Id @GeneratedValue
	Integer id;

    @ManyToOne @JoinColumn
	private Order order;

	private int quantity;

    @ManyToOne @JoinColumn
	private Article article;
	
	public OrderLine() {
		super();
	}

    public OrderLine(int quantity, Article article) {
        this.quantity = quantity;
        this.article = article;
    }

    public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}



	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderLine:  quantity: "+getQuantity()+" "+getArticle();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
