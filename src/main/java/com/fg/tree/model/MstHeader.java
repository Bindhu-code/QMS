package com.fg.tree.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "mst_header")
@Transactional
@NamedQueries({ @NamedQuery(name = "MstHeader.findAll", query = "SELECT m FROM MstHeader m") })
public class MstHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "header_Id")
	private int headerId;

	@Column(name = "header_Name")
	private String headerName;
	
	@OneToMany()
	private List<MstAttributeConfig> mstAttributeConfig;
	
	@OneToMany()
	private List<TxnDocumentDetail> txnDocumentDetail;
	

	public MstHeader() {
	}
	
	

	public int getHeaderId() {
		return headerId;
	}



	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}



	public String getHeaderName() {
		return headerName;
	}



	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}


	public List<MstAttributeConfig> getMstAttributeConfig() {
		return mstAttributeConfig;
	}



	public void setMstAttributeConfig(List<MstAttributeConfig> mstAttributeConfig) {
		this.mstAttributeConfig = mstAttributeConfig;
	}



	public List<TxnDocumentDetail> getTxnDocumentDetail() {
		return txnDocumentDetail;
	}



	public void setTxnDocumentDetail(List<TxnDocumentDetail> txnDocumentDetail) {
		this.txnDocumentDetail = txnDocumentDetail;
	}



	@Override
	public String toString() {
		return "MstHeader [headerId=" + headerId + ", headerName=" + headerName + "]";
	}


}