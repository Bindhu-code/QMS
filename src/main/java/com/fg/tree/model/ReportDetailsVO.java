package com.fg.tree.model;

import java.io.Serializable;
import java.util.Date;
//import java.util.Time;

public class ReportDetailsVO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String firstname;
	private String lastname;
	private String emailId;
	private String contact;
	private String docpath;
	private String  roleName;
	private Date logintime;
	private Date logindate;
	private String filename;
	private String action;
	private String loggedInUsersystemIP;
	
	public ReportDetailsVO() {
	}

	public ReportDetailsVO(String username, String firstname, String lastname, String emailId, String roleName, String contact, Date logindate, Date logintime, String docpath, String filename,String action,String loggedInUsersystemIP) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailId = emailId;
		this.roleName = roleName;
		this.contact = contact;
		this.logindate = logindate;
		this.logintime = logintime;
		this.docpath = docpath;
		this.filename = filename;
		this.action = action;
		this.loggedInUsersystemIP = loggedInUsersystemIP;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDocpath() {
		return docpath;
	}

	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}

	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
		
	}
	

	public String getLoggedInUsersystemIP() {
		return loggedInUsersystemIP;
	}

	public void setLoggedInUsersystemIP(String loggedInUsersystemIP) {
		this.loggedInUsersystemIP = loggedInUsersystemIP;
	}

	@Override
	public String toString() {
		return "ReportDetailsVO [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", emailId=" + emailId + ", contact=" + contact + ", docpath=" + docpath + ", roleName=" + roleName
				+ ", logintime=" + logintime + ", logindate=" + logindate + ", filename=" + filename + ", action="
				+ action + ", loggedInUsersystemIP=" + loggedInUsersystemIP + "]";
	}
	
	
	
	
	

}
