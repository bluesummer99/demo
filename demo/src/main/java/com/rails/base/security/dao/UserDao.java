package com.rails.base.security.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rails.base.security.domain.User;


public interface UserDao extends JpaRepository<User, String>{
	
	User findByLoginName(String loginName);
	Page<User> queryPage(String organizationId,String loginName,String userName, Pageable pageable);
	
}
