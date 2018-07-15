
package de.laliluna.webstock;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Article implements Serializable {

    @Id
    @GeneratedValue
	private Integer id;

	private String name;

	private float price;

	public Article() {
		super();
	}

	public Article(String name, float price) {
		super();
		this.name = name;
		this.price = price;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Article: " + getId() + " Name: " + getName();
	}
}
