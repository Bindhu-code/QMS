 package com.fg.tree.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.model.TxnDocumentDetail;

@Repository
public interface DocumentDetailRepository extends JpaRepository<TxnDocumentDetail, Long> {

	@Query("select u from TxnDocumentDetail u where filename like ?1% and user_access_id in (select m.userAccessId from MstUserAccess m where user_id = ?2 and module_id = ?3) and doc_path = ?4")
	public List<TxnDocumentDetail> getDocumentFullPath(String fileName, Integer userId, Integer moduleId, String fileLocation);

	//@Query("select count(u) from TxnDocumentDetail u where doc_path = ?1 and filename like ?2% and user_access_id in (select m.userAccessId from MstUserAccess m where user_id = ?3)")
	//public Integer searchFileDetail(String fileLocation, String fileName, Integer userId);
	
	@Query("select count(u) from TxnDocumentDetail u where doc_path = ?1 and filename like ?2%")
	public Integer searchFileDetail(String fileLocation, String fileName);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update TxnDocumentDetail u set status = 'deactive' where doc_path =:filelocation and filename like :filename%")
	public void updateFileDetail(@Param("filelocation") String fileLocation, @Param("filename") String fileName);
	
	@Query("select u from TxnDocumentDetail u where filename like ?1% and module_id = ?2 and doc_path = ?3")
	public List<TxnDocumentDetail> downloadFileDocforReader(String fileName, Integer moduleId, String fileLocation);
	
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update TxnDocumentDetail u set status='inactive' where docDetailId in(:filenames )")
	public void documentsInactive(@Param("filenames")Integer filenames);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update MstUserAccess u set download= :download, view= :viewFlag where user_id= :userId and module_id = :moduleId")
	public void updateFolderDetails(@Param("userId")Integer userId,@Param("moduleId")Integer moduleId,@Param("download")Boolean download,@Param("viewFlag")Boolean viewFlag);
	
	@Query("select t.download from MstUserAccess t where mstModule.moduleId = ?1 and userId= ?2")
	public Boolean downloadAllowed(Integer moduleId,Integer userId) throws Exception;
	
	@Query("select t.view from MstUserAccess t where mstModule.moduleId = ?1 and userId= ?2")
	public Boolean viewAllowed(Integer moduleId,Integer userId) throws Exception;

	@Modifying
	@Transactional
	@Query("delete from TxnDocumentDetail u where u.moduleId IN (:moduleList)")
	public void removeDependentFiles(@Param("moduleList") int moduleList);

	@Modifying
	@Transactional
	@Query("delete from TxnDocumentDetail u where u.moduleId = ?1")
	public void deletetxnfiles(@Param("moduleId") int moduleId);
	
	@Query("select u from  TxnDocumentDetail u where u.moduleId = ?1")
	public List<TxnDocumentDetail> getFiles(int moduleId);	

}
