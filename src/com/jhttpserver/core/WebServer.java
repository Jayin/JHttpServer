package com.jhttpserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jhttpserver.entity.Request;
import com.jhttpserver.entity.Response;
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

	private int Status_Staring = 0;
	private int Status_Stop = 1;
	private int status = -1;

	ThreadPoolExecutor excutor = new ThreadPoolExecutor(2, 5, 2,
			TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10));

	private void initServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			status = Status_Staring;
		} catch (IOException e1) {
			System.out.println("web server faild!");
			status = Status_Stop;
		}
	}

	public void listen(int port) {
		initServer(port);
		if (status == Status_Staring) {
			System.out.println("web server listened in port:" + port);
		}
		while (true) {
			try {
				Socket connection = serverSocket.accept();
				excutor.submit(new ConnectionHandler(connection,
						new Execution() {

							@Override
							public void onExecute(Request req, Response res) {
								res.send("Hello world");
							}
						}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
