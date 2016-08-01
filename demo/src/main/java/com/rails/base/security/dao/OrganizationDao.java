package com.rails.base.security.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rails.base.security.domain.Organization;


public interface OrganizationDao extends JpaRepository<Organization, String>{
	
	Page<Organization> queryPage(String loginName,String userName, Pageable pageable);

	List<Organization> findByParentIdOrderByPriorityDesc(String id);
	
}
