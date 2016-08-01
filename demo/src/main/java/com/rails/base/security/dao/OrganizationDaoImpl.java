package com.rails.base.security.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.User;

public class OrganizationDaoImpl {
	@PersistenceContext
	private EntityManager em;

	public Page<Organization> queryPage(String parentId,String organizationName, Pageable pageable) {
		 	String dataSql = "select t from "+Organization.class.getName()+" t where 1 = 1";
	        String countSql = "select count(t) from "+Organization.class.getName()+" t where 1 = 1";
	        
	        if(!StringUtils.isBlank(organizationName)) {
	            dataSql += " and t.organizationName like :organizationName ";
	            countSql += " and t.organizationName like :organizationName ";
	        }
	        
	        if(!StringUtils.isBlank(parentId)) {
	        	dataSql += " and t.parentId = :parentId ";
	        	countSql += " and t.parentId = :parentId ";
	        }
	        
	        Query dataQuery = em.createQuery(dataSql);
	        Query countQuery = em.createQuery(countSql);
	        
	        if(!StringUtils.isBlank(organizationName)) {
	            dataQuery.setParameter("organizationName", "%"+organizationName+"%");
	            countQuery.setParameter("organizationName","%"+organizationName+"%");
	        }
	        if(!StringUtils.isBlank(parentId)) {
	        	dataQuery.setParameter("parentId", parentId);
	        	countQuery.setParameter("parentId", parentId);
	        }
	        
	        long totalSize = (Long) countQuery.getSingleResult();
	        if(pageable!=null){
	        	dataQuery.setFirstResult(pageable.getPageNumber());
	        	dataQuery.setMaxResults(pageable.getPageSize());
	        }
	        List<Organization> data = dataQuery.getResultList();
	        Page<Organization> page = new PageImpl(data,pageable,totalSize);
	        return page;
	}

}
