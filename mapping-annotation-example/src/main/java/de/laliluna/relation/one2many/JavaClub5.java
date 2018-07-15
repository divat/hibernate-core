
package de.laliluna.relation.one2many;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "javaclub_seq", sequenceName = "javaclub_id_seq")
public class JavaClub5 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="javaclub_seq")
	private Integer id;

	private String name;

	@OneToMany
	private List<JavaClubMember3> members = new ArrayList<JavaClubMember3>();

	public JavaClub5() {

	}

	public JavaClub5(String name) {
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

	public List<JavaClubMember3> getMembers() {
		return members;
	}

	public void setMembers(List members) {
		this.members = members;
	}

	public String toString() {
		return "JavaClub3: " + getId() + " Name: " + getName();
	}
}
