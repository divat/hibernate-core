
package de.laliluna.component.simple;

import java.io.Serializable;
import java.text.MessageFormat;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "soup_seq", sequenceName = "soup_id_seq")
public class Soup {



	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="soup_seq")
	private Integer id;

	private String name;

	private Taste taste;

	
	private Recipe recipe;

	public Soup() {

	}

	public Soup(String name) {
		this.name = name;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Taste getTaste() {
		return taste;
	}

	public void setTaste(Taste taste) {
		this.taste = taste;
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
				getClass().getSimpleName(), id, name });
	}
}
