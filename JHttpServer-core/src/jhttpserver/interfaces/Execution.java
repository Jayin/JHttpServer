package jhttpserver.interfaces;

import jhttpserver.entity.Request;
import jhttpserver.entity.Response;

public interface Execution {

	public void onExecute(Request req, Response res);
}
