package httpResponse;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
			
	public byte[] getFormattedResponse() throws IOException {
			
		String statusLine = getStatusLine();
		String hrs = getFormattedHeaders();
		byte[] responseBody = getFormattedBody();
		
		String response = statusLine + hrs ;
			
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
         outputStream.write( response.getBytes() );
        outputStream.write( responseBody );

        byte combinedByteArray[] = outputStream.toByteArray();
		
		return combinedByteArray;
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
		
		if(headers.length != 0 && body.isEmpty()) {
			
			for(int i = 0; i < headers.length; ++i) {
				result += headers[i] + "\r\n";
			}	
			return result;
		}
		
		for(int i = 0; i < headers.length; ++i) {
			result += headers[i] + "\r\n";
		}	
	
		return result + "\r\n";
	}
	
	private boolean containsImageHeader() {
		for(int i = 0; i < headers.length; ++i) {
			
			if(headers[i].contains("image")) {
				return true;
			}
		}
		return false;
	}
	
	private byte[] getFormattedBody() throws IOException {
		
		if(containsImageHeader() || body.contains("src/files")) {
			
			File imageFile = new File(body);
			byte [] imageByteArray  = new byte [(int)imageFile.length()];
	        FileInputStream fileinStream = new FileInputStream(imageFile);
	        BufferedInputStream bis = new BufferedInputStream(fileinStream);
	        bis.read(imageByteArray, 0, imageByteArray.length);
	        bis.close();
	        
	        return imageByteArray;
		}
	
		return (body.isEmpty()) ? "\r\n".getBytes() : body.getBytes();
	}
	
}		

