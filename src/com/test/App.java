package com.test;

import com.jhttpserver.core.WebServer;

public class App {
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.startServer(WebServer.DefaultPort);
	}
}
