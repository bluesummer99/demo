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
import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.User;

public class ResourceDaoImpl {
	@PersistenceContext
	private EntityManager em;

	public Page<Resource> queryPage(String parentId,String caption, Pageable pageable) {
	 	String dataSql = "select t from "+Resource.class.getName()+" t where 1 = 1";
        String countSql = "select count(t) from "+Resource.class.getName()+" t where 1 = 1";
        
        if(!StringUtils.isBlank(caption)) {
            dataSql += " and t.caption like :caption ";
            countSql += " and t.caption like :caption ";
        }
        
        if(!StringUtils.isBlank(parentId)) {
        	dataSql += " and t.parentId = :parentId ";
        	countSql += " and t.parentId = :parentId ";
        }
	        dataSql += " order by t.priority desc ";
        
        Query dataQuery = em.createQuery(dataSql);
        Query countQuery = em.createQuery(countSql);
        
        if(!StringUtils.isBlank(caption)) {
            dataQuery.setParameter("caption", "%"+caption+"%");
            countQuery.setParameter("caption","%"+caption+"%");
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
        List<Resource> data = dataQuery.getResultList();
        Page<Resource> page = new PageImpl(data,pageable,totalSize);
        return page;
}

}
