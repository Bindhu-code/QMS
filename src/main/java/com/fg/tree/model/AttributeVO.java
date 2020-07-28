package com.fg.tree.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class AttributeVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String attributeName;
	private String fieldId;
	private String fieldName;
	private String headerName;
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	@Override
	public String toString() {
		return "AttributeVO [attributeName=" + attributeName + ", fieldId=" + fieldId + ", fieldName=" + fieldName + "]";
	}
		
}
