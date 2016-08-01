/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-26
 */
package com.rails.base.security.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rails.base.security.dao.OrganizationDao;
import com.rails.base.security.dao.ResourceDao;
import com.rails.base.security.dao.RoleDao;
import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.Role;
import com.rails.common.utils.ArrayUtils;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	public Page<Role> queryPage(String organizationId,String roleName, String project, Pageable pageable) {
		return roleDao.queryPage(organizationId,roleName,project, pageable);
	}
	public List<Role> query(String organizationId, String roleName, String project) {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		Page<Role> pages = roleDao.queryPage(organizationId,roleName,project, pageable);
		return pages.getContent();
	}

	public Role Save(Role role) {
		Role original = roleDao.findOne(role.getId());
		role.setResources(original.getResources());
		role.setOrganizations(original.getOrganizations());
		return roleDao.save(role);
	}

	public void delete(String id) {
		roleDao.delete(id);
		
	}

	public Role SaveResourceXref(String roleId,String resourcesId) {
		Role original = roleDao.findOne(roleId);
		String[] strings = StringUtils.split(resourcesId,"|");
		List list = ArrayUtils.array2List(strings);
		List<Resource> resources = resourceDao.findAll(list);
		original.setResources(resources);
		return roleDao.save(original);
	}
	public Role SaveOrganizationXref(String roleId,String organizationsId) {
		Role original = roleDao.findOne(roleId);
		String[] strings = StringUtils.split(organizationsId,"|");
		List list = ArrayUtils.array2List(strings);
		List<Organization> organizations = organizationDao.findAll(list);
		original.setOrganizations(organizations);
		return roleDao.save(original);
	}

	

}
