package com.fg.tree.model;

import java.sql.Timestamp;

public class TxnDocumentDetailVO {

	
	private int docDetailId;
	private String docName;
	private String docNumber;
	private String fileName;
	private String docPath;
	private String docType;
	private String status;
	private String Brand;
	
	
	
	private MstHeader mstHeader;
	private String categoryName;
	private String subCategoryName;
	private int userAccessId;
	private int moduleId;
	private Timestamp createdOn;
	private String createdBy;
	private String articleCode;
	private String productDetails;
	private String supplierCode;
	private String Manufacturename;
	private String Address;
	private String Region;
	
	
	
	private String ContactPerson;
	
	
	private String ContactNumber;
	
	
	private String rawmaterialDesc;
	
	
	private String rawmaterialSuppliername;
	
	
	private String packagingMaterialDesc;
	
	private String packagingMaterialSupName;
	
	
	private String auditDate;
	private String nextDueDate;
	
	
	private String auditScore;
	
	
	private String auditStatus;
	
	
	private String Certificationavailable;
	
	
	private String distributionCenterCode;
	
	private String distributionCenterLocation;
	
	
	private String docstatus;
	
	
	private String active;
	
	
	private String rawmaterialSupcode;
	
	
	private String rawmaterialArtcode;
	
	
	private String packagingmaterialartcode;
	
	
	private String packagingmaterialsupcode;
	
	
	private String cat2;
	
	
	private String subcat2;
	
	
	private String brand2;
	

	private String new3pmanufacture;
	

	
	

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

	
	
	
}
