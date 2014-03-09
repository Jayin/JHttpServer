package com.jhttpserver.interfaces;

public interface IWebServer {
	
   public void get(String url,Execution exe);
   
   public void post(String url,Execution exe);
}
