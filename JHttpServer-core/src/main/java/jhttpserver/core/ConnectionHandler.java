package jhttpserver.core;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

import jhttpserver.config.ServerConfig;
import jhttpserver.entity.Constants;
import jhttpserver.entity.Handler;
import jhttpserver.entity.Request;
import jhttpserver.entity.Response;
import jhttpserver.interfaces.IMiddleWare;
import jhttpserver.utils.RequestParser;

public class ConnectionHandler implements Runnable {
	private int SOCKET_READ_TIMEOUT = 15000; //15s
	private Socket connection;
	private long start = 0;
	private long end = 0;
	private InputStream in = null;
	private Request request;
	private Response response;
	private Handler handler;
	private HashMap<String, Handler> handlers;
	private List<IMiddleWare> middleWares;//middleWares = new ArrayList<IMiddleWare>();

	private WebServer app;

	public ConnectionHandler(Socket connection, WebServer app) throws SocketException {
		this.app = app;
		this.connection = connection;
		this.connection.setSoTimeout(SOCKET_READ_TIMEOUT);
		this.handlers = app.getHandlers();
		this.middleWares = app.getMiddleWares();
	}

	@Override
	public void run() {
		try {
			onStart();
			onParseBody();
			if (request != null) {
				response.appendHeader("Server", "JHttpServer");
				if (app.getServerConfig().cors){
					response.appendHeader("Access-Control-Allow-Origin", "*");
				}
				for(IMiddleWare m : middleWares){
					if(!m.onWork(request, response)){
						return;
					}
				}
				handler = handlers.get(request.getPath());
				if (handler == null || handler.getExecution(request.getMethod()) == null) {
					//check for the static file
					File tmpFile = new File(this.app.getServerConfig().root + File.separator + request.getPath());
					//check for static file
					if(tmpFile.exists() && tmpFile.isFile()){
						response.send(tmpFile);
					}else{
						//directory index append index.html/index.php/etc
						//ref: https://github.com/Jayin/JHttpServer/issues/15
						if(tmpFile.isDirectory()){
							for(String dir_index : this.app.getServerConfig().DirectoryIndex){
								tmpFile = new File(this.app.getServerConfig().root + File.separator + dir_index);
								if (tmpFile.exists()){
									break;
								}
							}
							if(tmpFile.exists()){
								response.send(tmpFile);
							}else{
								response.send(404, "Cannot {m} {r}".replace("{m}", request.getMethod()).replace("{r}", request.getPath()));
							}
						}else{
							response.send(404, "Cannot {m} {r}".replace("{m}", request.getMethod()).replace("{r}", request.getPath()));
						}
					}
				} else {
					handler.onExecute(request, response);
				}
			}
		} catch(SocketTimeoutException e){
			//Read time out on onParseBody()
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			onComplete();
		}
	}

	public void onStart() throws IOException {
		start = System.currentTimeMillis();
		response = new Response(connection);
	}

	public void onParseBody() throws IOException {
		in = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		int type = 0;
		request = new Request();
		while ((line = br.readLine()) != null) {
			if (line.equals(""))
				type = 2;
			switch (type) {
			case 0: // initial line
				RequestParser.parseInitialLine(request, line);
				type++;
				break;
			case 1: // header
				RequestParser.parseHeader(request, line);
				break;
			case 2: // [blank line here]
				type++;
				// parse body
				if (request.getMethod().equals(Constants.METHOD_POST)) {
					int content_length = Integer.parseInt(request
							.getHeader("Content-Length").getValue());
					char[] chars = new char[1024];
					int count = 0;
					String body = "";
					while (content_length > 0
							&& (count = br.read(chars, 0, 1024)) != -1) {
						content_length -= count;
						body += new String(chars, 0, count);
					}
					request.setBody(body);
					RequestParser.parseBody(request, body);
				}
				return;
			default:
				return;
			}
		}

	}

	public void onComplete() {
		if(!response.isSend()){
			response.send("");
		}
		if(request.getMethod() != null && request.getPath() != null){
			end = System.currentTimeMillis();
			System.out.println(request.getMethod() + " " + request.getPath() + " "
					+ (end - start) + "ms");
		}
		close(connection);
	}
	
	private void close(Closeable closeable){
		if(closeable!=null){
			try {
				closeable.close();
			} catch (IOException e) {
				System.out.println(this.getClass().getName()+"close error");
			}
		}
	}
}
