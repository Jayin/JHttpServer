package com.jhttpserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.jhttpserver.interfaces.Execution;
import com.jhttpserver.interfaces.IWebServer;

/**
 * a light-weight Http Server
 * 
 * @author Jayin Ton
 * 
 */
public class WebServer implements IWebServer {
	/** port */
	public static final int DefaultPort = 8000;
	/** main server */
	private ServerSocket serverSocket = null;
	/** mapping url and exe */
	private HashMap<String, Execution> handlers;

	private int Status_Staring = 0;
	private int Status_Stop = 1;
	private int status = -1;

	ThreadPoolExecutor excutor = new ThreadPoolExecutor(2, 4, 2,
			TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10));

	private void initServer(int port) throws IOException {
			serverSocket = new ServerSocket(port);
			status = Status_Staring;
	 
	}

	public void listen(int port) throws IOException {
		initServer(port);
		if (status == Status_Staring) {
			System.out.println("web server listened in port:" + port);
		}
		while (true && status == Status_Staring) {
			try {
				Socket connection = serverSocket.accept();
 				excutor.submit(new ConnectionHandler(connection, handlers));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void get(String url, Execution exe) {
		if (handlers == null)
			handlers = new HashMap<String, Execution>();
		handlers.put(url, exe);
	}

	@Override
	public void post(String url, Execution exe) {
		if (handlers == null)
			handlers = new HashMap<String, Execution>();
		handlers.put(url, exe);
	}

}
