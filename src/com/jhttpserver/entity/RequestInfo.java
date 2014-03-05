package com.jhttpserver.entity;

import com.jhttpserver.interfaces.IRequestInfo;

/**
 * 请求信息
 * 
 * @author Jayin Ton
 * 
 */
public class RequestInfo implements IRequestInfo {
	private String httpVersion;
	private String method;
	private String path;
	
	public RequestInfo(){
		
	}
	
	@Override
	public String getHttpVersion() {
		return this.httpVersion;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setHttpVersion(String verison) {
		this.httpVersion = verison;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "RequestInfo [httpVersion=" + httpVersion + ", method=" + method
				+ ", path=" + path + "]";
	}

}
