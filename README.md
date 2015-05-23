JHttpServer
-------

A simple http server implement in Java, with high-level api.

Feature
-------

* suport `GET` `POST` `PUT` `PATCH` `DELETE`
* suport cookie
* support middleware
* support CORS config

Example
-------

```java

       WebServer server = new WebServer();

		// regitster a  GET router
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

		// regitster a POST router
		server.post("/login", new Execution() {

			@Override
			public void onExecute(Request req, Response res) {
			  res.setCookie("username","Jayin");
               res.setCookie("test","1",0);
				res.send("login ok!");
			}
		});


		//redirect
		server.get("/tolike",new Execution() {
			
			@Override
			public void onExecute(Request req, Response res) {
				 res.redirect("/");
				
			}
		});		

		// start the server
		server.listen(WebServer.DefaultPort);
	}
```

now,you can visit `localhost:8000` in you browser  

visit `localhost:8000/hello`  
or `localhost:8000/like`  

License
-------

MIT
