package com.jhttpserver.entity;

import com.jhttpserver.interfaces.Execution;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求处理句柄
 */
public class Handler {
    /**
     * 处理器
     */
    private Map<String ,Execution> exes = new HashMap<String,Execution>();

    public Handler(){

    }
    /**
     * 注册对应的处理器
     * @param method
     * @param exe
     */
    public void register(String method,Execution exe){
        this.exes.put(method.toUpperCase(),exe);
    }

    /**
     * 根据请求方法调用对应的处理器
     * @param request
     * @param response
     */
    public void onExecute(Request request, Response response) {
       if(exes.get(request.getMethod().toUpperCase()) != null){
           exes.get(request.getMethod().toUpperCase()).onExecute(request,response);
       }else{
           //TODO 默认一个GET?
           //TODO 跑出一个异常 or 500？
           System.out.println("没有对应的处理器");
       }

    }
}
