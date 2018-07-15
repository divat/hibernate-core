package de.laliluna.other.query;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.MessageFormat;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;


@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_id_seq",
        allocationSize = 500)
    private Integer id;

    private String name;

    @Lob
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;

    private int pages;

    @ManyToMany
    private Set<BookCategory> bookCategories = new HashSet<BookCategory>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    @BatchSize(size = 1)
//  @Fetch(FetchMode.SUBSELECT)
    private Set<Chapter> chapters = new HashSet<Chapter>();


    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(String name, int pages) {
        this.name = name;
        this.pages = pages;
    }

    public String toString() {
        return MessageFormat.format("{0}: id={1} name={2} content={3} " +
            "pages={4}", new Object[]{getClass().getSimpleName(), id, name,
            content, pages});
    }

    public Set<BookCategory> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(Set<BookCategory> bookCategories) {
        this.bookCategories = bookCategories;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
