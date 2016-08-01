package com.rails.base.security.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rails.base.security.dao.ResourceDao;
import com.rails.base.security.domain.Resource;


@Service
@Transactional
public class ResourceServiceImpl implements ResourceService{
	@Autowired
	private ResourceDao resourceDao;

	public List<Resource> findByParentId(String id) {
		 List<Resource> children = resourceDao.findByParentIdOrderByPriorityDesc(id);
		 if(children!=null && children.size()!=0){
			 for(int i=0;i<children.size();i++){
				 Resource child = children.get(i);
				 List<Resource> grandchildren = findByParentId(child.getId());
				 child.setChildren(grandchildren);
			 }
		 }
		 return children;
	}
	
	public Resource findOneWithChildren(String id) {
		Resource one = resourceDao.findOne(id);
		List<Resource> children = findByParentId(one.getId());
		one.setChildren(children);
		return one;
	}

	public Page<Resource> queryPage(String parentId, String resourceName, Pageable pageable) {
		return resourceDao.queryPage(parentId,resourceName,pageable);
	}

	public Resource Save(Resource resource) {
		return resourceDao.save(resource);
	}

	public void delete(String id) {
		resourceDao.delete(id);
	}
	
	




	

}
