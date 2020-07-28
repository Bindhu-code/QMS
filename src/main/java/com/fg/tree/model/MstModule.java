package com.fg.tree.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

/**
 * The persistent class for the mst_module database table.
 * 
 */
@Entity
@Table(name = "mst_module")
@Transactional
@NamedQueries({ @NamedQuery(name = "MstModule.findAll", query = "SELECT m FROM MstModule m") })
public class MstModule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "module_id")
	private int moduleId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "parent_id")
	private String parentId;

	/*@OneToMany()
	private List<MstUserAccess> mstUserAccess;

	

	public List<MstUserAccess> getMstUserAccess() {
		return mstUserAccess;
	}

	public void setMstUserAccess(List<MstUserAccess> mstUserAccess) {
		this.mstUserAccess = mstUserAccess;
	}
*/
	public MstModule() {
	}

	public int getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
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

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "MstModule [moduleId=" + moduleId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", moduleName=" + moduleName
				+ ", parentId=" + parentId + "]";
	}


}