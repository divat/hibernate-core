package de.laliluna.relation.overview;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "sport_seq", sequenceName = "sport_id_seq")
public class Sport {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_seq")
	private Integer id;

	private String name;

	public Sport() {

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
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), getId(), getName() });
	}
}
