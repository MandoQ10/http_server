


import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import endPoint.EndPoint;
import httpResponse.HttpResponse; 

class EndPointTest {
	
	public class FakeHttpResponse extends HttpResponse {

		public FakeHttpResponse(String httpVersion, String statusCode, String reasonPhrase, String[] headers,
				String body) {
			super(httpVersion, statusCode, reasonPhrase, headers, body);
		}
			
		public byte[] getFormattedResponse() throws IOException {
			byte[] response = ("HTTP/1.1 200 OK\r\n" + "\r\n" + "\r\n").getBytes();		
			
			return response;
		}	
	};
	
	public class MethodNotFoundHttpREsponse extends HttpResponse {
		
		public MethodNotFoundHttpREsponse(String httpVersion, String statusCode, String reasonPhrase, String[] headers,
				String body) {
			super(httpVersion, statusCode, reasonPhrase, headers, body);
		}

		
		public byte[] getFormattedResponse() throws IOException {
			byte[] response = ("HTTP/1.1 405 Not Found\r\n" + "Allow: GET, HEAD\r\n" + "\r\n").getBytes();		
			
			return response;
		}	
	};

	@Test
	void testBasicEndPoint() throws ParseException, IOException {
		EndPoint testEndPoint = new EndPoint("/testing", new String[] {"GET"});
		FakeHttpResponse fake = new FakeHttpResponse("HTTP/1.1", "200", "OK", new String[] {}, "");
		testEndPoint.addHttpMethodAndResponse(fake, "GET");
		
		assertArrayEquals(testEndPoint.getResponseForMethod("GET"), fake.getFormattedResponse());
	}
	
	@Test
	void testEndPointWithMethodNotFoundResposne() throws ParseException, IOException {
		EndPoint testEndPoint = new EndPoint("/method_not_found", new String[] {"GET, HEAD"});
		
		MethodNotFoundHttpREsponse fake = new MethodNotFoundHttpREsponse("HTTP/1.1", "405", "Not Found", new String[] {"GET", "HEAD"}, "");
		testEndPoint.addHttpMethodAndResponse(fake, "GET");
		
		assertArrayEquals(testEndPoint.getResponseForMethod("POST"), fake.getFormattedResponse());
	}

}
