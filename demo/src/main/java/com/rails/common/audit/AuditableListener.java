/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-21
 */
package com.rails.common.audit;

import java.lang.reflect.Field;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.rails.base.security.domain.User;
import com.rails.common.constants.BaseConstants;
import com.rails.common.utils.DateUtils;

public class AuditableListener {

	   @PrePersist
	    public void setAuditCreatedBy(Object entity) throws Exception {
	        if (entity.getClass().isAnnotationPresent(Entity.class)) {
	            Field field = getSingleField(entity.getClass(), "auditable");
	            field.setAccessible(true);
	            if (field.isAnnotationPresent(Embedded.class)) {
	                Object auditable = field.get(entity);
	                if (auditable == null) {
	                    field.set(entity, new Auditable());
	                    auditable = field.get(entity);
	                }
	                Field createdField = auditable.getClass().getDeclaredField("dateCreated");
	                Field updatedField = auditable.getClass().getDeclaredField("dateUpdated");
	                Field createdByField = auditable.getClass().getDeclaredField("createdBy");
	                Field updatedByField = auditable.getClass().getDeclaredField("updatedBy");
	                setAuditValueDate(createdField, auditable);
	                setAuditValueDate(updatedField, auditable);
	                setAuditValueAgent(createdByField, auditable);
	                setAuditValueAgent(updatedByField, auditable);
	            }
	        }
	    }

	    @PreUpdate
	    public void setAuditUpdatedBy(Object entity) throws Exception {
	        if (entity.getClass().isAnnotationPresent(Entity.class)) {
	            Field field = getSingleField(entity.getClass(), "auditable");
	            field.setAccessible(true);
	            if (field.isAnnotationPresent(Embedded.class)) {
	                Object auditable = field.get(entity);
	                if (auditable == null) {
	                    field.set(entity, new Auditable());
	                    auditable = field.get(entity);
	                }
	                Field temporalField = auditable.getClass().getDeclaredField("dateUpdated");
	                Field agentField = auditable.getClass().getDeclaredField("updatedBy");
	                setAuditValueDate(temporalField, auditable);
	                setAuditValueAgent(agentField, auditable);
	            }
	        }
	    }
	    protected void setAuditValueDate(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
	    	String currentDate19 = DateUtils.getCurrentDate19();
	        field.setAccessible(true);
	        field.set(entity, currentDate19);
	    }

	    protected void setAuditValueAgent(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
	        try {
	        		Subject subject = SecurityUtils.getSubject();
	        		User user = (User)subject.getSession().getAttribute(BaseConstants.USER_SESSION_KEY);
	                field.setAccessible(true);
	                field.set(entity, user.getLoginName());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private Field getSingleField(Class<?> clazz, String fieldName) throws IllegalStateException {
	        try {
	            return clazz.getDeclaredField(fieldName);
	        } catch (NoSuchFieldException nsf) {
	            if (clazz.getSuperclass() != null) {
	                return getSingleField(clazz.getSuperclass(), fieldName);
	            }

	            return null;
	        }
	    }
}
