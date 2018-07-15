package de.laliluna.component.ternary;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "offeritem_seq", sequenceName = "offeritem_id_seq")
public class OfferItem {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offeritem_seq")
	private Integer id;

	private String name;

	public OfferItem() {

	}

	public OfferItem(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "OfferItem: " + id + " Name: " + name;
	}

}
