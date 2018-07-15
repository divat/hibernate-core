package de.laliluna.other.mapkey;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="country_table")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String isoCode;

    private String name;

    @ManyToOne
    private Beach beach;

    public Country() {

    }

    public Country(String name) {
	this.name = name;
    }

    public Country(String isoCode, String name) {
	this.isoCode = isoCode;
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
	return "Country: " + getId() + " Name: " + getName();
    }

    public String getIsoCode() {
	return isoCode;
    }

    public void setIsoCode(String isoCode) {
	this.isoCode = isoCode;
    }

    public Beach getBeach() {
        return beach;
    }

    public void setBeach(Beach beach) {
        this.beach = beach;
    }
}
