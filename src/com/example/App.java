package com.example;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.jhttpserver.core.WebServer;
import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;

public class App {
	public static void main(String[] args) {
		WebServer server = new WebServer();
		
		server.post("/post", new Execution() {
			
			@Override
			public void onExecute(Request req, Response res) {
				 res.send(req.toString());
				
			}
		});
		server.get("/", new Execution() {
			
			@Override
			public void onExecute(Request req, Response res) {
				try {
					res.send(FileUtils.readFileToString(new File("./layouts/index.html")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
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
		//DefaultPort = 8000
		server.listen(WebServer.DefaultPort);
	}
}
