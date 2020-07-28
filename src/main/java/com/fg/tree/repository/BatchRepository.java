package com.fg.tree.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fg.tree.model.MstBatch;

public interface BatchRepository extends JpaRepository<MstBatch,Long> {

	
	@Modifying
	@Transactional
	@Query(value="insert into mst_batch(start_date,end_date,status,user_id) values(:startdate,:enddate,:status,:userId)",nativeQuery = true)
	void saveDatedetails(@Param("startdate") Date startdate, @Param("enddate") Date enddate,@Param("status") String status,@Param("userId") int userId);

	@Query(value="SELECT * from mst_batch where status = 'pending' ",nativeQuery=true)
	public List<MstBatch> getDates();

	@Modifying
	@Transactional
	@Query("update MstBatch b set b.status=:status,b.docPath=:filepath,b.fileName=:filename where b.startDate=:startdate OR :startdate IS NULL and b.endDate=:enddate ")
	void updateStatusofBatch(@Param("startdate") Date startDate, @Param("enddate")Date endDate, @Param("filepath")String fileOut, @Param("status")String batchStatus,@Param("filename") String filename);


	@Query(value="SELECT * from mst_batch order by batch_id desc limit 1",nativeQuery=true)
	public MstBatch getLatestBatchId();
	

}
