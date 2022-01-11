import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Hashtable;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import endPoint.EndPoint;
import parser.EndPointJSONParser;
import parser.ResponsesJSONParser;

class ResponsesJSONParserTest {
	
	Hashtable<String, EndPoint> endPoints = new Hashtable<String, EndPoint>();
	String jsonAndResponseObjectNull = "Cannot invoke \"org.json.simple.JSONObject.get(Object)\" "
			+ "because \"responseObject\" is null";
	
	@Test
	void testResponseParser() throws IOException, ParseException  {
		
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		parsedRequests.parseEndPointsJSON("src/EndPoints.json", endPoints);
		
		ResponsesJSONParser parseResponses = new ResponsesJSONParser();
		parseResponses.getResponsesForEndPoints("src/Responses.json", endPoints);
	}
	
	@Test
	void testEmptyResponseJSON() throws IOException, ParseException  {
		
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		parsedRequests.parseEndPointsJSON("src/EndPoints.json",endPoints);
		
		ResponsesJSONParser parseResponses = new ResponsesJSONParser();
		
		Throwable exception = assertThrows(Exception.class, () 
				-> parseResponses.getResponsesForEndPoints("tests/files/invalidResponse.json", endPoints));
		
		assertEquals(jsonAndResponseObjectNull, exception.getMessage());
	}


}	
 