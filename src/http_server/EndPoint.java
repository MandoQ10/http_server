package http_server;

import java.util.Hashtable;

public class EndPoint {
	String path;
	private String[] allowedMethods;
	private Hashtable<String, byte[]> requestsAndResponses;
	
	public EndPoint(String path, String[] allowedMethods) {
		this.path = path;
		this.allowedMethods = allowedMethods;
		this.requestsAndResponses = new Hashtable<String, byte[]>();
	}
	
	public byte[] getResponseForMethod(String httpMethod) {
		String statusLine = "";
		String header = "";

		if(!requestsAndResponses.containsKey(httpMethod)) {
			statusLine = "HTTP/1.1 405 NOT FOUND\r\n";
			
			header = "Allow:" + getAvaliableMethods() + "\r\n";
			
			String response = statusLine + header + "\r\n";
			return response.getBytes();
		}
		return requestsAndResponses.get(httpMethod);
	}
	
	public void addHttpMethodAndResponse(String httpMethod, String response){
		requestsAndResponses.put(httpMethod, response.getBytes());
	}
	
	private String getAvaliableMethods() {
		String allMethods = "";
		
		for(int i = 0; i < allowedMethods.length; ++i) {
			if(i == allowedMethods.length - 1) {
				allMethods += allowedMethods[i];
			}else {
				allMethods += allowedMethods[i] + ",";
			}
		}
		return allMethods;
	}
}
