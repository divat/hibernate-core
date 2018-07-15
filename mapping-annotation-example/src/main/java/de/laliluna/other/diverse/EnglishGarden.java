
package de.laliluna.other.diverse;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

/**
 * @author hennebrueder This mapping shows
 * 
 * <pre>
 *   Table and SecondaryTable 
 *   indexes and unique key constraints.
 * </pre>
 * 
 * Column annotations for
 * 
 * <pre>
 *    transient
 *    enum
 *    date, time, timestamp
 *    CLOB
 *    Version
 *    column, basic
 * </pre>
 */
// the following declaration is redundant, The name would have been Forest
// anyway.
@Entity()
@Table(name = "english_garden", uniqueConstraints = { @UniqueConstraint(columnNames = { "unique_column" }) })
@org.hibernate.annotations.Table(appliesTo = "english_garden", indexes = { @Index(name = "forestidx", columnNames = { "indexedcolumn" }) })
@SecondaryTable(name = "tableName2", pkJoinColumns = { @PrimaryKeyJoinColumn(name = "id_2", referencedColumnName = "id", columnDefinition = "int4") })
public class EnglishGarden {



	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "unique_column")
	private String uniqueColumn;

	private String indexedColumn;

	@Column(table = "tableName2")
	private String secondaryTableColumn;

	@Transient
	private String transientField;

	/* temporal types */
	@Temporal(value = TemporalType.DATE)
	private Date dateField;

	@Temporal(TemporalType.TIME)
	private Date timeField;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestampField;

	@Lob
	private String largeField;

	/* enum types */
	public enum GardenType {
		HOUSE_GARDEN, PARK, FARM_GARDEN
	}

	@Enumerated(EnumType.STRING)
	private GardenType gardenType;

	public enum GardenSize {
		SMALL, MEDIUM, LARGE
	}


    @Enumerated(EnumType.ORDINAL)
	private GardenSize gardenSize;

	/* we can only have one version field */
	@Version
	private Long version;

	@Basic(fetch = FetchType.EAGER, optional = true)
	@Column(name = "full_described_field", unique = false, nullable = true, insertable = true, updatable = true, columnDefinition = "varchar(255)", table = "tableName2", length = 255, precision = 0, scale = 0)
	private String fullDescribedField;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @LazyToOne(LazyToOneOption.PROXY)
    private HeadGardener headGardener;

    @OneToMany
    @JoinColumn
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    //@javax.persistence.OrderBy(value = "name desc, id asc")
    //@Where(clause = "name like '%e%'")
    //@OrderBy(clause = "name desc")
    @BatchSize(size = 2)
    //@Fetch(value = FetchMode.SUBSELECT.JOIN)
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    private List<Tulip> noBlueTulips = new ArrayList<Tulip>();

    public EnglishGarden() {
		super();
	}

	@Override
	public String toString() {                 
		return MessageFormat
				.format(
						"{0}: id={1}, uniqueColumn={2}, indexedColumn={3}, uniqueColumn={4}, secondaryTableColumn={5}, dateField={6}, " +
						"timeField={7}, timestampField={8}, largeField={9}, gardenType={10}, gardenSize={11}, version={12}, fullDescribedField={13}",
						new Object[] { getClass().getSimpleName(), id,
								uniqueColumn, indexedColumn,
								secondaryTableColumn, dateField, timeField,
								timestampField, largeField, gardenType,
								gardenSize, version, fullDescribedField });
	}
    public List<Tulip> getTulips() {
            return noBlueTulips;
        }

        public void setTulips(List<Tulip> tulips) {
            this.noBlueTulips = tulips;
        }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUniqueColumn() {
		return uniqueColumn;
	}

	public void setUniqueColumn(String uniqueColumn) {
		this.uniqueColumn = uniqueColumn;
	}

	public String getSecondaryTableColumn() {
		return secondaryTableColumn;
	}

	public void setSecondaryTableColumn(String secondaryTable) {
		this.secondaryTableColumn = secondaryTable;
	}

	public String getIndexedColumn() {
		return indexedColumn;
	}

	public void setIndexedColumn(String indexedColumn) {
		this.indexedColumn = indexedColumn;
	}

	public String getTransientField() {
		return transientField;
	}

	public void setTransientField(String transientField) {
		this.transientField = transientField;
	}

	public String getFullDescribedField() {
		return fullDescribedField;
	}

	public void setFullDescribedField(String fullDescribedField) {
		this.fullDescribedField = fullDescribedField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	public String getLargeField() {
		return largeField;
	}

	public void setLargeField(String largeField) {
		this.largeField = largeField;
	}

	public GardenType getGardenType() {
		return gardenType;
	}

	public void setGardenType(GardenType forestType) {
		this.gardenType = forestType;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public GardenSize getGardenSize() {
		return gardenSize;
	}

	public void setGardenSize(GardenSize gardenSize) {
		this.gardenSize = gardenSize;
	}

	public Date getTimeField() {
		return timeField;
	}

	public void setTimeField(Date timeField) {
		this.timeField = timeField;
	}

	public Date getTimestampField() {
		return timestampField;
	}

	public void setTimestampField(Date timestampField) {
		this.timestampField = timestampField;
	}

    public HeadGardener getHeadGardener() {
        return headGardener;
    }

    public void setHeadGardener(HeadGardener headGardener) {
        this.headGardener = headGardener;
    }
}
