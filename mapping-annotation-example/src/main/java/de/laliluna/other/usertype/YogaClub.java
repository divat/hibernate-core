package de.laliluna.other.usertype;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@TypeDefs(value = { @TypeDef(name = "keyType", typeClass = KeyType.class) })
@Entity
public class YogaClub {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yogaclub_gen")
	@SequenceGenerator(name = "yogaclub_gen", sequenceName = "yogaclub_id_seq")
	private Integer id;

	@Type(type = "keyType")
	private String name;

	public YogaClub() {

	}

	public YogaClub(String name) {
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
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
	}
}
