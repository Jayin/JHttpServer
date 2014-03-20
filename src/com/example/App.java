package com.example;

import java.io.File;
import java.io.IOException;

import com.jhttpserver.core.WebServer;
import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;

public class App {
	public static void main(String[] args) {
		WebServer server = new WebServer();

		server.get("/", new Execution() {

			@Override
			public void onExecute(Request req, Response res) {
				try {
					res.send(new File("./layouts/index.html"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		server.post("/post", new Execution() {

			@Override
			public void onExecute(Request req, Response res) {
				res.send(req.toString());
			}
		});

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
		//redirect
		server.get("/tolike",new Execution() {
			
			@Override
			public void onExecute(Request req, Response res) {
				 res.redirect("/like");
				
			}
		});		
		
		server.get("/baidu", new Execution() {
			
			@Override
			public void onExecute(Request req, Response res) {
				res.redirect("http://www.baidu.com");
			}
		});
		
		// DefaultPort = 8000
		try {
			server.listen(WebServer.DefaultPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
