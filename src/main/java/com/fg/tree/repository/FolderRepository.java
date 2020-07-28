package com.fg.tree.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fg.tree.model.TxnDocumentDetail;

@Repository
public interface FolderRepository extends PagingAndSortingRepository<TxnDocumentDetail, Long> {

	//@Query("select t from TxnDocumentDetail t where user_access_id in (select m.userAccessId from MstUserAccess m where user_id = ?1 and module_id = ?2) and status = 'active'")
	//public Page<TxnDocumentDetail> getDocumentDetails(Integer userId, Integer moduleId, Pageable pageable) throws Exception;
	
	@Query("select t from TxnDocumentDetail t where user_access_id in (select m.userAccessId from MstUserAccess m where module_id = ?1) and status = 'active'")
	public Page<TxnDocumentDetail> getDocumentDetails(Integer moduleId, Pageable pageable) throws Exception;
	
	@Query("select t from TxnDocumentDetail t where user_access_id in (select m.userAccessId from MstUserAccess m where module_id IN (:modules) and status = 'active')")
	public Page<TxnDocumentDetail> getDetailsForReader(@Param("modules") List<Integer> moduleIds, Pageable pageable) throws Exception;
	
	/*@Query("select t.catName from MstCategory t where cat_id  = ?1")
	public String getCategoryName(Integer catId) throws Exception;
	
	@Query("select t.subCatName from MstSubCat t where sub_cat_id = ?1")
	public String getSubcategoryName(Integer subCatId) throws Exception;*/
	
	//Below Method Added By Harshil
	@Query("select t from TxnDocumentDetail t where user_access_id in (select m.userAccessId from MstUserAccess m where module_id = ?1) and status = 'active'")
	public Page<TxnDocumentDetail> getFolderDetailsForReader(Integer moduleId, Pageable pageable) throws Exception;
	
	//Below method By Bindhu
	@Query("select u from TxnDocumentDetail u where status = 'inactive' and created_by = ?1 and moduleId = ?2")
	public Page<TxnDocumentDetail> getInactiveFiles(String createdBy, int moduleId, Pageable pageable) throws Exception;
	
	@Query("select u from TxnDocumentDetail u where status = 'deactive' and moduleId = ?1")
	public Page<TxnDocumentDetail> getPreviousFiles(int moduleId, Pageable pageable);
	
	@Query("select u from TxnDocumentDetail u where doc_detail_id = ?1 and header_Id = ?2")
	public TxnDocumentDetail getHeaderDetailForEdit(String docDetailId, String headerId);
	

	
	
}
