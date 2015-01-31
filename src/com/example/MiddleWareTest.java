package com.example;

import com.jhttpserver.core.WebServer;
import com.jhttpserver.entity.Header;
import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;
import com.jhttpserver.interfaces.IMiddleWare;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by jayin on 14/12/5.
 */
public class MiddleWareTest {


    public static void main(String[] args) throws IOException {
        WebServer app = new WebServer();
        app.use(new IMiddleWare() {
            @Override
            public void work(Request req, Response res) {
                System.out.println("-- middleware 1 works");
            }
        });

        app.use(new IMiddleWare() {
            @Override
            public void work(Request req, Response res) {
                System.out.println("-- middleware 2 works");
                List<Header> headers = req.getHeaders();
                for(Header h : headers){
                    System.out.println(h.toString());
                }

            }
        });

        app.get("/get", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("Finish!");
            }
        });

        app.post("/post", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                List<Header> headers = req.getHeaders();
                for(Header h : headers){
                    System.out.println(h.toString());
                }
//                res.send("233");
            }
        });

        app.listen(WebServer.DefaultPort);
    }
}
