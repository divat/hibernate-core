
package de.laliluna.primarykey.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * @author hennebrueder
 */
@Entity
public class Tiger {
	@Id
	@TableGenerator(name = "puma_gen", table = "primary_keys")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "puma_gen")
	private Integer id;

	private String name;

	public Tiger() {
		super();
	}

	public Tiger(String name) {
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
