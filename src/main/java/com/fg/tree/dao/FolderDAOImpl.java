package com.fg.tree.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fg.tree.model.MstCategory;
import com.fg.tree.model.MstDocType;
import com.fg.tree.model.MstHeader;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstSubCat;
import com.fg.tree.model.TxnDocumentDetail;
import com.fg.tree.repository.CategotyRepository;
import com.fg.tree.repository.DocTypeRepository;
import com.fg.tree.repository.FolderRepository;
import com.fg.tree.repository.HeaderRepository;
import com.fg.tree.repository.SubCategotyRepository;

@Service
public class FolderDAOImpl implements IFolderDAO {

	@Autowired
	FolderRepository folderRepository;
	
	@Autowired
	SubCategotyRepository subCategotyRepository;
	
	@Autowired
	DocTypeRepository docTypeRepository;

	@Autowired
	CategotyRepository categotyRepository;
	
	@Autowired
	HeaderRepository headerRepository;

	@Override
	public Page<TxnDocumentDetail> getFolderDetails(Integer moduleId, Pageable pageable) throws Exception {
		
		//Page<TxnDocumentDetail> folderMappings = folderRepository.getDocumentDetails(userId, moduleId, pageable);
		Page<TxnDocumentDetail> folderMappings = folderRepository.getDocumentDetails(moduleId, pageable);
		return folderMappings;
	}
	
	@Override
	public Page<TxnDocumentDetail> getDetailsForReader(List<Integer> moduleIds, Pageable pageable) throws Exception {
		
		Page<TxnDocumentDetail> folderMappings = folderRepository.getDetailsForReader(moduleIds, pageable);
		return folderMappings;
	}

	@Override
	public List<MstCategory> getCategory() throws Exception {
		List<MstCategory> mstCategories = categotyRepository.findAll();
		return mstCategories;
	}
	
	@Override
	public List<MstHeader> getHeaders() throws Exception {
		List<MstHeader> mstHeaders = headerRepository.findAll();
		return mstHeaders;
	}
	
	@Override
	public List<MstSubCat> getSubCategory() throws Exception {
		List<MstSubCat> mstSubCategories = subCategotyRepository.findAll();
		return mstSubCategories;
	}
	
	@Override
	public List<MstDocType> getDocType() throws Exception {
		List<MstDocType> mstDocTypes = docTypeRepository.findAll();
		return mstDocTypes;
	}
	
	/*@Override
	public String getCategoryName(Integer catId) throws Exception {
		String catName = folderRepository.getCategoryName(catId);
		return catName;
	}
	
	@Override
	public String getSubCategoryName(Integer subCatId) throws Exception {
		String subCatName = folderRepository.getSubcategoryName(subCatId);
		return subCatName;
	}*/
	
	//Below Method Added By Harshil
	@Override
	public Page<TxnDocumentDetail> getFolderDetailsForReader(Integer moduleId, Pageable pageable) throws Exception {
		
		Page<TxnDocumentDetail> folderMappings = folderRepository.getFolderDetailsForReader(moduleId, pageable);
		return folderMappings;
	}

	@Override
	public TxnDocumentDetail getHeaderDetailForEdit(String docDetailId, String headerId) {
		TxnDocumentDetail tr = folderRepository.getHeaderDetailForEdit(docDetailId, headerId);
		return tr;
	}

	
}
