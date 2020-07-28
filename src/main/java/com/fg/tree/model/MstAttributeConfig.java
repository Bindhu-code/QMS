package com.fg.tree.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "mst_attribute_config")
@NamedQuery(name = "MstAttributeConfig.findAll", query = "SELECT m FROM MstAttributeConfig m")
@Transactional
public class MstAttributeConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "config_id")
	private int configId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_Id")
	private MstAttribute mstAttribute;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "header_Id")
	private MstHeader mstHeader;
	
	@Column(name = "field_value")
	private String fieldValue;
	
	@Column(name = "field_value_id")
	private String fieldValueId;
	
	@Column(name = "length")
	private String length;
	
	@Column(name = "type")
	private Character type;

	public MstAttributeConfig() {
	}

	

	public int getConfigId() {
		return configId;
	}



	public void setConfigId(int configId) {
		this.configId = configId;
	}



	public MstAttribute getMstAttribute() {
		return mstAttribute;
	}

	public void setMstAttribute(MstAttribute mstAttribute) {
		this.mstAttribute = mstAttribute;
	}

	public MstHeader getMstHeader() {
		return mstHeader;
	}

	public void setMstHeader(MstHeader mstHeader) {
		this.mstHeader = mstHeader;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}



	public String getFieldValueId() {
		return fieldValueId;
	}



	public void setFieldValueId(String fieldValueId) {
		this.fieldValueId = fieldValueId;
	}
	
	
	

}
