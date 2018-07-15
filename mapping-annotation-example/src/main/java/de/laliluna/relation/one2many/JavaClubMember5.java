
package de.laliluna.relation.one2many;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * @author hennebrueder
 */
@Entity
@SequenceGenerator(name = "javaclubmember_seq", sequenceName = "javaclubmember_id_seq")
public class JavaClubMember5 {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "javaclubmember_seq")
	private Integer id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "club_id", nullable = false, insertable=false, updatable=false)
	private JavaClub5 club;

	public JavaClubMember5() {

	}

	public JavaClubMember5(String name) {
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

	public JavaClub5 getClub() {
		return club;
	}

	public void setClub(JavaClub5 club) {
		this.club = club;
	}

	public String toString() {
		return "JavaClubMember3: " + getId() + " Name: " + getName();
	}
}
