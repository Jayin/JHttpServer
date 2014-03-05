package com.jhttpserver.entity;

import java.io.Serializable;
import java.util.HashMap;

public class Header implements Serializable {
	private static final long serialVersionUID = 3325736547362981588L;
    private HashMap<String, String> mHeaders = new HashMap<String, String>();
    
    public void put(String name,String value){
    	this.mHeaders.put(name, value);
    }
    
    
}
