package com.jhttpserver.entity;

import java.io.Closeable;
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
public class Response{
	private Socket socket;
	private OutputStream out = null;
	private String contentType;

	public Response(Socket socket) throws IOException {
		this.socket = socket;
		this.out = socket.getOutputStream();
	}

	private void _send(String str) throws IOException {
		if (!socket.isClosed()) {
			out.write(str.getBytes());
		}
	}

	public void send(int statusCode, String content){
		try {
			writeStatusLine(statusCode);
			writeHeader("Connection: keep-alive\r\n");
			writeHeader("content-type:" + getContentType() + "\r\n");
			_send("\n");
			_send(content);
			out.flush();
		} catch (IOException e) {
		} finally {
			close(out);
		}
	}
	
	/** response a string 
	 * @throws IOException */
	public void send(String content)   {
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
	
	public void redirect(String url){
		 //重定向
		try {
			writeStatusLine(302);
			writeHeader("Connection: keep-alive\r\n");
			writeHeader("Location: "+url);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			close(out);
		}
	}

	public String getContentType() {
		return contentType == null ? "text/html" : contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/** construct the status line */
	private void writeStatusLine(int statusCode) throws IOException {
		String statusLine = "HTTP/1.1 " + statusCode + " "
				+ getResponseCodeDescription(statusCode) + "\n";
		_send(statusLine);
	}
	/** write header */
	private void writeHeader(String header) throws IOException{
		_send(header);
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
	
	private void close(Closeable closeable){
		if(closeable!=null){
			try {
				closeable.close();
			} catch (IOException e) {
				System.out.println("close response exception");
			}
		}
	}
}
