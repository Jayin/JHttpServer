package com.jhttpserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import com.jhttpserver.entity.Constants;
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
				if(exe ==null){
					response.send(404, "Not found page");
				}else{
					exe.onExecute(request, response);
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
		response = new Response(connection);
	}

	public void onParseBody() throws IOException {
		InputStream in = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		int type = 0;
		request = new Request();
		while ((line = br.readLine()) != null) {
			System.out.println("Line--"+line);
			if (line.equals(""))
				type = 2;
			switch (type) {
			case 0: // initial line
//				
				RequestParser.parseInitialLine(request, line);
				type++;
				break;
			case 1: // header
				RequestParser.parseHeader(request, line);
				break;
			case 2: // [blank line here]
				type++;
				// parse body
				if (request.getMethod().equals(Constants.MEHOD_POST)) {
					int content_length = Integer.parseInt(request
							.getHeader("content-length"));
					char[] chars = new char[1024];
					int count = 0;
					String body = "";
					while (content_length > 0
							&& (count = br.read(chars, 0, 1024)) != -1) {
						content_length -= count;
						body += new String(new String(chars, 0, count));
					}
					request.setBody(body);
					RequestParser.parseBody(request, body);
				}
				return;
			default:
				return;
			}
		}
	}

	public void onComplete() {
		end = System.currentTimeMillis();
		System.out.println();
		System.out.println(request.getMethod() + " " + request.getPath() + " "
				+ (end - start) + "ms");
	}

}
