package com.rails;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


import com.rails.common.shiro.MyRealm;

@Configuration
public class ShiroConfiguration {

	
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/login");
		shiroFilter.setSuccessUrl("/main");
		shiroFilter.setUnauthorizedUrl("/forbidden");
		Map<String, String> filterChainDefinitionMapping = new LinkedHashMap<String, String>();
//		Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
		filterChainDefinitionMapping.put("/js/**", "anon");
		filterChainDefinitionMapping.put("/css/**", "anon");
		filterChainDefinitionMapping.put("/images/**", "anon");
		filterChainDefinitionMapping.put("/login/**", "anon");
		filterChainDefinitionMapping.put("/logout", "logout");
		filterChainDefinitionMapping.put("/kaptcha/**", "anon");
		filterChainDefinitionMapping.put("/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager());
		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("anon", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());
		filters.put("roles", new RolesAuthorizationFilter());
		filters.put("user", new UserFilter());
		shiroFilter.setFilters(filters);
		return shiroFilter;
	}

	@Bean(name = "securityManager")
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		return securityManager;
	}

	@Bean(name = "realm")
	@DependsOn(value={"lifecycleBeanPostProcessor"})
	public MyRealm realm() {
		MyRealm real = new MyRealm();
		real.init();
		return real;
	}
	


	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
//	@Bean
//	public ShiroDialect shiroDialect() {
//		return new ShiroDialect();
//	}
}
