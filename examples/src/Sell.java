import com.jhttpserver.core.WebServer;
import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
import com.jhttpserver.interfaces.Execution;

import java.io.IOException;

/**
 * The sell system.
 * Created by jayin on 14/12/2.
 */
public class Sell{
    public int tickets = 100;

    public synchronized boolean buy(){
            if(this.tickets > 0 ){
                System.out.println("you sell the ticket No." + tickets);
                tickets--;
                return true;
            }else{
                System.out.println("sold out !");
                return false;
            }

    }

    public static void main(String[] args) throws IOException {
        final Sell sellSystem = new Sell();

        WebServer app = new WebServer();

        app.get("/buy1", new Execution() {
            @Override
            public void onExecute(Request req, Response res) {
                    if(sellSystem.buy()){
                        res.send("ok");
                    }else{
                        res.send("faild");
                    }


            }
        });

        app.listen(WebServer.DefaultPort);
    }
}
