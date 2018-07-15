
package de.laliluna.relation.one2many;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "javaclub_seq", sequenceName = "javaclub_id_seq")
public class JavaClub2 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="javaclub_seq")
	private Integer id;

	private String name;


	public JavaClub2() {

	}

	public JavaClub2(String name) {
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
		return "JavaClub2: " + getId() + " Name: " + getName();
	}
}
