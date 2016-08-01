package com.rails.base.security.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.User;

public class UserDaoImpl {
	@PersistenceContext
	private EntityManager em;

	public Page<User> queryPage(String organizationId,String loginName,String userName, Pageable pageable) {
		 	String dataSql = "select t from "+User.class.getName()+" t where 1 = 1";
	        String countSql = "select count(t) from "+User.class.getName()+" t where 1 = 1";
	        
	        if(!StringUtils.isBlank(organizationId)) {
	        	dataSql += " and t.organizationId = :organizationId ";
	        	countSql += " and t.organizationId = :organizationId ";
	        }
	        if(!StringUtils.isBlank(loginName)) {
	            dataSql += " and t.loginName like :loginName ";
	            countSql += " and t.loginName like :loginName ";
	        }
	        
	        if(!StringUtils.isBlank(userName)) {
	        	dataSql += " and t.userName like :userName ";
	        	countSql += " and t.userName like :userName ";
	        }
	        
	        Query dataQuery = em.createQuery(dataSql);
	        Query countQuery = em.createQuery(countSql);
	        
	        if(!StringUtils.isBlank(organizationId)) {
	        	dataQuery.setParameter("organizationId", organizationId);
	        	countQuery.setParameter("organizationId",organizationId);
	        }
	        if(!StringUtils.isBlank(loginName)) {
	            dataQuery.setParameter("loginName", "%"+loginName+"%");
	            countQuery.setParameter("loginName","%"+loginName+"%");
	        }
	        if(!StringUtils.isBlank(userName)) {
	        	dataQuery.setParameter("userName", "%"+userName+"%");
	        	countQuery.setParameter("userName", "%"+userName+"%");
	        }
	        
	        long totalSize = (Long) countQuery.getSingleResult();
	        if(pageable!=null){
	        	dataQuery.setFirstResult(pageable.getPageNumber());
	        	dataQuery.setMaxResults(pageable.getPageSize());
	        }
	        List<User> data = dataQuery.getResultList();
	        Page<User> page = new PageImpl(data,pageable,totalSize);
	        return page;
	}

}
