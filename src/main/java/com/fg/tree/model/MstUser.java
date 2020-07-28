package com.fg.tree.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the mst_user database table.
 * 
 */
@Entity
@Table(name = "mst_user")
public class MstUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private int userId;

	private String contact;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_login_id")
	private Timestamp lastLoginId;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private MstRole role;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_status")
	private int userStatus;
	
	@Column(name = "pin_id")
	private int pinId;
	
	

	@Column(name = "pin_created_date",insertable = false)
	private Timestamp pinCreatedDate;

	public MstUser() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Timestamp getLastLoginId() {
		return this.lastLoginId;
	}

	public void setLastLoginId(Timestamp lastLoginId) {
		this.lastLoginId = lastLoginId;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MstRole getRole() {
		return this.role;
	}

	public void setRole(MstRole roleId) {
		this.role = roleId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getPinId() {
		return pinId;
	}

	public void setPinId(int pinId) {
		this.pinId = pinId;
	}

	public Timestamp getPinCreatedDate() {
		return pinCreatedDate;
	}

	public void setPinCreatedDate(Timestamp pinCreatedDate) {
		this.pinCreatedDate = pinCreatedDate;
	}

	@Override
	public String toString() {
		return "MstUser [userId=" + userId + ", contact=" + contact + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", emailId=" + emailId + ", firstName=" + firstName + ", lastLoginId=" + lastLoginId
				+ ", lastName=" + lastName + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate
				+ ", password=" + password + ", roleId=" + role + ", userName=" + userName + ", userStatus="
				+ userStatus + ", pinId=" + pinId + ", pinCreatedDate=" + pinCreatedDate + "]";
	}

	

}