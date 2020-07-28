package com.fg.tree.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fg.tree.model.MstBatch;

public interface BatchPageRepository extends PagingAndSortingRepository<MstBatch, Long> {

	
	
	@Query("select b from MstBatch b ")
	public Page<MstBatch> getAllBatchData(Pageable pageable);
	
	
	
	
	
	

}