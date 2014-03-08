package com.jhttpserver.entity;

import java.util.HashMap;


/**
 * Request
 * 
 * @author Jayin Ton
 * 
 */
public class Request {
	private String httpVersion;
	private String method;
	private String path;
	private HashMap<String, String> queryString;

	public Request() {
		queryString = new HashMap<String, String>();
	}
	
	public void setQueryString(String key,String value){
		queryString.put(key, value);
	}
	
	public String query(String key){
		return queryString.get(key);
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
}
