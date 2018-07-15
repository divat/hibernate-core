package de.laliluna.other.mapkey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name = "beach_of_the_world")
public class Beach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "beach")
    @MapKey(name = "isoCode")
    private Map<String, Country> countries = new HashMap<String, Country>();

    @OneToMany
    @JoinTable(name = "beach_other_country")
    @MapKeyColumn(name = "otherIsoCode")
    private Map<String, Country> othercountries = new HashMap<String, Country>();

    public Beach() {

    }

    public Beach(String name) {
        this.name = name;
    }

    public Map<String, Country> getCountries() {
        return countries;
    }

    public void setCountries(Map<String, Country> countries) {
        this.countries = countries;
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

    public Map<String, Country> getOthercountries() {
        return othercountries;
    }

    public void setOthercountries(Map<String, Country> othercountries) {
        this.othercountries = othercountries;
    }

    public String toString() {
        return "Beach: " + getId() + " Name: " + getName();
    }
}
