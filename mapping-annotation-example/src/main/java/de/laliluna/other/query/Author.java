package de.laliluna.other.query;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;


@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Cigar cigar;
    
    @OneToMany(mappedBy="author")
    private Set<Book> books = new HashSet<Book>();

    public Author() {
    }

    public Author(String name) {
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

    public Cigar getCigar() {
        return cigar;
    }

    public void setCigar(Cigar cigar) {
        this.cigar = cigar;
    }

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
    
    
    
}
