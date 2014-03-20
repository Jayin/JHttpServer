package com.jhttpserver.entity;

import java.util.HashMap;
/**
 * 请求头
 * @author Jayin Ton
 *
 */
public class Header {
	private HashMap<String, String> headers = new HashMap<String, String>();

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

}
