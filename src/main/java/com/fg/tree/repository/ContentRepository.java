package com.fg.tree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.model.MstContent;

public interface ContentRepository extends JpaRepository<MstContent, Long >{

	
	//update MstUser u set u.firstName = ?1 , u.lastName = ?2, u.contact=?3,u.emailId=?4 ,u.roleId=?5 where u.userId=?6")
	
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update MstContent m set m.content = ?1, m.createdBy = ?2 where contentId=1")
	public void updateContent(String data, String createdBy);
	
	@Query("select m from MstContent m where contentId=1")
	public MstContent showData();
	

}
