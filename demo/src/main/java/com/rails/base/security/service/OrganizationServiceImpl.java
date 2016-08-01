package com.rails.base.security.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rails.base.security.dao.OrganizationDao;
import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.User;


@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService{
	@Autowired
	private OrganizationDao organizationDao;
	
	
	
	public Organization Save(Organization organization) {
		return organizationDao.save(organization);
	}
	
	public Page<Organization> queryPage(String loginName,String userName, Pageable pageable) {
		return organizationDao.queryPage(loginName,userName,pageable);
	}
	
	public void delete(String id) {
		organizationDao.delete(id);
	}

	public List<Organization> findByParentId(String id) {
		 List<Organization> children = organizationDao.findByParentIdOrderByPriorityDesc(id);
		 if(children!=null && children.size()!=0){
			 for(int i=0;i<children.size();i++){
				 Organization child = children.get(i);
				 List<Organization> grandchildren = findByParentId(child.getId());
				 child.setChildren(grandchildren);
			 }
		 }
		 return children;
	}
	
	public Organization findOneWithChildren(String id) {
		Organization one = organizationDao.findOne(id);
		List<Organization> children = findByParentId(one.getId());
		one.setChildren(children);
		return one;
	}
	 
	
	

}
