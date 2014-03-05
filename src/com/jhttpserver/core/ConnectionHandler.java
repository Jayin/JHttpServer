package com.jhttpserver.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import com.jhttpserver.entity.RequestInfo;
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
				System.out.println(line);
				if (line.trim().equals("")) {
					break;
				}
			}

			RequestInfo info = RequestParser.parse(request.toString().split(
					"\n"));
			System.out.println("INFO");
			System.out.println(info.toString());
			String content = "hello JHttpServer";
			response = new OutputStreamWriter(connection.getOutputStream());
			response.write(content, 0, content.length());

			end = System.currentTimeMillis();
			System.out.println("response cost:" + (end - start) + "ms");
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
