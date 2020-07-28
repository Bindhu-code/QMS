package com.fg.tree.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.fg.tree.model.MstContent;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstUserAccess;
import com.fg.tree.model.ResponseVO;
import com.fg.tree.model.TxnDocumentDetail;

public interface IDocumentDetailDAO {
	
	public Page<TxnDocumentDetail> saveDocumentDetails(TxnDocumentDetail documentDetail) throws Exception;
	
	public List<TxnDocumentDetail> downloadDocument(String docName, Integer userId, Integer moduleId, String fileLocation) throws Exception;
	
	public List<TxnDocumentDetail> downloadFileDocforReader(String docName, Integer moduleId, String fileLocation) throws Exception;
	
	public Integer searchFileDetail(String fileLocation, String fileName, Integer userId) throws Exception;
	
	public List<MstUserAccess> searchHierarchy(Integer userId) throws Exception;
	
	public List<ResponseVO> createDirectory(MstModule module) throws Exception;
	
	public String getFileLocation(Integer moduleId) throws Exception;
	
	public Map<Integer, String> searchUserAccessId(Integer userId, Integer moduleId) throws Exception;

//	public MstContent saveContent(String data, String createdBy);

	//public MstContent saveContent(MstContent mstContent);

	public void updateContent(String data, String createdBy);

	public MstContent  showContent();

	public void removeDependentFiles(int moduleid);

	public void deletetxnfiles(int moduleId);

	public List<TxnDocumentDetail> getFiles(int moduleId);

}
