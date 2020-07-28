package com.fg.tree.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

/**
 * The persistent class for the txn_document_detail database table.
 * 
 */
@Entity
@Table(name = "txn_document_detail")
@NamedQuery(name = "TxnDocumentDetail.findAll", query = "SELECT t FROM TxnDocumentDetail t")
@Transactional
public class TxnDocumentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "doc_detail_id")
	private int docDetailId;

	@Column(name = "doc_name")
	private String docName;

	@Column(name = "doc_number")
	private String docNumber;

	@Column(name = "filename")
	private String fileName;

	@Column(name = "doc_path")
	private String docPath;

	@Column(name = "doc_Type")
	private String docType;
	
	@Column(name="status")
	private String status;
	
	@Column(name="Brand")
	private String Brand;
	
	
	/*@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	private MstUser user;*/
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "header_Id")
	private MstHeader mstHeader;
	
	@Column(name = "category_Name")
	private String categoryName;
	
	@Column(name = "sub_Category_Name")
	private String subCategoryName;

	@Column(name = "user_access_id")
	private int userAccessId;
	

	@Column(name = "module_id")
	private int moduleId;

	@DateTimeFormat(pattern = "dd/MM/YYYY")
	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "created_by")
	private String createdBy;
	
	
	
	@Column(name="article_code")
	private String articleCode;
	
	@Column(name="product_details")
	private String productDetails;
	
	@Column(name="supplier_code")
	private String supplierCode;
	
	@Column(name="3p_manufacture_name")
	private String Manufacturename;
	
	@Column(name="address")
	private String Address;
	
	@Column(name="region")
	private String Region;
	
	
	@Column(name="contact_person")
	private String ContactPerson;
	
	@Column(name="contact_number")
	private String ContactNumber;
	
	@Column(name="rawmaterial_description")
	private String rawmaterialDesc;
	
	@Column(name="rawmaterial_suppliername")
	private String rawmaterialSuppliername;
	
	@Column(name="packagingmaterial_desc")
	private String packagingMaterialDesc;
	
	@Column(name="packagingmaterial_supname")
	private String packagingMaterialSupName;
	
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "audit_date")
	private String auditDate;
	
	
	/*@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "next_due")
	private LocalDate nextDueDate;*/
	

	@Column(name = "created_date")
	private String createdDate;
	
	
	@Column(name = "next_due")
	private String nextDueDate;
	
	@Column(name="audit_score")
	private String auditScore;
	
	@Column(name="audit_status")
	private String auditStatus;
	
	@Column(name="certification_available")
	private String Certificationavailable;
	
	@Column(name="distribution_center_code")
	private String distributionCenterCode;
	
	@Column(name="distribution_center_location")
	private String distributionCenterLocation;
	
	@Column(name="doc_status")
	private String docstatus;
	
	@Column(name="active")
	private String active;
	
	@Column(name="rawmaterial_suppliercode")
	private String rawmaterialSupcode;
	
	@Column(name="rawmaterial_articlecode")
	private String rawmaterialArtcode;
	
	@Column(name="packagingmaterial_articlecode")
	private String packagingmaterialartcode;
	
	@Column(name="packagingmaterial_suppliercode")
	private String packagingmaterialsupcode;
	
	@Column(name="category_2")
	private String cat2;
	
	@Column(name="sub_category_2")
	private String subcat2;
	
	@Column(name="brand2")
	private String brand2;
	
	@Column(name="new_3p_manufacturers_name")
	private String new3pmanufacture;
	
//	private String srNo;
//	
//	public String getSrNo() {
//		return srNo;
//	}
//
//
//
//	public void setSrNo(String srNo) {
//		this.srNo = srNo;
//	}



	public TxnDocumentDetail() {
	}

	

	public String getCat2() {
		return cat2;
	}

	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}

	public String getSubcat2() {
		return subcat2;
	}

	public void setSubcat2(String subcat2) {
		this.subcat2 = subcat2;
	}

	public String getBrand2() {
		return brand2;
	}

	public void setBrand2(String brand2) {
		this.brand2 = brand2;
	}

	public String getNew3pmanufacture() {
		return new3pmanufacture;
	}

	public void setNew3pmanufacture(String new3pmanufacture) {
		this.new3pmanufacture = new3pmanufacture;
	}

	public String getPackagingmaterialartcode() {
		return packagingmaterialartcode;
	}

	public void setPackagingmaterialartcode(String packagingmaterialartcode) {
		this.packagingmaterialartcode = packagingmaterialartcode;
	}

	public String getPackagingmaterialsupcode() {
		return packagingmaterialsupcode;
	}

	public void setPackagingmaterialsupcode(String packagingmaterialsupcode) {
		this.packagingmaterialsupcode = packagingmaterialsupcode;
	}

	public String getRawmaterialSupcode() {
		return rawmaterialSupcode;
	}

	public void setRawmaterialSupcode(String rawmaterialSupcode) {
		this.rawmaterialSupcode = rawmaterialSupcode;
	}

	public String getRawmaterialArtcode() {
		return rawmaterialArtcode;
	}

	public void setRawmaterialArtcode(String rawmaterialArtcode) {
		this.rawmaterialArtcode = rawmaterialArtcode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	public String getCertificationavailable() {
		return Certificationavailable;
	}

	public void setCertificationavailable(String certificationavailable) {
		Certificationavailable = certificationavailable;
	}

	public String getDistributionCenterCode() {
		return distributionCenterCode;
	}

	public void setDistributionCenterCode(String distributionCenterCode) {
		this.distributionCenterCode = distributionCenterCode;
	}

	public String getDistributionCenterLocation() {
		return distributionCenterLocation;
	}

	public void setDistributionCenterLocation(String distributionCenterLocation) {
		this.distributionCenterLocation = distributionCenterLocation;
	}

	

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	
	public String getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}



	public String getAuditScore() {
		return auditScore;
	}

	public void setAuditScore(String auditScore) {
		this.auditScore = auditScore;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getPackagingMaterialDesc() {
		return packagingMaterialDesc;
	}

	public void setPackagingMaterialDesc(String packagingMaterialDesc) {
		this.packagingMaterialDesc = packagingMaterialDesc;
	}

	public String getPackagingMaterialSupName() {
		return packagingMaterialSupName;
	}

	public void setPackagingMaterialSupName(String packagingMaterialSupName) {
		this.packagingMaterialSupName = packagingMaterialSupName;
	}

	public String getRawmaterialDesc() {
		return rawmaterialDesc;
	}

	public void setRawmaterialDesc(String rawmaterialDesc) {
		this.rawmaterialDesc = rawmaterialDesc;
	}

	public String getRawmaterialSuppliername() {
		return rawmaterialSuppliername;
	}

	public void setRawmaterialSuppliername(String rawmaterialSuppliername) {
		this.rawmaterialSuppliername = rawmaterialSuppliername;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getDocDetailId() {
		return this.docDetailId;
	}

	public void setDocDetailId(int docDetailId) {
		this.docDetailId = docDetailId;
	}


	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocNumber() {
		return this.docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getDocPath() {
		return this.docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserAccessId() {
		return this.userAccessId;
	}

	public void setUserAccessId(int userAccessId) {
		this.userAccessId = userAccessId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public MstHeader getMstHeader() {
		return mstHeader;
	}

	public void setMstHeader(MstHeader mstHeader) {
		this.mstHeader = mstHeader;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategorysName() {
		return subCategoryName;
	}

	public void setSubCategorysName(String subCategorysName) {
		this.subCategoryName = subCategorysName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;		
	}
	
	

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getManufacturename() {
		return Manufacturename;
	}

	public void setManufacturename(String manufacturename) {
		Manufacturename = manufacturename;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getContactPerson() {
		return ContactPerson;
	}

	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}

	

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "TxnDocumentDetail [docDetailId=" + docDetailId + ", docName=" + docName + ", docNumber=" + docNumber
				+ ", fileName=" + fileName + ", docPath=" + docPath + ", docType=" + docType + ", status=" + status
				+ ", Brand=" + Brand + ", mstHeader=" + mstHeader + ", categoryName=" + categoryName
				+ ", subCategoryName=" + subCategoryName + ", userAccessId=" + userAccessId + ", moduleId=" + moduleId
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", articleCode=" + articleCode
				+ ", productDetails=" + productDetails + ", supplierCode=" + supplierCode + ", Manufacturename="
				+ Manufacturename + ", Address=" + Address + ", Region=" + Region + ", ContactPerson=" + ContactPerson
				+ ", ContactNumber=" + ContactNumber + ", rawmaterialDesc=" + rawmaterialDesc
				+ ", rawmaterialSuppliername=" + rawmaterialSuppliername + ", packagingMaterialDesc="
				+ packagingMaterialDesc + ", packagingMaterialSupName=" + packagingMaterialSupName + ", auditDate="
				+ auditDate + ", createdDate=" + createdDate + ", nextDueDate=" + nextDueDate + ", auditScore="
				+ auditScore + ", auditStatus=" + auditStatus + ", Certificationavailable=" + Certificationavailable
				+ ", distributionCenterCode=" + distributionCenterCode + ", distributionCenterLocation="
				+ distributionCenterLocation + ", docstatus=" + docstatus + ", active=" + active
				+ ", rawmaterialSupcode=" + rawmaterialSupcode + ", rawmaterialArtcode=" + rawmaterialArtcode
				+ ", packagingmaterialartcode=" + packagingmaterialartcode + ", packagingmaterialsupcode="
				+ packagingmaterialsupcode + ", cat2=" + cat2 + ", subcat2=" + subcat2 + ", brand2=" + brand2
				+ ", new3pmanufacture=" + new3pmanufacture + "]";
	}

	
}