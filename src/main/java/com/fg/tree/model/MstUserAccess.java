package com.fg.tree.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The persistent class for the mst_user_access database table.
 * 
 */
@Entity
@Table(name = "mst_user_access")
@NamedQueries({ @NamedQuery(name = "MstUserAccess.getDocumentDetails", query = "SELECT m FROM MstUserAccess m") })
public class MstUserAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_access_id")
	private int userAccessId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	@JoinColumn(name = "module_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MstModule mstModule;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "is_download")
	private Boolean download;
	
	@Column(name = "is_view")
	private Boolean view;
	
	public Boolean getDownload() {
		return download;
	}

	public void setDownload(Boolean download) {
		this.download = download;
	}

	public MstUserAccess() {
	}

	public int getUserAccessId() {
		return this.userAccessId;
	}

	public void setUserAccessId(int userAccessId) {
		this.userAccessId = userAccessId;
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

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public MstModule getMstModule() {
		return mstModule;
	}

	public void setMstModule(MstModule mstModule) {
		this.mstModule = mstModule;
	}

	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return "MstUserAccess [userAccessId=" + userAccessId + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", mstModule="
				+ mstModule + ", userId=" + userId + ", download=" + download + ", view=" + view + "]";
	}

}