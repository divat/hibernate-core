
package de.laliluna.primarykey.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author hennebrueder
 */
@Entity
public class Generic {
	@Id
	@GenericGenerator(name = "aName", strategy = "seqhilo", parameters = { @Parameter(name = "max_lo", value = "5") })
	@GeneratedValue(generator = "aName")
	private Integer id;

	private String name;

	public Generic() {
		super();
	}

	public Generic(String name) {
		super();
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
