

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {

	public static void main(String[] args) throws IOException {
		int portNumber = 5000;		
		
		Hashtable<String, EndPoint> endPoints = new Hashtable<String, EndPoint>();
		
		EndPoint redirect = new EndPoint("/redirect", new String[] {"GET"});
		EndPoint method_options = new EndPoint("/method_options", new String[] {"GET, OPTIONS, HEAD"});
		EndPoint method_options2 = new EndPoint("/method_options2", new String[] {"GET, HEAD, OPTIONS, PUT, POST"});
		EndPoint headReqeust = new EndPoint("/head_request", new String[] {"HEAD, OPTIONS"});
		EndPoint simpleGetWithBody = new EndPoint("/simple_get_with_body", new String[] {"GET"});
		EndPoint simpleGet = new EndPoint("/simple_get", new String[] {"GET", "HEAD"});
		EndPoint echoBody = new EndPoint("/echo_body", new String[] {"POST"});
		
		endPoints.put(redirect.path, redirect);
		endPoints.put(method_options.path, method_options);
		endPoints.put(method_options2.path, method_options2);
		endPoints.put(headReqeust.path, headReqeust);
		endPoints.put(simpleGetWithBody.path, simpleGetWithBody);
		endPoints.put(simpleGet.path, simpleGet);
		endPoints.put(echoBody.path, echoBody);
		
		redirect.addHttpMethodAndResponse("GET", "HTTP/1.1", "301", "Moved Permanently", new String[] {"Location: http://127.0.0.1:5000/simple_get"}, "" );
		
		method_options.addHttpMethodAndResponse("OPTIONS", "HTTP/1.1", "200", "OK", new String[] {"Allow: GET, HEAD, OPTIONS"}, "");
		
		method_options2.addHttpMethodAndResponse("OPTIONS", "HTTP/1.1", "200", "OK", new String[] {"Allow: GET, HEAD, OPTIONS, POST, PUT"}, "");
		
		headReqeust.addHttpMethodAndResponse("HEAD", "HTTP/1.1", "200", "OK", new String[] {}, "");
		
		simpleGetWithBody.addHttpMethodAndResponse("GET", "HTTP/1.1", "200", "OK", new String[] {}, "Hello world");
		
		simpleGet.addHttpMethodAndResponse("GET", "HTTP/1.1", "200", "OK", new String[] {}, "");
		simpleGet.addHttpMethodAndResponse("HEAD", "HTTP/1.1", "200", "OK", new String[] {}, "");
		
		echoBody.addHttpMethodAndResponse("POST", "HTTP/1.1", "200", "OK", new String[] {}, "some body");
		
		try(ServerSocket serverSocket = new ServerSocket(portNumber)){
			while(true) {
				try(Socket clientSocket = serverSocket.accept()){
							
					OutputStream outStream = clientSocket.getOutputStream();
					RequestParser clientInputParser = new RequestParser(clientSocket.getInputStream());
					
					String[] requestParams = clientInputParser.parse();
				
					if(endPoints.containsKey(requestParams[1])) {
						EndPoint clientRequest = endPoints.get(requestParams[1]);
						outStream.write(clientRequest.getResponseForMethod(requestParams[0]));
						
					}else {
						outStream.write("HTTP/1.1 404 NOT FOUND\r\n".getBytes());
						outStream.write("\r\n".getBytes());
					}
					
					outStream.flush();
					clientSocket.close();
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
