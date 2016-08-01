/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-20
 */
package com.rails.base.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.Role;
import com.rails.base.security.service.ResourceService;
import com.rails.common.domain.WebResult;

@Controller
@RequestMapping("/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("/page")
	public String page(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		return "/security/resource";
	}

	@RequestMapping("/queryPage")
	@ResponseBody
	public Page<Resource> queryPage(Pageable pageable, HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		String parentId = request.getParameter("q_parentId");
		String resourceName = request.getParameter("q_resourceName");
		Page<Resource> pages = resourceService.queryPage(parentId, resourceName, pageable);
		return pages;
	}

	@RequestMapping("/save")
	@ResponseBody
	public WebResult save(Resource resource, HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try {
			Resource result = resourceService.Save(resource);
			wr.setData(result);
		} catch (Exception e) {
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("保存失败");
		}
		return wr;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public WebResult delete(String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try {
			resourceService.delete(id);
		} catch (Exception e) {
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("删除失败");
		}
		return wr;
	}

//	@RequestMapping("/findByParentId")
//	@ResponseBody
//	public List<Resource> findByParentId(String id,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
//		List<Resource> children = null;
//		if(!StringUtils.isBlank(id)){
//			 children = resourceService.findByParentId(id);
//		}
//		return children;
//	}
	@RequestMapping("/findOne")
	@ResponseBody
	public Resource findOne(String id,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		Resource one = null;
		if(!StringUtils.isBlank(id)){
			one = resourceService.findOneWithChildren(id);
		}
		return one;
	}

}
