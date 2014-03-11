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
	private HashMap<String, String> queryString;//method-get
	private HashMap<String, String> header;// 请求头
	private HashMap<String, String> params; //method-post
	private String body; // body

	public Request() {
		queryString = new HashMap<String, String>();
		header = new HashMap<String, String>();
		params = new HashMap<String, String>();
	}
	
	public void addParams(String name,String value){
		this.params.put(name, value);
	}
	
	public String getParams(String name){
		return this.params.get(name);
	}

 
	public void addHeader(String name, String value) {
		this.header.put(name, value);
	}

	public String getHeader(String name) {
		return this.header.get(name);
	}

	public void setQueryString(String key, String value) {
		queryString.put(key, value);
	}

	public String query(String key) {
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Request [body=" + body + ", header=" + header
				+ ", httpVersion=" + httpVersion + ", method=" + method
				+ ", params=" + params + ", path=" + path + ", queryString="
				+ queryString + "]";
	}
}
