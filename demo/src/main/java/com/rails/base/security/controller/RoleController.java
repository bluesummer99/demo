/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-26
 */
package com.rails.base.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rails.base.security.domain.Role;
import com.rails.base.security.service.RoleService;
import com.rails.common.domain.WebResult;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		return "/security/role";
	}
	
	@RequestMapping("/queryPage")
	@ResponseBody
	public Page<Role> queryPage(Pageable pageable,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		String organizationId = request.getParameter("q_organizationId");
		String project = request.getParameter("q_project");
		String roleName = request.getParameter("q_roleName");
		Page<Role> pages = roleService.queryPage(organizationId,roleName, project, pageable);
		return pages;
	}
	@RequestMapping("/query")
	@ResponseBody
	public List<Role> query(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		String organizationId = request.getParameter("q_organizationId");
		String project = request.getParameter("q_project");
		String roleName = request.getParameter("q_roleName");
		List<Role> roles = roleService.query(organizationId,roleName, project);
		return roles;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public WebResult save(Role role,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
		Role result = roleService.Save(role);
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
			roleService.delete(id);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("删除失败");
		}
		return wr;
	}
	
	@RequestMapping("/resource_xref/save")
	@ResponseBody
	public WebResult saveResourceXref(String roleId,String resourcesId,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
		Role result = roleService.SaveResourceXref(roleId,resourcesId);
		wr.setData(result);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("保存失败");
		}
		return wr;
	}
	@RequestMapping("/organization_xref/save")
	@ResponseBody
	public WebResult saveOrganizationXref(String roleId,String organizationsId,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
			Role result = roleService.SaveOrganizationXref(roleId,organizationsId);
			wr.setData(result);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("保存失败");
		}
		return wr;
	}
}
