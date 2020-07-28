package com.fg.tree.dao;

import java.util.List;

import com.fg.tree.model.MstRole;
import com.fg.tree.model.MstUser;
import com.fg.tree.model.MstUserAccess;

public interface IUserDAO {

	public MstUser getUserDetail(String username, String password) throws Exception;

	public List<MstRole> getRole() throws Exception;
	public List<MstUser> getAllUsers() throws Exception;
	public void updateUserDetail(String firstname,String lastname,String contact,String emailid,int roleid,int userid) throws Exception;
	public boolean isUserExists(String userName)throws Exception;
	
	public boolean isUserAccessExists(int userId,int moduleId)throws Exception;
	
	public void saveUserAccess(MstUserAccess userAccess)throws Exception;
	
	public MstUser getUderDataFromrole(int role)throws Exception;
	
	public void updatePassword(String password,String username) throws Exception;
	
	public MstUser getUserDataFromUserName(String userName)throws Exception;
	
	public boolean sendEmailForForgotPassword(MstUser userVO);


	public void removeDependentFolderUseraccess(int moduleid);

	public void deleteuseraccess(int moduleId);

	/*Start : Below mwthod Added By Bindhu For Give Folder Rights*/
	public MstUserAccess addUseraccessDetails(MstUserAccess useraccess);

	List<MstUser> getAllFilereadersUserId();

	/*End : Below mwthod Added By Bindhu For Give Folder Rights*/
}
