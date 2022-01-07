
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import httpResponse.HttpResponse;

class HttpResponseTest {
	
	
	@Test	
	void testBasicResponse() throws IOException {
		HttpResponse response = new HttpResponse("HTTP/1.1", "200", "OK", new String[] {}, "");
		String expectedResponse = "HTTP/1.1 200 OK\r\n" + "\r\n";
		
		String actualResponse = new String(response.getFormattedResponse(), StandardCharsets.UTF_8);
		
		assertEquals(expectedResponse, actualResponse);
	}
			
	@Test	
	void testResponseWithHeadersAndBody() throws IOException {
		HttpResponse response = new HttpResponse("HTTP/1.1", "200", "OK", new String[] {"Date: Sun, 18 Oct 2012 10:36:20 GMT", "Allow: OPTIONS, GET"}, "Hello world");
		String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Date: Sun, 18 Oct 2012 10:36:20 GMT\r\n" + "Allow: OPTIONS, GET\r\n\r\n" + "Hello world" ;
		
		String actualResponse = new String(response.getFormattedResponse(), StandardCharsets.UTF_8);
		
		assertEquals(expectedResponse, actualResponse);
	}	
	
	@Test	
	void testWithBodyAndNoHeaders() throws IOException {
		HttpResponse response = new HttpResponse("HTTP/1.1", "200", "OK", new String[] {}, "Hello world");
		String expectedResponse = "HTTP/1.1 200 OK\r\n" + "\r\n" + "Hello world" ;
		
		String actualResponse = new String(response.getFormattedResponse(), StandardCharsets.UTF_8);
		
		assertEquals(expectedResponse, actualResponse);
	}
	
	@Test
	void testWithHeaderAndNoBody() throws IOException {
		HttpResponse response = new HttpResponse("HTTP/1.1", "200", "OK", new String[] {"Allow: OPTIONS, GET"}, "");
		String expectedResponse = "HTTP/1.1 200 OK\r\n" + "Allow: OPTIONS, GET\r\n" + "\r\n" ;
		
		String actualResponse = new String(response.getFormattedResponse(), StandardCharsets.UTF_8);
		
		assertEquals(expectedResponse, actualResponse);
	}
	
	@Test
	void testContentTypeHeaderAndBody() throws IOException {
		HttpResponse response = new HttpResponse("HTTP/1.1", "200", "OK", new String[] {"content-type: text/plain;charset=utf-8"}, "text response");
		String expectedResponse = "HTTP/1.1 200 OK\r\n" + "content-type: text/plain;charset=utf-8\r\n\r\n" + "text response" ;
		
		String actualResponse = new String(response.getFormattedResponse(), StandardCharsets.UTF_8);
		
		assertEquals(expectedResponse, actualResponse);
	}
}	