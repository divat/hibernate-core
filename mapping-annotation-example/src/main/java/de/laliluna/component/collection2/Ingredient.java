package de.laliluna.component.collection2;

import org.hibernate.annotations.Parent;

import javax.persistence.Embeddable;
@Embeddable
public class Ingredient {


	private String name;

	@Parent
	private Pizza client;

	public Ingredient() {

	}

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pizza getClient() {
		return client;
	}

	public void setClient(Pizza client) {
		this.client = client;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
