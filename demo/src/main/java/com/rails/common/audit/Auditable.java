/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-21
 */
package com.rails.common.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * 需要详细记录创建信息
 *
 */
@Embeddable
public class Auditable implements Serializable  {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "DATE_CREATED", updatable = false)
    protected String dateCreated;
    
    @Column(name = "CREATED_BY", updatable = false)
    protected String createdBy;
    
    @Column(name = "DATE_UPDATED")
    protected String dateUpdated;
    
    @Column(name = "UPDATED_BY")
    protected String updatedBy;

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}



	
}
