package com.fg.tree.model;

import java.io.Serializable;

public class ErrorMessageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long errorCode;
	private String errorMessage;

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ErrorMessage [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
