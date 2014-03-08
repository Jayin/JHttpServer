package com.jhttpserver.utils;

import com.jhttpserver.entity.Request;

/**
 * 请求解析
 * 
 * @author Jayin Ton
 * 
 */
public class RequestParser {

	public static Request parse(String[] headers) {
		Request requestInfo = new Request();
		// 解析第一行
		String initial_line = headers[0];
		parseLine1(requestInfo, initial_line);
		return requestInfo;
	}

	private static void parseLine1(Request req, String initial_line) {
		String[] s = initial_line.split(" ");
		req.setMethod(s[0]);
		req.setPath(s[1]);
		req.setHttpVersion(s[2]);
		// parse the querystring
		if (s[1].contains("?")) {
			String[] query = s[1].substring(s[1].indexOf('?') +1,
					s[1].length() ).split("&");
			for (String _s : query) {
				String key = _s.split("=")[0];
				String value = _s.split("=")[1];
				req.setQueryString(key, value);
			}
		}
	}
}
