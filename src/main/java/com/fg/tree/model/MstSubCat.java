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
 * The persistent class for the mst_sub_cat database table.
 * 
 */
@Entity
@Table(name = "mst_sub_cat")
@NamedQuery(name = "MstSubCat.findAll", query = "SELECT m FROM MstSubCat m")
public class MstSubCat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "sub_cat_id")
	private int subCatId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Column(name = "sub_cat_name")
	private String subCatName;

	/*@OneToMany()
	private List<TxnDocumentDetail> txnDocumentDetail;*/

	public MstSubCat() {
	}

	public int getSubCatId() {
		return this.subCatId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getSubCatName() {
		return this.subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}

	/*public List<TxnDocumentDetail> getTxnDocumentDetail() {
		return txnDocumentDetail;
	}

	public void setTxnDocumentDetail(List<TxnDocumentDetail> txnDocumentDetail) {
		this.txnDocumentDetail = txnDocumentDetail;
	}*/

	@Override
	public String toString() {
		return "MstSubCat [subCatId=" + subCatId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", subCatName=" + subCatName + "]";
	}

}