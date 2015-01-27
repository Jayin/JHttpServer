package com.jhttpserver.entity;

import java.util.HashMap;
import java.util.Map;

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
	private HashMap<String, String> headers;// 请求头
	private HashMap<String, String> data; //method-post
	private HashMap<String, String> params;//method-get
	private String body; // body

	public Request() {
		headers = new HashMap<String, String>();
		data = new HashMap<String, String>();
		params = new HashMap<String, String>();
	}
	
	public void addData(String name, String value){
		this.data.put(name, value);
	}
	
	public String getData(String name){
		return this.data.get(name);
	}

    public void addParam(String name,String value){
		this.params.put(name,value);
	}

	public String getParam(String name){
		return this.params.get(name);
	}

	public HashMap<String, String> getParams(){
		return this.params;
	}
	public void addHeader(String name, String value) {
		this.headers.put(name, value);
	}

	public String getHeader(String name) {
		return this.headers.get(name);
	}

	public Map<String,String> getHeaders(){
		return this.headers;
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
		return "Request{" +
				"httpVersion='" + httpVersion + '\'' +
				", method='" + method + '\'' +
				", path='" + path + '\'' +
				", headers=" + headers +
				", data=" + data +
				", params=" + params +
				", body='" + body + '\'' +
				'}';
	}
}
