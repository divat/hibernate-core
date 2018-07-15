package de.laliluna.other.query;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;


@Entity
public class Cigar {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public Cigar() {
    }

    public Cigar(String name) {
        this.name = name;
    }

    public String toString() {
        return MessageFormat.format("{0}: id={1} name={2}", new Object[]{getClass().getSimpleName(), id, name});
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
}
