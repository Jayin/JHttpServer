package com.jhttpserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {
	/** port */
	public static final int PORT = 8000;
	/** main server */
	private ServerSocket serverSocket = null;

	ThreadPoolExecutor excutor = new ThreadPoolExecutor(2, 5, 2,
			TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10));

	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.initServer();
		server.startServer();
	}

	private void initServer() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e1) {
			System.out.println("web server faild!");
		}
		System.out.println("web server listened in port " + PORT);
	}

	private void startServer() {
		while (true) {
			try {
				Socket connection = serverSocket.accept();
				excutor.submit(new ConnectionHandler(connection));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
