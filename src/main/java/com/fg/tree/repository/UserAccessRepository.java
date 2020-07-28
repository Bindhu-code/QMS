
package com.fg.tree.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.model.MstUserAccess;

public interface UserAccessRepository extends JpaRepository<MstUserAccess, Long> {

	@Query("select u from MstUserAccess u where userId=?1 and mstModule.moduleId=?2 ")
	public MstUserAccess isUserAccessExists(int userId, int moduleId) throws Exception;

	@Query("select m from MstUserAccess m  where m.userId = ?1")
	public List<MstUserAccess> searchHierarchy(Integer userId) throws Exception;

	@Query("select m.userAccessId from MstUserAccess m  where m.userId = ?1 and mstModule.moduleId=?2")
	public Integer searchUserAccessId(Integer userId, Integer moduleId) throws Exception;

	@Query("select m from MstUserAccess m  where m.userAccessId = ?1")
	public MstUserAccess getDetailByUserAccessId(Integer userAccessId) throws Exception;

	@Transactional
	@Modifying
	@Query("delete from MstUserAccess m where m.userId = :userId and mstModule.moduleId IN (:moduleId)")
	public void revokeAccess(@Param("userId") int userId, @Param("moduleId") List<Integer> moduleId);
	
	
	@Transactional
	@Modifying
	@Query("delete from MstUserAccess m where m.userId = :userId and mstModule.moduleId IN (:moduleId)")
	public int revokeAccessmass(@Param("userId") int userId, @Param("moduleId") Integer moduleId);

	
	@Transactional
	@Modifying
	@Query("delete from MstUserAccess m where mstModule.moduleId in (:moduleList)")
	public void removeDependency(@Param("moduleList") int moduleList);

	
	@Transactional
	@Modifying
	@Query("delete from MstUserAccess m where mstModule.moduleId = ?1")
	public void deleteuseraccess(int moduleId);
	
	
	@Transactional
	@Modifying
	@Query("delete from MstUserAccess m where user_id = ?1")
	public void removeUserAccess(int userId);
	
	
	@Transactional
	@Modifying
	@Query("delete from MstUser m where user_id = ?1")
	public void removeUser(int userId);
	
	
	@Query("select m from MstUserAccess m  where m.userId = ?1")
	public List<MstUserAccess> getAllFoldersAccessed(Integer userId) throws Exception;
	
	
	
	@Transactional
	@Modifying
	@Query("delete from MstUserAccess m where m.userId = :userId and mstModule.moduleId NOT IN (:moduleId)")
	public int removeAccess(@Param("userId") int userId, @Param("moduleId") List<Integer> moduleId);

	
	@Transactional
	@Modifying
	@Query("Update MstUserAccess m set m.view = :view , m.download=:download where mstModule.moduleId = :moduleId and m.userId= :userId ")
	public int updateViewandDownload(@Param("view")Boolean view, @Param("download")Boolean download,@Param("moduleId")int moduleId,@Param("userId")int userId);
	
	
	
	
}
