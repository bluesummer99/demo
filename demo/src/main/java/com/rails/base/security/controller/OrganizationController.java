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
import com.rails.base.security.domain.Organization;
import com.rails.base.security.domain.Resource;
import com.rails.base.security.domain.Role;
import com.rails.base.security.service.OrganizationService;
import com.rails.base.security.service.ResourceService;
import com.rails.base.security.service.RoleService;
import com.rails.common.domain.WebResult;


@Controller
@RequestMapping("/organization")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		return "/security/organization";
	}
	
	@RequestMapping("/queryPage")
	@ResponseBody
	public Page<Organization> queryPage(Pageable pageable,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		String parentId = request.getParameter("q_parentId");
		String organizationName = request.getParameter("q_organizationName");
		Page<Organization> pages = organizationService.queryPage(parentId, organizationName, pageable);
		return pages;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public WebResult save(Organization role,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
			Organization result = organizationService.Save(role);
		wr.setData(result);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("保存失败");
		}
		return wr;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public WebResult delete(String id,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
			organizationService.delete(id);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("删除失败");
		}
		return wr;
	}
	@RequestMapping("/findOne")
	@ResponseBody
	public Organization findOne(String id,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		Organization one = null;
		if(!StringUtils.isBlank(id)){
			one = organizationService.findOneWithChildren(id);
		}
		return one;
	}
}
