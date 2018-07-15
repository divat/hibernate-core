package de.laliluna.relation.many2many;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "visitor_seq", sequenceName = "visitor_id_seq")
public class Visitor2 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="visitor_seq")
	private Integer id;

	private String name;

	@ManyToMany(mappedBy="visitors", cascade=CascadeType.ALL)
	private List<Concert2> concerts = new ArrayList<Concert2>();

	public Visitor2() {

	}

	public Visitor2(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<Concert2> getConcerts() {
		return concerts;
	}

	public void setConcerts(List<Concert2> concerts) {
		this.concerts = concerts;
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
		return "Visitor2: " + getId() + " Name: " + getName();
	}

}
