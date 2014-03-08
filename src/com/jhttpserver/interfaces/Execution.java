package com.jhttpserver.interfaces;

import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;

public interface Execution {
	public void onExecute(Request req, Response res);
}
