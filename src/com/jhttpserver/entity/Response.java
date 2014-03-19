package com.jhttpserver.entity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;

import com.jhttpserver.utils.DefaultConfig;

/**
 * 响应
 * 
 * @author Jayin Ton
 * 
 */
public class Response {
	private Socket socket;
	private OutputStream out = null;
	private String contentType;

	public Response(Socket socket) throws IOException {
		this.socket = socket;
		this.out = socket.getOutputStream();

	}

	private void _send(String str) throws IOException {
		if (!socket.isOutputShutdown() && !socket.isClosed()) {
			out.write(str.getBytes());
		}
	}

	public void send(int statusCode, String content) {
		try {
			writeHeader(statusCode);
			_send("Connection: keep-alive\n");
			_send("content-type:" + getContentType() + "\n");
			_send("\n");
			_send(content);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					if(!socket.isClosed())out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** response a string */
	public void send(String content) {
		send(200, content);
	}

	/** default encoding is UTF-8 */
	public void send(File file) throws IOException {
		send(file, DefaultConfig.def_encoding);
	}

	/** response a file */
	public void send(File file, String encoding) throws IOException {
		send(200, FileUtils.readFileToString(file, encoding));
	}

	public String getContentType() {
		return contentType == null ? "text/html" : contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/** construct the status line */
	public void writeHeader(int statusCode) throws IOException {
		String statusLine = "HTTP/1.1 " + statusCode + " "
				+ getResponseCodeDescription(statusCode) + "\n";
		_send(statusLine);
	}

	private static Hashtable<Integer, String> StatusCodes = new Hashtable<Integer, String>();
	static {
		StatusCodes.put(200, "OK");
		StatusCodes.put(206, "Partial Content");
		StatusCodes.put(101, "Switching Protocols");
		StatusCodes.put(301, "Moved Permanently");
		StatusCodes.put(302, "Found");
		StatusCodes.put(404, "Not Found");
	}

	public static String getResponseCodeDescription(int code) {
		String d = StatusCodes.get(code);
		if (d == null)
			return "Unknown";
		return d;
	}
}
