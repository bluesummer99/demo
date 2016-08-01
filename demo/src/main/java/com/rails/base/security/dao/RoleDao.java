/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-26
 */
package com.rails.base.security.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rails.base.security.domain.Role;

public interface RoleDao extends JpaRepository<Role, String>{

	Page<Role> queryPage(String organizationId,String roleName,String project,Pageable pageable);

}
