
package de.laliluna.inheritance.mappedsuperclass;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "house_seq", sequenceName = "house_id_seq")
public class House {



	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_seq")
	private Integer id;

	private String name;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "house_kitchenmouse", joinColumns = { @JoinColumn(name = "house_id") }, inverseJoinColumns = { @JoinColumn(name = "kitchenmouse_id") })
	private Set<KitchenMouse> kitchenMice = new HashSet<KitchenMouse>();

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "house_librarymouse", joinColumns = { @JoinColumn(name = "mouse_id") }, inverseJoinColumns = { @JoinColumn(name = "librarymouse_id") })
	private Set<LibraryMouse> libraryMice = new HashSet<LibraryMouse>();

	public House() {

	}

	public House(String name) {
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



	public Set<KitchenMouse> getKitchenMice() {
		return kitchenMice;
	}

	public void setKitchenMice(Set<KitchenMouse> kitchenmice) {
		this.kitchenMice = kitchenmice;
	}

	public Set<LibraryMouse> getLibraryMice() {
		return libraryMice;
	}

	public void setLibraryMice(Set<LibraryMouse> libraryMice) {
		this.libraryMice = libraryMice;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
	}

}
