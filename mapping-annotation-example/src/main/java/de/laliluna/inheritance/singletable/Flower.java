package de.laliluna.inheritance.singletable;

import javax.persistence.Entity;
@Entity
public class Flower extends Plant {

	private String color;

	public Flower(){
		super();
	}
	public Flower(Integer id, String name, String color){
		super(id, name);
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "Flower: "+getId()+" Name:  "+getName() + " Color:  "+color;
	}
}
