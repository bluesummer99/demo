/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-20
 */
package com.rails.base.security.controller;

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
import com.rails.base.security.domain.User;
import com.rails.base.security.service.UserService;
import com.rails.common.domain.WebResult;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		return "/security/user";
	}
	@RequestMapping("/queryPage")
	@ResponseBody
	public Page<User> queryPage(Pageable pageable,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		String organizationId = request.getParameter("q_organizationId");
		String loginName = request.getParameter("q_loginName");
		String userName = request.getParameter("q_userName");
		Page<User> pages = userService.queryPage(organizationId,loginName, userName, pageable);
		return pages;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public WebResult save(User user,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
		User result = userService.Save(user);
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
			userService.delete(id);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("删除失败");
		}
		return wr;
	}
	@RequestMapping("/role_xref/save")
	@ResponseBody
	public WebResult saveRoleXref(String userId,String rolesId,String resourcesId,HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		WebResult wr = new WebResult();
		try{
		User result = userService.SaveRoleXref(userId,rolesId);
		wr.setData(result);
		}catch(Exception e){
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("保存失败");
		}
		return wr;
	}
	
}
