
package de.laliluna.primarykey.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author hennebrueder
 */
@Entity
public class Lion {

	/* This id is assigned. You must set if before saving */
	@Id
	private Integer id;

	private String name;

	public Lion() {
		super();
	}

	public Lion(Integer id, String name) {
		super();
		this.id = id;
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
