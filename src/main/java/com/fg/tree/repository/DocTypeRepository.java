package com.fg.tree.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fg.tree.model.MstDocType;

public interface DocTypeRepository extends JpaRepository<MstDocType, Long> {

}
