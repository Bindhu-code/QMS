package com.fg.tree.model;

import java.util.Arrays;

public class AuthorizationvaluesVO {
	
	
	private String[] finalmoduleValues;
	private int userId;
	private Integer[] parentmodules;
	
	
	
	

	public String[] getFinalmoduleValues() {
		return finalmoduleValues;
	}
	public void setFinalmoduleValues(String[] finalmoduleValues) {
		this.finalmoduleValues = finalmoduleValues;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	public Integer[] getParentmodules() {
		return parentmodules;
	}
	public void setParentmodules(Integer[] parentmodules) {
		this.parentmodules = parentmodules;
	}
	@Override
	public String toString() {
		return "AuthorizationvaluesVO [finalmoduleValues=" + Arrays.toString(finalmoduleValues) + ", userId=" + userId
				+ ", parentmodules=" + Arrays.toString(parentmodules) + "]";
	}
	
	
	
	

}
