package com.fg.tree.model;

import java.io.Serializable;

public class ResponseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SuccessMessageVO successMessageVO;

	private ErrorMessageVO errorMessageVO;

	public SuccessMessageVO getSuccessMessageVO() {
		return successMessageVO;
	}

	public void setSuccessMessageVO(SuccessMessageVO successMessageVO) {
		this.successMessageVO = successMessageVO;
	}

	public ErrorMessageVO getErrorMessageVO() {
		return errorMessageVO;
	}

	public void setErrorMessageVO(ErrorMessageVO errorMessageVO) {
		this.errorMessageVO = errorMessageVO;
	}

	@Override
	public String toString() {
		return "ResponseVO [successMessageVO=" + successMessageVO + ", errorMessageVO=" + errorMessageVO + "]";
	}

}
