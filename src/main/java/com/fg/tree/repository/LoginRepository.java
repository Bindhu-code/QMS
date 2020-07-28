package com.fg.tree.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fg.tree.model.MstUser;


@Repository
public interface LoginRepository extends JpaRepository<MstUser, Long> {

	@Query("select u as count from MstUser u where user_name = ?1 and password = ?2")
	public MstUser getUserDetail(String username, String password) throws Exception;
	
	@Modifying
	@Query("update MstUser u set u.firstName = ?1 , u.lastName = ?2, u.contact=?3,u.emailId=?4 ,u.role.roleId=?5 where u.userId=?6")
	public void updateUserDetail(String firstname,String lastname,String contact,String emailid,int roleid,int userid)throws Exception;
	
	
	@Query("select u from MstUser u where u.userName=?1")
	public MstUser isUserExists(String userName)throws Exception;
	
	@Query("select u from MstUser u where userId=?1")
	public MstUser findById(int id)throws Exception;
	
	@Query("select u from MstUser u where u.role.roleId=?1")
	public MstUser isSysAdminExists(int id)throws Exception;
	
	@Modifying
	@Query("update MstUser u set u.password = ?1  where u.userName=?2")
	public void updatePassword(String password,String username);
	
	@Query("select u from MstUser u where emailId=?1")
	public MstUser isEmailIdExists(String emailId)throws Exception;
	
	@Query("select u from MstUser u where emailId=?1 and u.userName!=?2")
	public MstUser isEmailIdExistsWithUser(String emailId,String userName)throws Exception;
	
	@Modifying
	@Query("update MstUser u set u.pinId = ?1, u.pinCreatedDate=CURRENT_TIMESTAMP   where u.userName=?2")
	public void updateOtp(int otp,String userName);
	
	@Query("select u from MstUser u where u.role.roleId = 3 ")
	public List<MstUser> getAllFilereadersUserId();
}
