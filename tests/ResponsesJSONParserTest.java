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
	
	//Is it better to test the actual endPoints and responses or create endPoints and responses manually?
	//Since response parser depends on the endPoint parser to fill a hastable with endpoints 
	//However for testing reasons, it shouldn't need to rely on the EndPointParser?
	@Test
	void testResponseParser() throws IOException, ParseException  {
		
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		parsedRequests.parseEndPointsJSON(endPoints);
		
		ResponsesJSONParser parseResponses = new ResponsesJSONParser();
		parseResponses.getResponsesForEndPoints(endPoints);
	}

}
 