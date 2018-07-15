
package de.laliluna.primarykey.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "puma_seq", sequenceName = "puma_id_seq", allocationSize = 100)
public class Puma {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "puma_seq")
	private Integer id;

	private String name;

	public Puma() {
		super();
	}

	public Puma(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
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

}
