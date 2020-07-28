package com.fg.tree.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the mst_category database table.
 * 
 */
@Entity
@Table(name = "mst_category")
@NamedQuery(name = "MstCategory.findAll", query = "SELECT m FROM MstCategory m")
public class MstCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cat_id")
	private int catId;

	@Column(name = "cat_name")
	private String catName;

	@Column(name = "ceated_by")
	private String ceatedBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;
		
	public MstCategory() {
	}

	public int getCatId() {
		return this.catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return this.catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCeatedBy() {
		return this.ceatedBy;
	}

	public void setCeatedBy(String ceatedBy) {
		this.ceatedBy = ceatedBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/*@Override
	public String toString() {
		return "MstCategory [catId=" + catId + ", catName=" + catName + ", ceatedBy=" + ceatedBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}
*/
	

	
}