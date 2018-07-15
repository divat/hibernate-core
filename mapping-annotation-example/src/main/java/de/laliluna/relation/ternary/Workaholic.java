
package de.laliluna.relation.ternary;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "workaholic_seq", sequenceName = "workaholic_id_seq")
public class Workaholic{


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="workaholic_seq")
	private Integer id;

	private String name;

	public Workaholic() {

	}

	public Workaholic(String name) {
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

	public String toString() {
		return "Workaholic: " + getId() + " Name: " + getName();
	}
}
