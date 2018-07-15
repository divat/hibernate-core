package de.laliluna.inheritance.singletable;

import javax.persistence.Entity;

@Entity
public class Tree extends Plant {

	private boolean hasFruits;

	public Tree(){
		super();
	}
	public Tree(Integer id, String name,boolean hasFruits){
		super(id,name);
		this.hasFruits=hasFruits;
	}
	public boolean isHasFruits() {
		return hasFruits;
	}

	public void setHasFruits(boolean hasFruits) {
		this.hasFruits = hasFruits;
	}
	
	@Override
	public String toString() {
		return "Tree: "+getId()+" Name:  "+getName()+" hasFruits: "+hasFruits;
	}
}
