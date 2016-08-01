package com.rails.base.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.User;

public interface UserService {
	public User findByLoginName(String loginName);
	public User Save(User user) ;
	public Page<User> queryPage(String organizationId,String loginName,String userName, Pageable pageable);
	public void delete(String id);
	public User SaveRoleXref(String userId, String roledId);
}
