
package de.laliluna.domain;

import javax.persistence.*;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue
	Integer id;

    @ManyToOne @JoinColumn
	private Order order;

	private boolean delivered;
	private int quantity;

    @ManyToOne @JoinColumn
	private Article article;
	
	public OrderLine() {
		super();
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

	public String toString() {
		return "OrderLine:  quantity: "+getQuantity()+" delivered: "+isDelivered() +" "+getArticle();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

}
