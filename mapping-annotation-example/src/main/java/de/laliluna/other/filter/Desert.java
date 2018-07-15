package de.laliluna.other.filter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;

@Entity
@FilterDef(name = "region", parameters = {@ParamDef(name = "regionName", type = "string")})
@Filters({@Filter(name = "region", condition = "lower(c_name) like lower(:regionName)")
        })
public class Desert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "c_name")
    private String name;

    public Desert() {
    }

    public Desert(String name) {
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
