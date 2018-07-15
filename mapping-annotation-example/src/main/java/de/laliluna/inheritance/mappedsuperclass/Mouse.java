
package de.laliluna.inheritance.mappedsuperclass;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public class Mouse {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mouse_seq")
    private Integer id;

    private String name;

    public Mouse() {

    }

    public Mouse(String name) {
	this.name = name;
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
