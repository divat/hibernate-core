
package de.laliluna.relation.one2many;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "javaclub_seq", sequenceName = "javaclub_id_seq")
public class JavaClub1 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="javaclub_seq")
	private Integer id;

	private String name;

	@OneToMany
	@JoinColumn(name="club_id", nullable=false)
	private Set<JavaClubMember1> members = new HashSet<JavaClubMember1>();

	public JavaClub1() {

	}

	public JavaClub1(String name) {
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

	public Set<JavaClubMember1> getMembers() {
		return members;
	}

	public void setMembers(Set<JavaClubMember1> members) {
		this.members = members;
	}

	public String toString() {
		return "JavaClub: " + getId() + " Name: " + getName();
	}
}
