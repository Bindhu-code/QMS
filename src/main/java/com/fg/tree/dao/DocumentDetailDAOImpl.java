package com.fg.tree.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fg.tree.constant.CommonConstant;
import com.fg.tree.model.ErrorMessageVO;
import com.fg.tree.model.MstContent;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstUser;
import com.fg.tree.model.MstUserAccess;
import com.fg.tree.model.ResponseVO;
import com.fg.tree.model.SuccessMessageVO;
import com.fg.tree.model.TxnDocumentDetail;
import com.fg.tree.repository.ContentRepository;
import com.fg.tree.repository.DocumentDetailRepository;
import com.fg.tree.repository.FolderRepository;
import com.fg.tree.repository.LoginRepository;
import com.fg.tree.repository.ModuleRepository;
import com.fg.tree.repository.UserAccessRepository;

@Service
public class DocumentDetailDAOImpl implements IDocumentDetailDAO {

	@Autowired
	DocumentDetailRepository documentDetailRepository;
	
	@Autowired
	ModuleRepository moduleRepository;		
	
	@Autowired
	FolderRepository folderRepository;
	
	@Autowired
	UserAccessRepository userAccessRepository;
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	ContentRepository contentrepository;
	
	public Page<TxnDocumentDetail> saveDocumentDetails(TxnDocumentDetail documentDetail) throws Exception {

		Page<TxnDocumentDetail> documentDetails = null;
		
		TxnDocumentDetail detail = documentDetailRepository.save(documentDetail);
		if (detail.getUserAccessId() > 0) {
			
			MstUserAccess mstUserAccess = userAccessRepository.getDetailByUserAccessId(documentDetail.getUserAccessId());
			
			if (mstUserAccess != null) {
				documentDetails = folderRepository.getDocumentDetails(mstUserAccess.getMstModule().getModuleId(), new PageRequest(0, 4));
			}
		}
		return documentDetails;
	}
	
	public List<MstUserAccess> searchHierarchy(Integer userId) throws Exception {

		List<MstUserAccess> modules = null;
	//	modules = moduleRepository.searchHierarchy(userId);
		modules=userAccessRepository.searchHierarchy(userId);
		return modules;
	}
	
	public List<TxnDocumentDetail> downloadDocument(String fileName, Integer userId, Integer moduleId, String fileLocation) throws Exception{
		
		List<TxnDocumentDetail> documentDetail = null;
		documentDetail = documentDetailRepository.getDocumentFullPath(fileName, userId, moduleId, fileLocation);
		return documentDetail;	
		
	}
	
	public List<TxnDocumentDetail> downloadFileDocforReader(String fileName, Integer moduleId, String fileLocation) throws Exception {

		List<TxnDocumentDetail> documentDetail = null;
		documentDetail = documentDetailRepository.downloadFileDocforReader(fileName, moduleId, fileLocation);
		return documentDetail;

	}
	
	public Integer searchFileDetail(String fileLocation, String fileName, Integer userId) throws Exception {

		Integer count = documentDetailRepository.searchFileDetail(fileLocation, fileName);
		/*
		 * Previous file should be In-active as per business guide-lines.
		*/
		documentDetailRepository.updateFileDetail(fileLocation, fileName);
		return count;
	}

	public List<ResponseVO> createDirectory(MstModule module) throws Exception {

		List<ResponseVO> responseVOs = new ArrayList<ResponseVO>();
		ResponseVO responseVO = new ResponseVO();
		SuccessMessageVO successMessageVO = new SuccessMessageVO();
		ErrorMessageVO errorMessageVO = new ErrorMessageVO();
		MstModule module2 = moduleRepository.searchDirectory(module.getModuleName());
		if (module2 != null) {
			errorMessageVO.setErrorCode(-101);
			errorMessageVO.setErrorMessage(CommonConstant.ALREADY_AVAILABLE_MODULE);
			responseVO.setSuccessMessageVO(successMessageVO);
			responseVOs.add(responseVO);
		} else {
			MstModule mstModule = moduleRepository.save(module);
			if (mstModule != null) {
				successMessageVO.setSuccessCode(101);
				successMessageVO.setSuccessMessage(CommonConstant.CREATE_MODULE);
				responseVO.setSuccessMessageVO(successMessageVO);
				responseVOs.add(responseVO);
			}
		}
		return responseVOs;
	}
	
	/*public String getFileLocation(Integer moduleId) throws Exception{
		
		MstModule parentDirMod = moduleRepository.getFileLocation(moduleId);
		String fileLoc = null;
		StringBuffer stringBuffer = new StringBuffer();
		if (parentDirMod != null) {
			if (!parentDirMod.getModuleName().equalsIgnoreCase("#")) {
				stringBuffer.append(parentDirMod.getModuleName() + "/");
				List<String> childName = moduleRepository.getChildDirectory(String.valueOf(parentDirMod.getModuleId()), moduleId);
				for (String string : childName) {
					stringBuffer.append(string + "/");
				}
			}
		}else{
			List<String> childName = moduleRepository.getParentDirectory(moduleId);
			if(childName.size()>0){
				for (String string : childName) {
					stringBuffer.append(string + "/");
				}
			}

		}
		fileLoc = stringBuffer.toString();
		return fileLoc;
	}*/
	
	public String getFileLocation(Integer moduleId) throws Exception{
		
		String fileLocation = null;
		String test = moduleRepository.getFolderPathAllLevel(moduleId);
	
		StringBuffer finalPath = new StringBuffer();
		List<String> items = Arrays.asList(test.split("\\s*,\\s*"));
		
		for (int i = items.size() - 1; i >= 0; i--) {
			if(items.get(i) != null && !items.get(i).equalsIgnoreCase("null")){
				String s = items.get(i);
				finalPath.append( s + "/");
			}
		}
		//System.out.println("finalPath-->" + finalPath);
		fileLocation = finalPath.toString();
		return fileLocation;
	}
	
	public Map<Integer, String> searchUserAccessId(Integer userId, Integer moduleId) throws Exception {
		
		Map<Integer, String> returnVal = new HashMap<Integer, String>();
		Integer userAccessId = userAccessRepository.searchUserAccessId(userId, moduleId);
		/*
		 * To set user-name in transaction details table 
		*/
		MstUser mstUser = loginRepository.findById(userId);
		if (mstUser != null) {
			returnVal.put(userAccessId, mstUser.getUserName());
		}
		return returnVal;
	}

	@Override
	public void updateContent(String data, String createdBy) {
		contentrepository.updateContent(data,createdBy);
	}

	@Override
	public MstContent showContent() 
	{
		return contentrepository.showData();
		
	}

	@Override
	public void removeDependentFiles(int moduleList) {
	
			documentDetailRepository.removeDependentFiles(moduleList);
		
	}

	@Override
	public void deletetxnfiles(int moduleId) {
		
			documentDetailRepository.deletetxnfiles(moduleId);
		
		
	}

	@Override
	public List<TxnDocumentDetail> getFiles(int moduleId) {
		
		List<TxnDocumentDetail> txnfiles = documentDetailRepository.getFiles(moduleId);
		
		return txnfiles;
	}



}
