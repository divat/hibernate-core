package de.laliluna.relation.many2many;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "concert_seq", sequenceName = "concert_id_seq")
public class Concert1 {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "concert_seq")
	private Integer id;

	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "concert_visitor", 
			joinColumns = { @JoinColumn(name = "concert_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "visitor_id") })
	private List<Visitor1> visitors = new ArrayList<Visitor1>();

	public Concert1() {

	}

	public Concert1(Integer id, String name) {
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

	public List<Visitor1> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<Visitor1> visitors) {
		this.visitors = visitors;
	}

	@Override
	public String toString() {
		return "Concert1: " + getId() + " Name: " + getName();
	}
}
