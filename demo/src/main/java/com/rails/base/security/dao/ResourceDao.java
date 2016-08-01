package com.rails.base.security.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.User;


public interface ResourceDao extends JpaRepository<Resource, String>{
	
	List<Resource> findByParentIdOrderByPriorityDesc(String id);

	Page<Resource> queryPage(String parentId, String resourceName, Pageable pageable);
	
}
