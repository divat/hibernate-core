
package de.laliluna.inheritance.tableperclass;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MusicBand {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "musicgroup_seq")
    @SequenceGenerator(name = "musicgroup_seq", sequenceName = "musicgroup_id_seq")
    private Integer id;

    private String name;

    public MusicBand() {

    }

    public MusicBand(String name) {
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

    public String toString() {
	return "MusicGroup: " + getId() + " Name: " + getName();
    }
}
