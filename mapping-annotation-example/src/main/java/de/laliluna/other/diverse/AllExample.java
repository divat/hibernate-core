
package de.laliluna.other.diverse;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Formula;

@Entity
@SequenceGenerator(name = "allexample_seq", sequenceName = "allexample_id_seq")
@AccessType("field")
public class AllExample {



	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allexample_seq")
	private Integer id;

	@Temporal(value = TemporalType.TIME)
	private Date myTime;

	@Temporal(value = TemporalType.DATE)
	private Date myDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date myTimeStamp;

	@Enumerated(value = EnumType.STRING)
	private DeveloperType developerType;

	@Lob
	private String longText;

	@Lob
	private byte buffer[];

	@Column(name = "myColumnName", unique = true, nullable = false, insertable = true, updatable = true, columnDefinition = "varchar (200)", length = 200)
	private String preciseField;

	// field is calculated by the database, id is the name of the table column
	@Formula("10 * id + 5")
	public Integer formula;

	public enum DeveloperType {
		BRILLIANT, COULD_IMPROVE, NORMLAL
	}

	public AllExample() {
		super();
	}

	@Override
	public String toString() {
		return MessageFormat.format("AllExample: {0} {1} {2} {3} {4} {5} {6} {7}",
				new Object[] { id, myTime, myDate, myTimeStamp, developerType,
						longText, new String(buffer), formula });
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public DeveloperType getDeveloperType() {
		return developerType;
	}

	public void setDeveloperType(DeveloperType developerType) {
		this.developerType = developerType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLongText() {
		return longText;
	}

	public void setLongText(String longText) {
		this.longText = longText;
	}

	public Date getMyDate() {
		return myDate;
	}

	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}

	public Date getMyTime() {
		return myTime;
	}

	public void setMyTime(Date myTime) {
		this.myTime = myTime;
	}

	public Date getMyTimeStamp() {
		return myTimeStamp;
	}

	public void setMyTimeStamp(Date myTimeStamp) {
		this.myTimeStamp = myTimeStamp;
	}

	public String getPreciseField() {
		return preciseField;
	}

	public void setPreciseField(String preciseField) {
		this.preciseField = preciseField;
	}

	public Integer getFormula() {
		return formula;
	}

	public void setFormula(Integer formula) {
		this.formula = formula;
	}

}
