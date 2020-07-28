package com.fg.tree.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fg.tree.model.MstCategory;
import com.fg.tree.model.MstDocType;
import com.fg.tree.model.MstHeader;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstSubCat;
import com.fg.tree.model.TxnDocumentDetail;

public interface IFolderDAO {
	
	public Page<TxnDocumentDetail> getFolderDetails(Integer moduleId, Pageable pageable) throws Exception;
	
	public Page<TxnDocumentDetail> getDetailsForReader(List<Integer> moduleIds, Pageable pageable) throws Exception;
	
	public List<MstCategory> getCategory() throws Exception;
	
	public List<MstSubCat> getSubCategory() throws Exception;
	
	public List<MstDocType> getDocType() throws Exception;
	
//	public String getCategoryName(Integer catId) throws Exception;
	
//	public String getSubCategoryName(Integer subCatId) throws Exception;
	
	public Page<TxnDocumentDetail> getFolderDetailsForReader(Integer moduleId, Pageable pageable) throws Exception;

	public List<MstHeader> getHeaders() throws Exception;
	
	public TxnDocumentDetail getHeaderDetailForEdit(String docDetailId, String headerId);

	
}
