
package de.laliluna.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Article {

    @Id @GeneratedValue
	private Integer id;

	private String name;

	private float price;
	
	private int version;
	
	private int available;
	
	private boolean ebook;
	

	public boolean isEbook() {
		return ebook;
	}

	public void setEbook(boolean book) {
		ebook = book;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Article() {
		super();
	}

	public Article(String name, float price, int available) {
		super();
		this.name = name;
		this.price = price;
		this.available=available;
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
		return "Article: " + getId() + "Version: "+getVersion()+" Name: " + getName() + " available: "+getAvailable();
	}

    /**
     * decrease the quantity of the article
     *
     * @param quantity
     * @return true when the quantity was available else returns false
     */
    public boolean decreaseStock(int quantity) {
        if (getAvailable() >= quantity){
            setAvailable(getAvailable() - quantity);
            return true;
        }
        else
            return false;
    }
}
