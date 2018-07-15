package de.laliluna.relation.recursive;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "treeitem_seq", sequenceName = "treeitem_id_seq")
public class TreeItem {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "treeitem_seq")
	private Integer id;

	@ManyToOne
	@JoinColumn(name="parent_id")
	private TreeItem parent;

	private String name;

	@OneToMany(mappedBy="parent", cascade = CascadeType.ALL)
	private Set<TreeItem> children = new HashSet<TreeItem>();

	public TreeItem() {

	}

	public TreeItem(Integer id, String name) {
		this.id = null;
		this.name = name;
	}

	public Set<TreeItem> getChildren() {
		return children;
	}

	public void setChildren(Set<TreeItem> children) {
		this.children = children;
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

	public TreeItem getParent() {
		return parent;
	}

	public void setParent(TreeItem parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "TreeItem: " + getId() + " Name: " + getName();
	}

}
