package routers;

import jhttpserver.core.WebServer;
import jhttpserver.entity.Request;
import jhttpserver.entity.Response;
import jhttpserver.interfaces.Execution;

import java.io.IOException;

/**
 * Created by jayin on 15/5/23.
 */
public class Main {

    public static void main(String[] argv){
        WebServer app = new WebServer();
        app.get("/", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.send("<h1>Welcome to JHttpServer</h1>");
            }
        });

        try {
            app.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
