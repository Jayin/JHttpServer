package com.jhttpserver.interfaces;
/**
 * 请求信息接口
 * @author Jayin Ton
 *
 */
public interface IRequestInfo {
	public final static String GET = "GET";
	
    /** 活动请求方法*/
	public String getMethod();
	 /** 设置请求方法*/
	public void setMethod(String method);
	 /** 获得请求路径*/
	public String getPath();
	 /** 保存请求路径*/
	public void setPath(String path);
	/** 获得http版本信息*/
	public String getHttpVersion();
	/** 设置http版本*/
	public void setHttpVersion(String verison);
}
