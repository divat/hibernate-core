
package de.laliluna.inheritance.tableperclass;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class MusicConsumer {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "musicfan_seq")
    @SequenceGenerator(name = "musicfan_seq", sequenceName = "musicfan_id_seq")
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(name = "musicgroup_musicfan", joinColumns = { @JoinColumn(name = "musicfan_id") }, inverseJoinColumns = { @JoinColumn(name = "musicgroup_id") })
    private Set<MusicBand> musicGroups = new HashSet<MusicBand>();

    public MusicConsumer() {

    }

    public MusicConsumer(String name) {
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

    public Set<MusicBand> getMusicGroups() {
	return musicGroups;
    }

    public void setMusicGroups(Set<MusicBand> musicGroups) {
	this.musicGroups = musicGroups;
    }

    public String toString() {
	return "MusicFan: " + getId() + " Name: " + getName();
    }

}
