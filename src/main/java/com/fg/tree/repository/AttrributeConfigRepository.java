

package com.fg.tree.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fg.tree.model.MstAttributeConfig;

@Repository
public interface AttrributeConfigRepository extends JpaRepository<MstAttributeConfig, Long> {
		
	@Query(value="SELECT * from mst_attribute_config where header_Id=:headerId",nativeQuery=true)
	public List<MstAttributeConfig> getAllAttribute(@Param("headerId") Integer headerId)throws Exception;
	
	/*@Query(value="SELECT *,GROUP_CONCAT(field_value) from mst_attribute_config where header_Id=:headerId group by attribute_Id",nativeQuery=true)
	public List<MstAttributeConfig> getAllAttribute(@Param("headerId") Integer headerId)throws Exception;*/
	
	
}
