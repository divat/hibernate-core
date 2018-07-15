package de.laliluna.other.query;

import javax.persistence.*;


@Entity
public class BookCategory {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "BookCategory{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    public BookCategory() {
    }

    public BookCategory(String name) {
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
}
