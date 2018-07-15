package de.laliluna.blob;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Types;
import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.type.BlobType;

@Entity
public class Image implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_gen")
	@SequenceGenerator(name = "image_gen", sequenceName = "image_id_seq")
	private Integer id;

	private String name;

	@Lob
	private byte imageAsBlob[];

	// alternatively you can use Blob as well
	private Blob imageAsBlob2;

	@Type(type = "org.hibernate.type.BinaryType")
	private byte imageAsBytea[];

	public Image() {

	}

	public Image(String name) {
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

	public byte[] getImageAsBlob() {
		return imageAsBlob;
	}

	public void setImageAsBlob(byte[] imageAsBlob) {
		this.imageAsBlob = imageAsBlob;
	}

	public byte[] getImageAsBytea() {
		return imageAsBytea;
	}

	public void setImageAsBytea(byte[] imageAsBytea) {
		this.imageAsBytea = imageAsBytea;
	}

	public Blob getImageAsBlob2() {
		return imageAsBlob2;
	}

	public void setImageAsBlob2(Blob imageAsBlob2) {
		this.imageAsBlob2 = imageAsBlob2;
	}

	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
	}
}
