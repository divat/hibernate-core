package de.laliluna.component.collection1;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Hedgehog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "hedgehog_seq")
    @SequenceGenerator(name = "hedgehog_seq",
            sequenceName = "hedgehog_id_seq", allocationSize = 10)
    private Integer id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "hedgehog_winter_addresses", joinColumns = @JoinColumn
            (name = "hedgehog_id"))
    @OrderColumn(name = "list_index")
//    @IndexColumn(name = "list_index")
    private List<WinterAddress> addresses = new ArrayList<WinterAddress>();

    public Hedgehog() {

    }

    public Hedgehog(String name) {
        this.name = name;
    }

    public List<WinterAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<WinterAddress> addresses) {
        this.addresses = addresses;
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
        return "HedgeHog: " + getId() + " Name: " + getName();
    }

}
