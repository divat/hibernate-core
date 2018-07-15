package de.laliluna.blob;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

@Entity
public class Document implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="document_gen")
	@SequenceGenerator(name="document_gen", sequenceName="document_id_seq")
	private Integer id;

	private String name;

	@Lob
	private String text;

	public Document() {

	}

	public Document(String name) {
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

	/**
     * @return the text
     */
	public String getText() {
		return text;
	}

	/**
     * @param text
     *            the text to set
     */
	public void setText(String text) {
		this.text = text;
	}
}
