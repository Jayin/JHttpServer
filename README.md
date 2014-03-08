JHttpServer
===========
A simple http server implement in Java
:
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
		//DefaultPort = 8000
		server.listen(WebServer.DefaultPort);
```

now,you can visit `localhost:8000` in you browser  

visit `localhost:8000/hello`  
or `localhost:8000/like`  

License
===
MIT
