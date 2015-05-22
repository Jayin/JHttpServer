package jhttpserver.interfaces;

import jhttpserver.entity.Request;
import jhttpserver.entity.Response;

/**
 * Created by jayin on 14/12/5.
 */
public interface IMiddleWare {

    public void work(Request req,Response res);
}
