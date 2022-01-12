import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
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
	void testParserWhenResponseExistsWithoutEndPoint() throws IOException, ParseException  {
		int numberOfEndPoints = 2;
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		ResponsesJSONParser parseResponses = new ResponsesJSONParser();
		
		parsedRequests.parseEndPointsJSON("tests/files/endPoints.json", endPoints);
		Throwable exception = assertThrows(Exception.class, () 
				-> parseResponses.getResponsesForEndPoints("tests/files/responses.json", endPoints));
		
		assertEquals(numberOfEndPoints, endPoints.size());		
		assertEquals("The Response's EndPoint does not exist", exception.getMessage());
	}	
	
	@Test
	void testResponsesAreAddedToEndPoints() throws IOException, ParseException  {
		int numberOfEndPoints = 2;
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		ResponsesJSONParser parseResponses = new ResponsesJSONParser();
		
		parsedRequests.parseEndPointsJSON("tests/files/endPoints.json", endPoints);
		parseResponses.getResponsesForEndPoints("tests/files/responsesWithExactEndPoints.json", endPoints);
	
		assertEquals(numberOfEndPoints, endPoints.size());		
	}	
		
	@Test
	void testInvalidResponses() throws IOException, ParseException  {
			
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		ResponsesJSONParser parseResponses = new ResponsesJSONParser();
		
		parsedRequests.parseEndPointsJSON("src/EndPoints.json",endPoints);
		Throwable exception = assertThrows(Exception.class, () 
				-> parseResponses.getResponsesForEndPoints("tests/files/invalidResponse.json", endPoints));
		
		assertEquals(jsonAndResponseObjectNull, exception.getMessage());
	}

}	
 