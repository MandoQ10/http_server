

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class RequestParserTest {
	
	String simpleGetRequest = "GET /redirect HTTP/1.1\n \r\n";
	String invalidHttpMethodRequest = "RANDOMMETHOD /redirect HTTP/1.1\n \r\n";
	
	@Test
	void testParse() throws IOException, Exception {
		InputStream request = new ByteArrayInputStream(simpleGetRequest.getBytes());
		RequestParser pr = new RequestParser(request);
		String[] requestParams = pr.parse();
		
		assertEquals(2, requestParams.length);
		assertArrayEquals(new String[]{"GET", "/redirect"}, requestParams);
	}

	@Test
	void testInvalidHttpMethod() throws IOException,Exception {
		InputStream request = new ByteArrayInputStream(invalidHttpMethodRequest.getBytes());
		RequestParser pr = new RequestParser(request);
		Throwable exception = assertThrows(Exception.class, () -> pr.parse());
		
		assertEquals("Invalid http method was requested", exception.getMessage());
	}
	
}				