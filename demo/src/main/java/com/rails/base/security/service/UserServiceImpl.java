package com.rails.base.security.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rails.base.security.dao.RoleDao;
import com.rails.base.security.dao.UserDao;
import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.Role;
import com.rails.base.security.domain.User;
import com.rails.common.utils.ArrayUtils;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	
	public User findByLoginName(String loginName) {
		User user = userDao.findByLoginName(loginName);
		return user;
	}
	
	public User Save(User user) {
		User original = userDao.findOne(user.getId());
		user.setRoles(original.getRoles());
		return userDao.save(user);
	}
	
	public Page<User> queryPage(String organizationId,String loginName,String userName, Pageable pageable) {
		return userDao.queryPage(organizationId,loginName,userName,pageable);
	}
	
	public void delete(String id) {
		userDao.delete(id);
	}

	public User SaveRoleXref(String userId, String rolesId) {
		// TODO Auto-generated method stub
		User original = userDao.findOne(userId);
		String[] strings = StringUtils.split(rolesId,"|");
		List list = ArrayUtils.array2List(strings);
		List<Role> roles = roleDao.findAll(list);
		original.setRoles(roles);
		return userDao.save(original);
	}
	 
	
	

}
