/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-21
 */
package com.rails.base.security.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.rails.common.audit.Auditable;
import com.rails.common.audit.AuditableListener;

@Entity
@Table(name = "sys_role")
@EntityListeners(value = { AuditableListener.class })
public class Role implements java.io.Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "role_name")
	private String roleName; // 角色名称
	@Transient
	private String text;

	@Column(name = "role_type")
	private String roleType; // 角色类型

	@Column(name = "project")
	private String project; //项目
	
	@Column(name = "organization_id")
	private String organizationId; // 所属机构编号
	
	@ManyToMany(targetEntity=Resource.class,fetch=FetchType.LAZY)
	@JoinTable(name="sys_role_resource_xref",joinColumns=@JoinColumn(name="role_id",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="resource_id",referencedColumnName="id"))
	private List<Resource> resources;
	
	@ManyToMany(targetEntity=Organization.class,fetch=FetchType.LAZY)
	@JoinTable(name="sys_role_organization_xref",joinColumns=@JoinColumn(name="role_id",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="organization_id",referencedColumnName="id"))
	private List<Organization> organizations;

	@Embedded
	private Auditable auditable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Auditable getAuditable() {
		return auditable;
	}

	public void setAuditable(Auditable auditable) {
		this.auditable = auditable;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public String getText() {
		return this.roleName;
	}

	


}
