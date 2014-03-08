package com.jhttpserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;
import com.jhttpserver.utils.RequestParser;

public class ConnectionHandler implements Runnable {
	private Socket connection;
	private long start = 0;
	private long end = 0;
	private Request request;
	private Response response;
	private Execution exe;

	public ConnectionHandler(Socket connection, Execution exe) {
		this.connection = connection;
		this.exe = exe;
	}

	@Override
	public void run() {
		try {
			onStart();
			onParseBody();
			if (exe != null) {
				exe.onExecute(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			onComplete();
		}
	}

	public void onStart() throws IOException {
		start = System.currentTimeMillis();
		response = new Response(connection.getOutputStream());
	}

	public void onParseBody() throws IOException {
		StringBuffer headerString = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection
				.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			headerString.append(line).append("\n");
			if (line.trim().equals("")) {
				break;
			}
		}
		// parse initial line
		request = RequestParser.parse(headerString.toString().split("\n"));
	}

	public void onComplete() {
		end = System.currentTimeMillis();
		System.out.println(request.getMethod() + " " + request.getPath() + " "
				+ (end - start) + "ms");
	}

}
