package com.fg.tree.repository;

import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.model.LoginHistory;
import com.fg.tree.model.ReportDetailsVO;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

	
	@Modifying
	@Transactional
	@Query(value="insert into mst_login_history(user_id,login_time,login_date,systemIP) Values (:userID,:timeSql,:dateSql,:loggedInUserSystemIP)",nativeQuery = true)
	void saveDetails(@Param("userID") int userID,@Param("timeSql") Time timeSql, @Param("dateSql") Date dateSql,@Param("loggedInUserSystemIP") String loggedInUserSystemIP);

	
	
	@Query(value="select * from mst_login_history  where user_id=:userId and login_date=:date ORDER BY Id DESC limit 1",nativeQuery=true)
	public LoginHistory getloginDetailsofUser(@Param("userId") int userID, @Param("date") Date dateSql);

	
	
	@Modifying
	@Transactional
	@Query("update LoginHistory t set t.loginTime = ?1 where t.loginDate=?2 and t.user.userId=?3 ")
	public void updateTime(@Param("timeSql") Time timeSql,@Param("dateSql") Date dateSql,@Param("userID") int userID);
	
	
	@Modifying
	@Transactional
	@Query("delete from LoginHistory t where t.user.userId=?1 ")
	public void deletefromLoginHistory(@Param("userId") int userId);
	

	@Query("select  max(t.loginDate) from LoginHistory t where t.user.userId=?1 ")
	public Date getMaxDate(int userID);
	

	@Query("select max(t.loginTime) from LoginHistory t where t.user.userId=?1 ")
	public String getMaxDateAndTime(@Param("userId") int userId);
	
	
	@Query(" SELECT "
			+ " NEW com.fg.tree.model.ReportDetailsVO(u.userName,u.firstName,u.lastName,u.emailId,u.role.roleName,u.contact,l.loginDate,l.loginTime,l.docPath,l.fileName,l.Action,l.systemIP) "
			+ " FROM com.fg.tree.model.MstUser u, com.fg.tree.model.LoginHistory l "
			+ " WHERE u.userId = l.user.userId "
			+ " AND l.loginDate BETWEEN  :startdate AND :enddate ORDER BY u.userName")	
	public List<ReportDetailsVO> getReportDetails(@Param("startdate") Date date, @Param("enddate")Date date2);


	@Modifying
	@Transactional
	@Query("update LoginHistory t set t.docPath=:fileLocation ,t.fileName=:finalFile,t.Action=:filestatus where t.loginDate=:logindate AND t.loginTime=:logintime ")
	void saveLoginDetails(@Param("fileLocation") String fileLocation, @Param("finalFile") String finalFile, @Param("logindate") Date logindate,@Param("logintime") Date logintime,@Param("filestatus") String fileStatusUpload);

	@Query(value="SELECT * from mst_login_history where user_id =:userId order by id desc limit 1 ",nativeQuery=true)
	public LoginHistory getDocumentDetails(@Param("userId") int userId);

	
	@Modifying
	@Transactional
	@Query("delete from LoginHistory t where t.user.userId=?1 and t.loginDate=?2 and t.loginTime =?3")
	void deletePreviousLogindetails(int userID, Date dateSql, Date time);

	@Query(" SELECT "
			+ " NEW com.fg.tree.model.ReportDetailsVO(u.userName,u.firstName,u.lastName,u.emailId,u.role.roleName,u.contact,l.loginDate,l.loginTime,l.docPath,l.fileName,l.Action,l.systemIP) "
			+ " FROM com.fg.tree.model.MstUser u, com.fg.tree.model.LoginHistory l "
			+ " WHERE u.userId = l.user.userId "
			+ " AND l.user.userId = :userId "
			+ " AND l.loginDate BETWEEN  :startdate AND :enddate ORDER BY u.userName")	
	public List<ReportDetailsVO> getReportDetailswithUserId(@Param("startdate") Date startDate, @Param("enddate")Date endDate,@Param("userId") int userId);


	@Query("select m from LoginHistory m where m.user.userId=:userId and"
			+ " (m.Action=:action  OR :action IS NULL)  and"
			+ " (m.fileName=:filename OR :filename IS NULL) ")
	List<LoginHistory> testForDynamicQuery1(@Param("userId") int userId, @Param("action")String action,@Param("filename") String filename);


	@Query(" SELECT "
			+ " NEW com.fg.tree.model.ReportDetailsVO(u.userName,u.firstName,u.lastName,u.emailId,u.role.roleName,u.contact,l.loginDate,l.loginTime,l.docPath,l.fileName,l.Action,l.systemIP) "
			+ " FROM com.fg.tree.model.MstUser u, com.fg.tree.model.LoginHistory l "
			+ " WHERE u.userId = l.user.userId "
			+ " AND (l.user.userId = :userId OR :userId = '')"
			+ " AND (u.role.roleName = :rolename OR :rolename IS NULL)"
			+ " AND (l.Action = :action OR :action IS NULL)"
			+ " AND (l.loginDate BETWEEN  :startdate AND :enddate) ORDER BY u.userName")	
	public List<ReportDetailsVO> testForDynamicQuery2(@Param("userId") int userId,@Param("rolename") String rolename,@Param("action") String action,@Param("startdate") Date startDate, @Param("enddate")Date endDate );
	
}


