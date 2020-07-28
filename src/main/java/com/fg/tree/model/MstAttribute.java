package com.fg.tree.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

/**
 * The persistent class for the mst_module database table.
 * 
 */
@Entity
@Table(name = "mst_attribute")
@Transactional
@NamedQueries({ @NamedQuery(name = "MstAttribute.findAll", query = "SELECT m FROM MstAttribute m") })
public class MstAttribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "attribute_Id")
	private int attributeId;

	@Column(name = "attribute_Name")
	private String attributeName;
	
	@OneToMany()
	private List<MstAttributeConfig> mstAttributeConfig;
	
	
	public MstAttribute() {
	}
	
	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public List<MstAttributeConfig> getMstAttributeConfig() {
		return mstAttributeConfig;
	}

	public void setMstAttributeConfig(List<MstAttributeConfig> mstAttributeConfig) {
		this.mstAttributeConfig = mstAttributeConfig;
	}

	
	

}