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
@Table(name = "sys_organization")
public class Organization implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;// 资源编号（主键）
	
	@Column(name = "organization_name")
	private String organizationName; // 名称NAME VARCHAR2(225)
	
	@Column(name = "parent_id",nullable=true)
	private String parentId; // 上级编号PARENTID NUMBER(10)
	
	@Transient
	private List<Organization> children;

	@Column(name = "organization_type")
	private String organizationType; // 机构类型

	@Column(name = "organization_code")
	private String organizationCode; // 机构编码ORGANIZATIONNUM VARCHAR2(255)
	
	@Column(name = "priority",nullable=false)
	private long priority; //显示顺序
	private String text;
	
	@Embedded
	private Auditable auditable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	public Auditable getAuditable() {
		return auditable;
	}

	public void setAuditable(Auditable auditable) {
		this.auditable = auditable;
	}



	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return this.organizationName;
	}

}
