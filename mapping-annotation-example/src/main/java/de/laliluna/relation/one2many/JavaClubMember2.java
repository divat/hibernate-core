
package de.laliluna.relation.one2many;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Filter;


@Entity
@SequenceGenerator(name = "javaclubmember_seq", sequenceName = "javaclubmember_id_seq")
public class JavaClubMember2 {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="javaclubmember_seq")
	private Integer id;

	private String name;

	@ManyToOne()
	@JoinColumn(name="club_id")
	private JavaClub2 club;
	
	public JavaClubMember2() {

	}

	public JavaClubMember2(String name) {
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

	public JavaClub2 getClub() {
		return club;
	}

	public void setClub(JavaClub2 club) {
		this.club = club;
	}
	
	public String toString() {
		return "JavaClubMember2: " + getId() + " Name: " + getName();
	}
}
