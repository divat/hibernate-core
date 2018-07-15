package de.laliluna.component.collection2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Pizza {


	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@ElementCollection()
	@CollectionTable(name="pizza_ingredients", joinColumns =
    @JoinColumn(name="pizza_id"))
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();

	public Pizza() {

	}

	public Pizza(String name) {
		this.name = name;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> engredients) {
		this.ingredients = engredients;
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
		StringBuffer buffer = new StringBuffer();
		buffer.append("Client: " + id + " Name: " + name);
		for (Iterator iter = ingredients.iterator(); iter.hasNext();) {
			Ingredient element = (Ingredient) iter.next();
			buffer.append("\n  " + element.toString());
		}
		return buffer.toString();
	}

}
