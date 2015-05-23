import jhttpserver.core.WebServer;
import jhttpserver.entity.Header;
import jhttpserver.entity.Request;
import jhttpserver.entity.Response;
import jhttpserver.interfaces.Execution;
import jhttpserver.interfaces.IMiddleWare;

import java.io.IOException;
import java.util.List;

/**
 * Created by jayin on 14/12/5.
 */
public class MiddleWareTest {


    public static void main(String[] args) throws IOException {
        WebServer app = new WebServer();
        app.use(new IMiddleWare() {
            @Override
            public boolean onWork(Request req, Response res) {
                System.out.println("-- middleware 1 works");
                return true;
            }
        });

        app.use(new IMiddleWare() {
            @Override
            public boolean onWork(Request req, Response res) {
                System.out.println("-- middleware 2 works");
                List<Header> headers = req.getHeaders();
                for(Header h : headers){
                    System.out.println(h.toString());
                }
                return true;
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
