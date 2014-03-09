package com.jhttpserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

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
	HashMap<String, Execution> handlers;

	public ConnectionHandler(Socket connection,
			HashMap<String, Execution> handlers) {
		this.connection = connection;
		this.handlers = handlers;
	}

	@Override
	public void run() {
		try {
			onStart();
			onParseBody();
			if (request != null) {
				exe = handlers.get(request.getPath());
				if (exe != null) {
					exe.onExecute(request, response);
				} else {
					response.send(404, "Not found page");
				}
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
		int type = 0;
		request = new Request();
		while ((line = br.readLine()) != null) {
			if (line.equals(""))
				type = 2;
			switch (type) {
			case 0: // initial line
				RequestParser.parseInitialLine(request, line);
				type++;
				break;
			case 1: // header
				RequestParser.parseHeader(request, line);
				break;
			case 2: // [blank line here]
				type++;
				return;
			 
			case 3: // body
				RequestParser.parseBody(request, line);
				break;
			default:
				break;
			}
		}
		// parse initial line
		// request = RequestParser.parse(headerString.toString().split("\n"));
	}

	public void onComplete() {
		end = System.currentTimeMillis();
		System.out.println(request.getMethod() + " " + request.getPath() + " "
				+ (end - start) + "ms");
	}

}
