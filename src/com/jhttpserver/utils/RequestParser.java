package com.jhttpserver.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.jhttpserver.entity.Request;

/**
 * 请求解析
 * 
 * @author Jayin Ton
 * 
 */
public class RequestParser {
	/**
	 * 解析请求initial_line
	 * 
	 * @param request
	 * @param initial_line
	 * @return
	 */
	public static boolean parseInitialLine(Request request, String initial_line) {
		if (initial_line == null || initial_line.equals("")) {
			return false;
		}
		String[] s = initial_line.split(" ");
		request.setMethod(s[0]);
		request.setPath(s[1].substring(0, s[1].indexOf("?") == -1 ? s[1]
				.length() : s[1].indexOf("?")));
		request.setHttpVersion(s[2]);
		// parse the querystring
		if (s[1].contains("?")) {
			String[] query = s[1].substring(s[1].indexOf('?') + 1,
					s[1].length()).split("&");
			for (String _s : query) {
				String key = _s.split("=")[0];
				String value = _s.split("=")[1];
				request.setQueryString(key, value);
			}
		}
		return true;
	}

	/**
	 * 解析 header
	 * 
	 * @param request
	 * @param h
	 * @return
	 */
	public static Request parseHeader(Request request, String header_line) {
		String name = header_line.split(":")[0].trim();
		String value = header_line.split(":")[1].trim();
		request.addHeader(name.toLowerCase(), value);
		return request;
	}
   /**
    * 解析body
    * @param request
    * @param body
    * @return
    */
	public static Request parseBody(Request request, String body) {
		String[] s = body.split("&");
		for (String _s : s) {
			String name = _s.split("=")[0];
			String value = null;
			try {
				value = URLDecoder.decode(_s.split("=")[1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.addParams(name, value);
		}
		return request;
	}

}
