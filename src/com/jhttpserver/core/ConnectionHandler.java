package com.jhttpserver.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import com.jhttpserver.entity.Request;
import com.jhttpserver.utils.RequestParser;

public class ConnectionHandler implements Runnable {
	Socket connection;
	private long start = 0;
	private long end = 0;

	public ConnectionHandler(Socket connection) {
		this.connection = connection;
		start = System.currentTimeMillis();
	}

	@Override
	public void run() {
		Writer response = null;
		try {
			StringBuffer request = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				request.append(line).append("\n");
				if (line.trim().equals("")) {
					break;
				}
			}
           //parse initial line 
			Request info = RequestParser.parse(request.toString().split(
					"\n"));
			int statusCode = 200;
			String responseHeader = "HTTP/1.1 " + statusCode + " OK\n";
			String content = "Hello JHttpServer";
			response = new OutputStreamWriter(connection.getOutputStream());
			response.write(responseHeader , 0, responseHeader.length());
			response.write('\n');
			response.write(content, 0, content.length());
			response.flush();
			
			//print info
			end = System.currentTimeMillis();
			System.out.println(info.getMethod() + " " + info.getPath() + " "
					+ (end - start) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
