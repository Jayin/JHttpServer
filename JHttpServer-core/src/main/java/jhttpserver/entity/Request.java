package jhttpserver.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Request
 * 
 * @author Jayin Ton
 * 
 */
public class Request {
	private String httpVersion;
	private String method;
	private String path;
	private List<Header> headers;// 请求头
	private HashMap<String, String> data; //method-post
	private HashMap<String, String> params;//method-get
	private List<Cookie> cookies;
	private String body; // body

	public Request() {
		headers = new ArrayList<Header>();
		data = new HashMap<String, String>();
		params = new HashMap<String, String>();
		cookies = new ArrayList<Cookie>();
	}

	public List<Cookie> getCookies(){
		return this.cookies;
	}

	public Cookie getCookie(String field){
		for(Cookie c : this.cookies){
			if(c.getField().equals(field)){
				return c;
			}
		}
		return null;
	}

	public void setCookies(List<Cookie> cookies){
		this.cookies.clear();
		this.cookies.addAll(cookies);
	}
	
	public void addData(String name, String value){
		this.data.put(name, value);
	}
	
	public String getData(String name){
		return this.data.get(name);
	}

    public void addParam(String name,String value){
		this.params.put(name,value);
	}

	public String getParam(String name){
		return this.params.get(name);
	}

	public HashMap<String, String> getParams(){
		return this.params;
	}
	public void addHeader(String name, String value) {
		this.headers.add(new Header(name, value));
	}

	public Header getHeader(String field) {
		field = field.toLowerCase();
		for(Header h : headers){
			if(h.getField().equals(field))
				return h;
		}
		return null;
	}

	public List<Header> getHeaders(){
		return this.headers;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Request{" +
				"httpVersion='" + httpVersion + '\'' +
				", method='" + method + '\'' +
				", path='" + path + '\'' +
				", headers=" + headers +
				", data=" + data +
				", params=" + params +
				", body='" + body + '\'' +
				'}';
	}
}
