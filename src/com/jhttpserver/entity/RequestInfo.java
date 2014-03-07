package com.jhttpserver.entity;


/**
 * 请求信息
 * 
 * @author Jayin Ton
 * 
 */
public class RequestInfo {
	private String httpVersion;
	private String method;
	private String path;

	public RequestInfo() {

	}

	 
	public String getHttpVersion() {
		return httpVersion;
	}


	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	@Override
	public String toString() {
		return "RequestInfo [httpVersion=" + httpVersion + ", method=" + method
				+ ", path=" + path + "]";
	}

}
