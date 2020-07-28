package com.fg.tree.model;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mst_login_history")
public class LoginHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int Id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="user_id")
	private MstUser user;
	
	@Column(name="login_time")
	private Time loginTime;
	
	@Column(name="login_date")
	private Date loginDate;
	
	@Column(name="Action")
	private String Action;
	
	@Column(name="docpath")
	private String docPath;
	
	@Column(name="file_name")
	private String fileName;
	
	
	@Column(name="systemIP")
	private String systemIP;
	
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	public Time getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Time loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public MstUser getUserId() {
		return user;
	}

	public void setUserId(MstUser userId) {
		this.user = userId;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
	public String getSystemIP() {
		return systemIP;
	}

	public void setSystemIP(String systemIP) {
		this.systemIP = systemIP;
	}

	@Override
	public String toString() {
		return "LoginHistory [Id=" + Id + ", user=" + user + ", loginTime=" + loginTime + ", loginDate=" + loginDate
				+ ", Action=" + Action + ", docPath=" + docPath + ", fileName=" + fileName + ", systemIP=" + systemIP
				+ "]";
	}


}
