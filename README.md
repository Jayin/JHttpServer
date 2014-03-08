JHttpServer
===========
A simple http server

Example
===
```java

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
		
		server.listen(WebServer.DefaultPort);
```