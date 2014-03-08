package com.jhttpserver.entity;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 响应
 * 
 * @author Jayin Ton
 * 
 */
public class Response {

	private OutputStream out;
	private String contentType;

	public Response(OutputStream outputStream) {
		this.out = outputStream;
	}

	public void send(int statusCode, String content) {
		String statusLine = "HTTP/1.1 " + statusCode + " OK\n";
		try {
			out.write(statusLine.getBytes());
			out.write(("content-type:"+getContentType()+"\n").getBytes());
			out.write("\n".getBytes());
			out.write(content.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void send(String content) {
		send(200, content);
	}

	public String getContentType() {
		return contentType == null ? "text/html" : contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
