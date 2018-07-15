
package de.laliluna.relation.one2many;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "javaclubmember_seq", sequenceName = "javaclubmember_id_seq")
public class JavaClubMember1 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="javaclubmember_seq")
	private Integer id;

	private String name;

	public JavaClubMember1() {

	}

	public JavaClubMember1(String name) {
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
		return "JavaClubMember1: " + getId() + " Name: " + getName();
	}
}
