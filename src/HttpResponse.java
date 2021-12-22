import java.util.Arrays;
import java.util.Hashtable;

public class HttpResponse {
	String httpVersion;
	String statusCode;
	String reasonPhrase;
	String[] headers;
	String body;
	
	public HttpResponse(String httpVersion, String statusCode, String reasonPhrase, String[] headers, String body) {
		this.httpVersion = httpVersion;
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
		this.headers = headers;
		this.body = body;
	}
	
	public byte[] getFormattedResponse() {
		String statusLine = getStatusLine();
		String hrs = getFormattedHeaders();
		
		String responseBody = getFormattedBody();
		String response = statusLine + hrs + responseBody;
		
		return response.getBytes();
	}
	
	private String getStatusLine() {
		String statusLine = httpVersion + " " + statusCode + " " + reasonPhrase + "\r\n";
		return statusLine;
	}
	
	private String getFormattedHeaders() {
		String result = ""; 
		
		if(headers.length == 0 && body.isEmpty()) {
			return result;
		}
		
		if(headers.length == 0 && !body.isEmpty()) {
			return "\r\n";
		}
		
		for(int i = 0; i < headers.length; ++i) {
			result += headers[i] + "\r\n";
		}
		
		return result;
	}
	
	private String getFormattedBody() {
		return (body.isEmpty()) ? "\r\n" : body;
	}
	
}	

