import jhttpserver.core.WebServer;
import jhttpserver.entity.Request;
import jhttpserver.entity.Response;
import jhttpserver.interfaces.Execution;
import jhttpserver.utils.PathUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        WebServer server = new WebServer();

        server.getServerConfig().root = PathUtil.getUserDir() + "/webapp/www";
        //注释这个, 为了测试DirectoryIndex https://github.com/Jayin/JHttpServer/issues/15
//        server.get("/", new Execution() {
//
//            @Override
//            public void onExecute(Request req, Response res) {
//                try {
//                    res.send(new File(System.getProperty("user.dir") +  File.separator +  "examples/layouts/index.html"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        server.get("/get", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
//                res.send(req.get);
                HashMap<String, String> params = req.getParams();
                Set<String> keys = params.keySet();
                String result = "";
                for (String key : keys) {
                    result += key + " : " + params.get(key) + " <br> ";
                }
                res.send(result);

            }

        });

        server.get("/post", new Execution() {

            @Override
            public void onExecute(Request req, Response res) {
                res.send("get!");
            }
        });
        server.post("/post", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("post!");
            }
        });

        server.put("/post", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("put!");
            }
        });

        server.patch("/post", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("patch!");
            }
        });

        server.delete("/post", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("delete = =,!");
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
        server.get("/tolike", new Execution() {

            @Override
            public void onExecute(Request req, Response res) {
                res.redirect("/like");
//                Set<String> keys = req.getData();
//                for(Set<String> key : req.getData().keySet())
//                System.out.println(req.getData().toString())

            }
        });

        server.get("/baidu", new Execution() {

            @Override
            public void onExecute(Request req, Response res) {
                res.redirect("http://www.baidu.com");
            }
        });

        //test bug #7:https://github.com/Jayin/JHttpServer/issues/7
        server.get("/bug7", new Execution() {

            @Override
            public void onExecute(Request req, Response res) {
                System.out.println("Nothing return! send '' by defult!");
            }
        });

        server.get("/header", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.appendHeader("language", "Java", "Csharp");
                res.appendHeader("language", "Python");
                res.send("finish!");
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
