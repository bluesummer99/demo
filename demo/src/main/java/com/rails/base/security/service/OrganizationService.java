package com.rails.base.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.Resource;

public interface OrganizationService {
	public Organization Save(Organization user) ;
	public Page<Organization> queryPage(String loginName,String userName, Pageable pageable);
	public void delete(String id);
	public Organization findOneWithChildren(String id);
}
