/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-26
 */
package com.rails.base.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.Role;

public interface RoleService {

	Page<Role> queryPage(String organizationId,String roleName, String project, Pageable pageable);
	
	List<Role> query(String organizationId, String roleName, String project);

	Role Save(Role role);

	void delete(String id);

	Role SaveResourceXref(String roleId,String resourcesId);

	Role SaveOrganizationXref(String roleId, String organizationsId);


}
