package com.jhttpserver.entity;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

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
	private boolean isSend = false;
	private static final String CRLF = "\r\n";
	private Map<String,String> headers ;

	public Response(Socket socket) throws IOException {
		this.socket = socket;
		this.out = socket.getOutputStream();
		this.headers = new HashMap<String, String>();
	}

	/**
	 * 设置响应头
	 * @param field 头部名
	 * @param value value为null 则取消其对应的响应头名
	 */
	public void appendHeader(String field,String value){
		if(value == null && headers.get(field) != null){
			headers.remove(field);
		}else if(field != null){
			headers.put(field,value);
		}
	}

	private void _send(String str) throws IOException {
		if (!socket.isClosed()) {
			out.write(str.getBytes());
			out.flush();
			isSend = true;
		}
	}

	public void send(int statusCode, String content){
		appendHeader("Connection","keep-alive");
		if(getContentType() !=null ){
			appendHeader("Content-Type",getContentType());
		}
		if(content != null){
			appendHeader("Content-Length",content.length()+"");
		}
		try {
			sendStatusLine(statusCode);
			//send headers
			for(String field:this.headers.keySet()){
				_send(field + ":" + headers.get(field)+CRLF);
			}
			if(content != null){
				_send(CRLF);
				_send(content);
			}
			out.flush();
		} catch (IOException e) {
			//e.printStackTrace();
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
		appendHeader("Location",url);
		send(302,null);
	}

	public String getContentType() {
		return contentType == null ? "text/html" : contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/** construct the status line */
	private void sendStatusLine(int statusCode) throws IOException {
		String statusLine = "HTTP/1.1 " + statusCode + " "
				+ getResponseCodeDescription(statusCode) + "\r\n";
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
	
	private void close(Closeable closeable){
		if(closeable!=null){
			try {
				closeable.close();
			} catch (IOException e) {
				System.out.println("close response exception");
			}
		}
	}
	/**
	 * 是否已经发送相应头
	 * @return true if header had been send.
	 */
	public boolean isSend(){
		return this.isSend;
	}
}
