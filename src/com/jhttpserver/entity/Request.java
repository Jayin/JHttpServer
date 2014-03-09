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
	private HashMap<String, String> header;// 请求头
	private String body; // body

	public Request() {
		queryString = new HashMap<String, String>();
		header = new HashMap<String, String>();
	}

	// 有点粗糙,其实value那里还可以细分
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
				+ ", path=" + path + ", queryString=" + queryString + "]";
	}

}
