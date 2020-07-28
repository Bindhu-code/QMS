package com.fg.tree.dao;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.constant.CommonConstant;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstUser;
import com.fg.tree.repository.ModuleCrudRepository;
import com.fg.tree.repository.ModuleRepository;



@Service
public class ModuleDAOImpl implements IModuleDAO {

	
	@Autowired
	ModuleRepository modulerepository;
	
	@Autowired
	ModuleCrudRepository modulecrudrepository;

	@Override
	public MstModule addParent(MstModule mstModule) throws Exception {

		String name = mstModule.getModuleName();

		MstModule module = modulerepository.save(mstModule);

		File theDir = new File(CommonConstant.FILE_PATH_FOLDER);
		if (!theDir.exists()) {
			theDir.mkdir();
		}

	//	File newFile = new File(CommonConstant.FILE_PATH_FOLDER + "\\" + name);
		File newFile = new File(CommonConstant.FILE_PATH_FOLDER + name);
		System.out.println("FilePath:"+newFile);
		
		System.out.println("My File Exists ::::" + newFile.mkdir());
		
		
		
		return module;
	}

	@Override
	public List<MstModule> getParentFolderNames() throws Exception {
		List<MstModule> modulelist = modulerepository.getParentFolderNames();
		return modulelist;
	}

	@Override
	public MstModule addChild(MstModule mstModule) throws Exception {

		MstModule module = modulerepository.save(mstModule);

		return module;
	}


	@Override
	@Transactional
	public void deleteChildFolder(int Id) throws Exception {
		
		System.out.println("Am here................");
		 int i=modulerepository.deleteChildFolder(Id);
		
	}

	@Override
	public void deleteSubFolders(Integer moduleId) {
	
		modulerepository.deleteSubFolders(moduleId);
	}

	@Override
	public List<MstModule> getDependentChildFolders(List<String> moduleId) {
		
	List<MstModule> modulelist  = 	modulerepository.getdependentChildfolders(moduleId);
		
		return modulelist;
	}
	
	@Override
	public MstModule getParent(int moduleId) {
		MstModule parentfolder = modulerepository.getParent(moduleId);
		
		
		return parentfolder;
	}

	@Override
	public MstModule getChild(int moduleId) {
	
		MstModule childfoldername =  modulerepository.getChild(moduleId);
		
		return childfoldername;
	}
	
	@Override
	public MstModule getParentId(String name) {
		
		MstModule parentmoduleId = modulerepository.getParentId(name);
		return parentmoduleId;
	}

	@Override
	public void deleteParentFolder(Integer moduleId) {
		
		
		modulerepository.deleteParentFolder(moduleId);
		
	}

	
	

	

}
