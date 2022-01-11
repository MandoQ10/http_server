package endPoint;

import java.io.IOException;
import java.util.Hashtable;
import org.json.simple.parser.ParseException;

import httpResponse.HttpResponse;

public class EndPoint {
	private String path;
	private String[] allowedMethods;
	private Hashtable<String, HttpResponse> requestsAndResponses;
	
	public EndPoint(String path, String[] allowedMethods) {
		this.path = path;
		this.allowedMethods = allowedMethods;
		this.requestsAndResponses = new Hashtable<String, HttpResponse>();
	}
	
	public String getPath() {
		return path;
	}	
		
	public byte[] getResponseForMethod(String httpMethod) throws ParseException, IOException {
		
		HttpResponse methodNotFoundResponse = new HttpResponse("HTTP/1.1", "405", "Not Found", new String[]{"Allow: " + getAvaliableMethods()}, "");

		if(!requestsAndResponses.containsKey(httpMethod)) {
			return methodNotFoundResponse.getFormattedResponse();
		}	
		
		return requestsAndResponses.get(httpMethod).getFormattedResponse();
	}

	public void addHttpMethodAndResponse(HttpResponse response, String httpMethod){
		requestsAndResponses.put(httpMethod, response);
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