package com.fg.tree.model;

import java.io.Serializable;

public class SuccessMessageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int successCode;
	private String successMessage;

	public int getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(int successCode) {
		this.successCode = successCode;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	@Override
	public String toString() {
		return "SuccessMessageVO [successCode=" + successCode + ", successMessage=" + successMessage + "]";
	}

}
