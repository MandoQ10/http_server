

import java.util.Hashtable;

public class EndPoint {
	String path;
	private String[] allowedMethods;
	private Hashtable<String, HttpResponse> requestsAndResponses;
	
	public EndPoint(String path, String[] allowedMethods) {
		this.path = path;
		this.allowedMethods = allowedMethods;
		this.requestsAndResponses = new Hashtable<String, HttpResponse>();
	}
	
	public byte[] getResponseForMethod(String httpMethod) {
		HttpResponse methodResponse = new HttpResponse("HTTP/1.1", "405", "NOT FOUND", new String[]{"Allow: " + getAvaliableMethods()}, "");

		if(!requestsAndResponses.containsKey(httpMethod)) {
			return methodResponse.getFormattedResponse();
		}
		return requestsAndResponses.get(httpMethod).getFormattedResponse();
	}

	public void addHttpMethodAndResponse(String httpMethod, String httpVersion, String statusCode, String reasonPhrase, String[] headers, String body){
		HttpResponse response = new HttpResponse(httpVersion, statusCode, reasonPhrase, headers, body);
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