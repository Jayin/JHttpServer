package com.example;

import com.jhttpserver.core.WebServer;
import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;
import com.jhttpserver.interfaces.IMiddleWare;

import java.io.IOException;

/**
 * Created by jayin on 14/12/5.
 */
public class MiddleWareTest {


    public static void main(String[] args) throws IOException {
        WebServer app = new WebServer();
        app.use(new IMiddleWare() {
            @Override
            public void work(Request req, Response res) {
                System.out.println("1");
            }
        });

        app.use(new IMiddleWare() {
            @Override
            public void work(Request req, Response res) {
                System.out.println("2");
            }
        });

        app.get("/get", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("Finish!");
            }
        });

        app.listen(WebServer.DefaultPort);
    }
}
