
package de.laliluna.component.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Embeddable;
import org.hibernate.annotations.Parent;


@Embeddable
public class Recipe {


    private String ingredients;

    private String description;

    @Parent
    private Soup soup;

    public Recipe() {

    }

    public Soup getSoup() {
	return soup;
    }

    public void setSoup(Soup soup) {
	this.soup = soup;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getIngredients() {
	return ingredients;
    }

    public void setIngredients(String ingredients) {
	this.ingredients = ingredients;
    }

	@Override
	public String toString() {
		return MessageFormat.format("{0}: ingredients={1}, description={2}", new Object[] {
				getClass().getSimpleName(), ingredients, description});
	}
}
