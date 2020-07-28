package com.fg.tree.model;

import java.io.Serializable;

public class MstUserAccessVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private int moduleId;
	private Boolean flag;
	private Boolean viewflag;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public Boolean getViewflag() {
		return viewflag;
	}
	public void setViewflag(Boolean viewflag) {
		this.viewflag = viewflag;
	}
	
}
