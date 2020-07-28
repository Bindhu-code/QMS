package com.fg.tree.dao;

import java.util.List;

import com.fg.tree.model.MstModule;

public interface IModuleDAO {

	MstModule addParent(MstModule mstModule) throws Exception;

	List<MstModule> getParentFolderNames() throws Exception;

	MstModule addChild(MstModule mstModule) throws Exception;

	void  deleteChildFolder(int i) throws Exception;

	void deleteParentFolder(Integer moduleId);

	List<MstModule> getDependentChildFolders(List<String> getModuleIds);

	MstModule getParent(int moduleId);

	MstModule getChild(int moduleId);

	MstModule getParentId(String name);

	

	void deleteSubFolders(Integer moduleId);
	

}
