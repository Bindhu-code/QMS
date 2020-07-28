package com.fg.tree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fg.tree.model.MstRole;

@Repository
public interface RoleRepository extends JpaRepository<MstRole,Long>{

	
}
