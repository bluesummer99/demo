/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-19
 */
package com.rails.common.domain;


public class WebResult  {
	
	private String status = WebResult.SUCCESS;
	private String message;
	private Object data;
	private Exception exception;
	
	public static final String SUCCESS="success";
	public static final String ERROR="error";
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}

