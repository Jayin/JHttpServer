import jhttpserver.core.WebServer;
import jhttpserver.entity.Request;
import jhttpserver.entity.Response;
import jhttpserver.interfaces.Execution;

import java.io.IOException;

/**
 * Created by jayin on 15/1/31.
 */
public class CookieTest {

    public static void main(String[] args){
        WebServer server = new WebServer();

        server.get("/", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                System.out.println("Get Cookie: fav="+req.getCookie("fav"));
                res.send(req.getCookies().toString());
            }
        });

        server.get("/login", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.setCookie("test-1","2",-1000);
                res.setCookie("test","1",0);
                res.setCookie("user","jayin",10);
                res.setCookie("fav","py",600);
            }
        });


        server.get("/logout", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                res.removeCookie("fav");
                res.removeCookie("user");
                res.removeCookie("test");
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
