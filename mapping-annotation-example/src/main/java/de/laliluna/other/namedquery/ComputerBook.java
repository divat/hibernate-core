package de.laliluna.other.namedquery;

import org.hibernate.annotations.*;
import org.hibernate.annotations.FlushModeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;


@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "bookReport", entities = {@EntityResult
        (entityClass = ComputerBook.class,
            fields = {@FieldResult(name = "id", column = "id"),
                @FieldResult(name = "name", column = "book_name")})}),
    @SqlResultSetMapping(name = "bookReport2", entities = {@EntityResult
        (entityClass = ComputerBook.class,
            fields = {@FieldResult(name = "id", column = "id"),
                @FieldResult(name = "name", column = "name")})},
        columns = {@ColumnResult(name = "count_group")})
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "reportSql",
        query = "select b.id, b.book_name, (select count(1) as count_group " +
            "from " +
            "computerbook where book_name = b.book_name) as count_group " +
            "from computerbook b", resultSetMapping = "bookReport2")})

@NamedQueries({@NamedQuery(name = "bookQuery", query = "from ComputerBook b "
    +
    "where b.id > :minId and b.name = :name",
    hints = {
        @QueryHint(name = "org.hibernate.readOnly", value = "false"),
        @QueryHint(name = "org.hibernate.timeout", value = "5000")})
    })
@org.hibernate.annotations.NamedQueries({@org.hibernate.annotations
    .NamedQuery(name = "alternativeQuery", query = "from ComputerBook b " +
    "where" +
    " b.id > :minId and b.name = :name", flushMode = FlushModeType.AUTO,
    cacheable = true, cacheRegion = "", fetchSize = 20, timeout = 5000,
    comment = "A comment", cacheMode = CacheModeType.NORMAL,
    readOnly = true)})
public class ComputerBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "book_name")
    private String name;

    @OneToMany
    @JoinColumn(name = "computerbook_fk")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Lab> labs = new HashSet<Lab>();

    /* I will use this attribute for reporting with namednativequeris */
    @Transient
    private int countGroup;

    public int getCountGroup() {
        return countGroup;
    }

    public void setCountGroup(int countGroup) {
        this.countGroup = countGroup;
    }

    public ComputerBook() {
    }

    public ComputerBook(String name) {
        this.name = name;
    }

    public String toString() {
        return MessageFormat.format("{0}: id={1} name={2}",
            new Object[]{getClass().getSimpleName(), id, name});
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Lab> getLabs() {
        return labs;
    }

    public void setLabs(Set<Lab> labs) {
        this.labs = labs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}