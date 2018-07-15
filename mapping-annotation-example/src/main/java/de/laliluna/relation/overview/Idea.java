
package de.laliluna.relation.overview;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author hennebrueder
 */
@Entity
@SequenceGenerator(name = "idea_seq", sequenceName = "idea_id_seq")
public class Idea {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idea_seq")
	private Integer id;

	private String name;

	public Idea() {

	}
	
	public Idea(String name) {
		super();
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

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name});
	}
}
