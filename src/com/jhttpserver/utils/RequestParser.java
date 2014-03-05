package com.jhttpserver.utils;

import com.jhttpserver.entity.RequestInfo;

/**
 * 请求解析
 * 
 * @author Jayin Ton
 * 
 */
public class RequestParser {

	public static RequestInfo parse(String[] headers) {
		RequestInfo requestInfo = new RequestInfo();
		// 解析第一行
		String line1 = headers[0];
		parseLine1(requestInfo, line1);
        return requestInfo;
	}

	private static void parseLine1(RequestInfo requestInfo, String line1) {
		String[] s = line1.split(" ");
        requestInfo.setMethod(s[0]);
        requestInfo.setPath(s[1]);
        requestInfo.setHttpVersion(s[2]);
	}
}
