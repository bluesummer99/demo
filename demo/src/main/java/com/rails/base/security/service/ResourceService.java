package com.rails.base.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.Role;

public interface ResourceService {
	List<Resource> findByParentId(String id);
	Resource findOneWithChildren(String id);
	Page<Resource> queryPage(String parentId, String resourceName, Pageable pageable);
	Resource Save(Resource resource);
	void delete(String id);
}
