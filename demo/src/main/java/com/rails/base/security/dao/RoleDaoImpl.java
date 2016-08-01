package com.rails.base.security.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.Role;
import com.rails.base.security.domain.User;

public class RoleDaoImpl {
	@PersistenceContext
	private EntityManager em;

	public Page<Role> queryPage(String organizationId,String project,String roleName, Pageable pageable) {
		 	String dataSql = "select t from "+Role.class.getName()+" t where 1 = 1";
	        String countSql = "select count(t) from "+Role.class.getName()+" t where 1 = 1";
	        
	        if(!StringUtils.isBlank(organizationId)) {
	            dataSql += " and t.organizationId = :organizationId ";
	            countSql += " and t.organizationId = :organizationId ";
	        }
	        if(!StringUtils.isBlank(project)) {
	        	dataSql += " and t.project = :project ";
	        	countSql += " and t.projrct = :project ";
	        }
	        
	        if(!StringUtils.isBlank(roleName)) {
	        	dataSql += " and t.roleName like :roleName ";
	        	countSql += " and t.roleName like :roleName ";
	        }
	        
	        Query dataQuery = em.createQuery(dataSql);
	        Query countQuery = em.createQuery(countSql);
	        
	        if(!StringUtils.isBlank(organizationId)) {
	        	dataQuery.setParameter("organizationId", organizationId);
	        	countQuery.setParameter("organizationId",organizationId);
	        }
	        if(!StringUtils.isBlank(project)) {
	            dataQuery.setParameter("project", project);
	            countQuery.setParameter("project",project);
	        }
	        if(!StringUtils.isBlank(roleName)) {
	        	dataQuery.setParameter("roleName", "%"+roleName+"%");
	        	countQuery.setParameter("roleName", "%"+roleName+"%");
	        }
	        
	        long totalSize = (Long) countQuery.getSingleResult();
	        if(pageable!=null){
	        	dataQuery.setFirstResult(pageable.getPageNumber());
	        	dataQuery.setMaxResults(pageable.getPageSize());
	        }
	        List<Role> data = dataQuery.getResultList();
	        Page<Role> page = new PageImpl(data,pageable,totalSize);
	        return page;
	}

}
