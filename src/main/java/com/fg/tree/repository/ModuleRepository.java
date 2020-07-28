

package com.fg.tree.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.model.ModulesList;
import com.fg.tree.model.ModulesVO;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstUserAccess;

public interface ModuleRepository extends JpaRepository<MstModule, Long> {

	/*@Query("select m from MstModule m inner join m.mstUserAccess n where m.moduleId = n.moduleId and n.userId = ?1")
	public List<MstModule> searchHierarchy(Integer userId) throws Exception;*/
	
	@Query("select m from MstModule m where m.moduleId = ?1")
	public MstModule findbyParent(Integer parentId)throws Exception;
	
	@Query("select m from MstModule m where m.moduleName = ?1 and m.parentId='#'")
	public MstModule searchDirectory(String moduleName) throws Exception;
	
	@Query(value = "select * from mst_module where module_id = (select parent_id from mst_module m1 where module_id = :moduleId)", nativeQuery=true)
	public MstModule getFileLocation(@Param("moduleId") Integer moduleId) throws Exception;
	
	@Query("select m.moduleName from MstModule m where m.parentId = ?1 and m.moduleId = ?2")
	public List<String> getChildDirectory(String parentId, Integer moduleId) throws Exception;
	
	@Query("select m.moduleName from MstModule m where m.moduleId = ?1")
	public List<String> getParentDirectory(Integer moduleId) throws Exception;
	
	@Query("select m from MstModule m where m.parentId = ?1")
	public List<MstModule> getParentData(String parentId)throws Exception;
	
	@Query("select m from MstUserAccess m where m.userId = ?1 and mstModule.moduleId= ?2")
	public List<MstUserAccess> getParentData(int userId,int moduleId)throws Exception;
	
	@Query("select mstUser from MstUserAccess mstUser where userId = :userId  and mstModule.moduleId in (select m.moduleId from MstModule m where parentId = :moduleId)")
	public List<MstUserAccess> getParentDetails(@Param("moduleId")String moduleId,@Param("userId")int userId)throws Exception; 
	
	@Query("select m from MstModule m where parentId = '#' ")
	public List<MstModule> getParentFolderNames() throws Exception;
	
	@Transactional
    @Modifying
	@Query("delete from MstModule m where m.moduleId= ?1")
	public int deleteChildFolder(int moduleid);
	
	
	@Transactional
    @Modifying
	@Query("delete from MstModule m where m.moduleId= ?1" )
	public int deleteParentFolder(int moduleid);
	
	
	@Transactional
    @Modifying
	@Query("delete from MstModule m where m.moduleId IN(:moduleid)")
	public int deleteSubFolders(@Param("moduleid") int moduleid);
	
	@Query("select m from MstModule m where m.parentId IN(:moduleId)")
	public List<MstModule> getdependentChildfolders(@Param("moduleId") List<String> moduleId);
	
	@Query("select m from MstModule m where m.moduleId= ?1")
	public MstModule getParent(int moduleId);
	
	@Query("select m from MstModule m where m.moduleId= ?1")
	public MstModule getChild(int moduleId);
	
	/*@Query(value=" SELECT t1.moduleName AS lev4, t2.moduleName as lev3, t3.moduleName as lev2, t4.moduleName as lev1 FROM MstModule AS t1 LEFT join MstModule AS t2 ON t2.moduleId = t1.parentId LEFT join MstModule AS t3 ON t3.moduleId = t2.parentId LEFT join MstModule AS t4 ON t4.moduleId = t3.parentId WHERE t1.moduleId = ?1 ",
			nativeQuery = true)*/
	@Query(value=" SELECT t1.module_name AS lev4, t2.module_name as lev3, t3.module_name as lev2, t4.module_name as lev1 "
			+ " FROM mst_module AS t1 LEFT JOIN mst_module AS t2 ON t2.module_id = t1.parent_id LEFT JOIN mst_module AS t3 ON t3.module_id = t2.parent_id"
			+ " LEFT JOIN mst_module AS t4 ON t4.module_id = t3.parent_id WHERE t1.module_id = :moduleId ",
			nativeQuery = true)
	public String getFolderPathAllLevel(@Param("moduleId")int moduleId) throws Exception;
	
	@Query(value="SELECT  GROUP_CONCAT(distinct t1.module_id) as module, GROUP_CONCAT(distinct t2.module_id) as module11, GROUP_CONCAT(distinct t3.module_id) as module12,t4.module_id as moduleid4 FROM mst_module AS t1 LEFT JOIN mst_module AS t2 ON t2.parent_id = t1.module_id LEFT JOIN mst_module AS t3 ON t3.parent_id = t2.module_id\r\n" + 
			"LEFT JOIN mst_module AS t4 ON t4.parent_id = t3.module_id where  t1.parent_id =:moduleId ",nativeQuery=true)

	public String getAllchildModules(@Param("moduleId") int moduleId );
	
	@Query("select m from MstModule m where m.moduleName= ?1 and parentId = '#'")
	public MstModule getParentId(String name);

	//Added By Bindhu (9/7/2019)
	
	@Query("select m from MstModule m where m.moduleName=:foldername and m.parentId in (:moduleIds)")
	public MstModule searchFolder(@Param("moduleIds") String moduleId, @Param("foldername") String folderTobecreated);
	


	
	@Query(value="SELECT " + 
			"       t1.module_name AS lev1Folder, " + 			
			"       t2.module_name AS lev2Folder, " + 			 
			"       t3.module_name AS lev3Folder, " + 
			"       t4.module_name AS lev4Folder, " + 
			"       t1.module_id AS moduleid1, " + 
			"       t2.module_id AS moduleid2, " + 
			"       t3.module_id AS moduleid3, " + 
			"       t4.module_id AS moduleid4 " + 			
			"		FROM   mst_module AS t1 " + 
			"       LEFT JOIN mst_module AS t2 " + 
			"              ON t2.parent_id = t1.module_id" + 
			"       LEFT JOIN mst_module AS t3 " + 
			"              ON t3.parent_id = t2.module_id " + 
			"       LEFT JOIN mst_module AS t4 " + 
			"              ON t4.parent_id = t3.module_id " + 
			"WHERE  t1.parent_id = '#' ",nativeQuery = true)
			
	public List<ModulesList> getFolders() throws Exception;

	
/*@Query("SELECT "
		+ " NEW com.fg.tree.model.ModulesVO(t1.moduleName, t2.moduleName, t3.moduleName, t4.moduleName)"
		+ " FROM MstModule  t1 "  
		+ " LEFT JOIN MstModule  t2 "  
		+ " ON t2.parentId = t1.moduleId " 
		+ " LEFT JOIN MstModule  t3 " 
		+ " ON t3.parentId =  t2.moduleId " 
		+ " LEFT JOIN MstModule  t4 "
		+ " ON t4.parentId  = t3.moduleId" 
		+ " WHERE t1.parentId = '#'")
		public List<ModulesVO> getFolders() throws Exception;*/

	
	



	//@Query("SELECT new com.fg.tree.model.ModulesVO(mstMod1.moduleName, mstMod2.moduleName, mstMod3.moduleName, mstMod4.moduleName)" + 
	//		"FROM MstModule mstMod1, MstModule mstMod2, MstModule mstMod3, MstModule mstMod4 WHERE mstMod1.parentId = '#' AND mstMod1.moduleId = mstMod2.parentId AND mstMod2.moduleId = mstMod3.parentId AND mstMod3.moduleId = mstMod4.parentId")
	//public List<ModulesVO> getFolders() throws Exception;
	
	
	
	
	
}
