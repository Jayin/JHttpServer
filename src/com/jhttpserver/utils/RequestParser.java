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
		Request request = new Request();
		// 解析第一行
		String initial_line = headers[0];
		if(parseInitialLine(request, initial_line)){
			for (int i = 1; i < headers.length; i++) {
				String h = headers[i];
				String name = h.split(":")[0].trim();
				String value = h.split(":")[1].trim();
				request.addHeader(name, value);
			}
			return request;
		}
		return null;
	}

	// 解析请求报头
	private static boolean parseInitialLine(Request req, String initial_line) {
		if(initial_line==null || initial_line.equals("")){
			return false;
		}
		String[] s = initial_line.split(" ");
		System.out.println("parseInitialLine-->"+initial_line);
		req.setMethod(s[0]);
		req.setPath(s[1].substring(0, s[1].indexOf("?") == -1 ? s[1].length()
				: s[1].indexOf("?")));
		req.setHttpVersion(s[2]);
		// parse the querystring
		if (s[1].contains("?")) {
			String[] query = s[1].substring(s[1].indexOf('?') + 1,
					s[1].length()).split("&");
			for (String _s : query) {
				String key = _s.split("=")[0];
				String value = _s.split("=")[1];
				req.setQueryString(key, value);
			}
		}
		return true;
	}
}
