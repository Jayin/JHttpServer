package com.test;

import com.jhttpserver.core.WebServer;
import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;

public class App {
	public static void main(String[] args) {
		WebServer server = new WebServer();

		server.get("/hello", new Execution() {

			@Override
			public void onExecute(Request req, Response res) {
				res.send("hello JHttpServer");
			}
		});

		server.get("/like", new Execution() {

			@Override
			public void onExecute(Request req, Response res) {
				res.send("Do you like JHttpServer?");
			}
		});
		//DefaultPort = 8000
		server.listen(WebServer.DefaultPort);
	}
}
