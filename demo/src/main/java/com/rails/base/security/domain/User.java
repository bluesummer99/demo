package com.rails.base.security.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.rails.common.audit.Auditable;
import com.rails.common.audit.AuditableListener;

@Entity
@Table(name = "sys_user")
@EntityListeners(value = { AuditableListener.class })
public class User implements Serializable {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id; 	// 用户编号
	
	@Column(name = "login_name", unique=true)
	private String loginName; // 登录名

	@Column(name = "password")
	private String password; // 密码
	
	@Column(name = "user_name")
	private String userName; // 用户姓名

	@Column(name = "organization_id")
	private String organizationId; // 所属机构编号

	@Column(name = "module")
	private String module; // 模块
	
	@Column(name = "project")
	private String project; //项目
	
	@Embedded
	private Auditable auditable;
	
	@ManyToMany(targetEntity=Role.class,fetch=FetchType.LAZY)
	@JoinTable(name="sys_user_role_xref",joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id"))
	private List<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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

	public Auditable getAuditable() {
		return auditable;
	}

	public void setAuditable(Auditable auditable) {
		this.auditable = auditable;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	

}
