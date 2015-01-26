package com.jhttpserver.core;

import com.jhttpserver.entity.Constants;
import com.jhttpserver.entity.Handler;
import com.jhttpserver.interfaces.Execution;
import com.jhttpserver.interfaces.IMiddleWare;
import com.jhttpserver.interfaces.IWebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * a light-weight Http Server
 *
 * @author Jayin Ton
 */
public class WebServer implements IWebServer {
    /**
     * port
     */
    public static final int DefaultPort = 8000;
    /**
     * main server
     */
    private ServerSocket serverSocket = null;
    /**
     * mapping url and exe
     */
    private HashMap<String, Handler> handlers;
    /**
     * the list of middle
     */
    private List<IMiddleWare> middleWares;
    private int Status_Staring = 0;
    private int Status_Stop = 1;
    private int status = -1;

    ThreadPoolExecutor excutor = new ThreadPoolExecutor(2, 4, 2,
            TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10));

    public WebServer() {
        handlers = new HashMap<String, Handler>();
        middleWares = new ArrayList<IMiddleWare>();
    }

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
                excutor.submit(new ConnectionHandler(connection, handlers, middleWares));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void use(IMiddleWare middleWare) {
        middleWares.add(middleWare);
    }

    /**
     * 注册处理器
     * @param method 请求方法
     * @param router 请求路由
     * @param exe 执行器
     */
    private void registerHandler(String method,String router, Execution exe){
        if (handlers.get(router) == null) {
            this.handlers.put(router, new Handler());
        }
        this.handlers.get(router).register(method, exe);
    }

    @Override
    public void get(String router, Execution exe) {
        registerHandler(Constants.METHOD_GET, router, exe);
    }

    @Override
    public void post(String router, Execution exe) {
        registerHandler(Constants.METHOD_POST, router, exe);
    }

    @Override
    public void put(String router, Execution exe) {
        registerHandler(Constants.METHOD_PUT, router, exe);
    }

    @Override
    public void patch(String router, Execution exe) {
        registerHandler(Constants.METHOD_PATCH, router, exe);
    }

    @Override
    public void delete(String router, Execution exe) {
        registerHandler(Constants.METHOD_DELETE, router, exe);
    }

}
