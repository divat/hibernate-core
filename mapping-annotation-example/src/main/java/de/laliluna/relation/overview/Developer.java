
package de.laliluna.relation.overview;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CascadeType;

/**
 * @author hennebrueder
 */
@Entity
@SequenceGenerator(name = "developer_seq", sequenceName = "developer_id_seq")
@AttributeOverrides( { @AttributeOverride(name = "developmentLanguages.key", column = @Column(name = "short_name")) })
public class Developer {
	/**
	 * 
	 */


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_seq")
	private Integer id;

	private String name;

	@ElementCollection
	@IndexColumn(name = "listindex")
	private int[] favouriteNumbers;

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "developer_id", nullable=false)
	@IndexColumn(name = "listindex")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "customRegion", include = "all")
    private List<Computer> computers = new ArrayList<Computer>();

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "developer_id")
	private Set<Hobby> hobbies = new HashSet<Hobby>();

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "developer_id")
	private Collection<Idea> ideas = new ArrayList<Idea>();

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "developer_id")
	@Sort(type = SortType.COMPARATOR, comparator = SportComparator.class)
	private SortedSet<Sport> sports = new TreeSet<Sport>(new SportComparator());

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "developer_id")
	@IndexColumn(name = "listindex")
	private JuneBeetle juneBeetles[];

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "developer_id")
	@IndexColumn(name = "index")
	private List<Dream> dreams = new ArrayList<Dream>(); // mapped with an
                                                            // index

	@ElementCollection
	@JoinTable(name = "development_languages", joinColumns = @JoinColumn(name = "developer_id"))
	@Column(name = "name", nullable = false)
	private Map<String, String> developmentLanguages = new HashMap<String, String>();

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "developer_id")
	@MapKey(name = "isocode")
	private Map<String, LovedCountry> lovedCountries = new HashMap<String, LovedCountry>();

	public Developer() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
	}

	public Set<Hobby> getHobbies() {
		return hobbies;
	}

	public void setHobbies(Set<Hobby> hobbies) {
		this.hobbies = hobbies;
	}

	public int[] getFavouriteNumbers() {
		return favouriteNumbers;
	}

	public void setFavouriteNumbers(int[] favouriteNumbers) {
		this.favouriteNumbers = favouriteNumbers;
	}

	public Map<String, String> getDevelopmentLanguages() {
		return developmentLanguages;
	}

	public void setDevelopmentLanguages(Map<String, String> developmentLanguages) {
		this.developmentLanguages = developmentLanguages;
	}

	public SortedSet<Sport> getSports() {
		return sports;
	}

	public void setSports(SortedSet<Sport> sports) {
		this.sports = sports;
	}

	public Map<String, LovedCountry> getLovedCountries() {
		return lovedCountries;
	}

	public void setLovedCountries(Map<String, LovedCountry> lovedCountries) {
		this.lovedCountries = lovedCountries;
	}

	public JuneBeetle[] getJuneBeetles() {
		return juneBeetles;
	}

	public void setJuneBeetles(JuneBeetle[] juneBeetles) {
		this.juneBeetles = juneBeetles;
	}

	public List<Dream> getDreams() {
		return dreams;
	}

	public void setDreams(List<Dream> dreams) {
		this.dreams = dreams;
	}

	public Collection<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(Collection<Idea> ideas) {
		this.ideas = ideas;
	}
	
	

}
