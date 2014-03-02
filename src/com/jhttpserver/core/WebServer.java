package com.jhttpserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public static final int  PORT =8000;
	public static void main(String[] args) {
		ServerSocket sock = null;
		Thread connect = null;
		try {
			sock = new ServerSocket(PORT);
		} catch (IOException e1) {
			System.out.println("web server listened in port "+PORT);
		}
		while (true) {
			try {
				Socket connection = sock.accept();
				// 创建处理线程
				connect = new WebServerConnection(connection);
				connect.start();
				System.out.println("WebServer-->receive request No."+connect.getId());				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
