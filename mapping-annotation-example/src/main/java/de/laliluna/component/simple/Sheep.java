
package de.laliluna.component.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Sheep {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sheep_gen")
    @SequenceGenerator(name = "sheep_gen", sequenceName = "sheep_id_seq")
    private Integer id;

    private String name;

    @Embedded
    // we would like the color field of pullover to be mapped to a different
    // column
    @AttributeOverride(name = "color", column = @Column(name = "pullover_column"))
    private Pullover pullover;

    public Sheep() {
	super();
    }
	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
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

    public Pullover getPullover() {
	return pullover;
    }

    public void setPullover(Pullover pullover) {
	this.pullover = pullover;
    }

}
