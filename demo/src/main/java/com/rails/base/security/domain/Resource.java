/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-20
 */
package com.rails.base.security.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.rails.common.audit.Auditable;
import com.rails.common.audit.AuditableListener;

@Entity
@EntityListeners(value = { AuditableListener.class })
@Table(name = "sys_resource")
public class Resource implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;// 资源编号（主键）
	
	@Column(name = "parent_id",nullable=true)
	private String parentId;// 父资源单编号
	
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="parentId")
	@Transient
	private List<Resource> children;

	@Column(name = "resource_type")
	private String resourceType;// 资源类型（模块、页面和操作）

	@Column(name = "caption")
	private String caption;// 标题
	
	@Transient
	private String text;// 标题


	@Column(name = "permission")
	private String permission;// 权限串

	@Column(name = "linkurl")
	private String linkurl;// 应用链接

	
	@Column(name = "module")
	private String module; // 模块
	
	@Column(name = "project")
	private String project; //项目

	@Column(name = "priority")
	private long priority;// 序号
	
	@Embedded
	private Auditable auditable;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}





	

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public Auditable getAuditable() {
		return auditable;
	}

	public void setAuditable(Auditable auditable) {
		this.auditable = auditable;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return this.caption;
	}

}
