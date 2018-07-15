package de.laliluna.relation.many2many;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "visitor_seq", sequenceName = "visitor_id_seq")
public class Visitor1 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="visitor_seq")
	private Integer id;

	private String name;

	public Visitor1() {

	}

	public Visitor1(Integer id, String name) {
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
		return "Visitor1: " + getId() + " Name: " + getName();
	}

}
