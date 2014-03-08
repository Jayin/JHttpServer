package com.jhttpserver.entity;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 响应
 * @author Jayin Ton
 * 
 */
public class Response {

	private OutputStream out;

	public Response(OutputStream outputStream) {
		this.out = outputStream;
	}

	public void send(int statusCode, String content) {
		String statusLine = "HTTP/1.1 " + statusCode + " OK\n";
		try {
			out.write(statusLine.getBytes());
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

}
