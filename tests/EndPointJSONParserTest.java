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

class EndPointJSONParserTest {
	
	String jsonAndEndPointObjectNull = "Cannot invoke \"org.json.simple.JSONObject.get(Object)\" "
			+ "because \"endPointObject\" is null";
	
	private int getJSONFileSize(String filePath) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filePath);
		
		Object obj = jsonParser.parse(reader);
		JSONArray endPointList = (JSONArray) obj;
	
		return endPointList.size();
	}
	
	@Test
	void testServerEndPointsParseCorrectly() throws IOException, ParseException {
		EndPointJSONParser parser = new EndPointJSONParser();
		Hashtable<String, EndPoint> routerEndPoints =  new Hashtable<String, EndPoint>();
		
		int expectedEndPointsSize = getJSONFileSize("src/EndPoints.json");
		
		parser.parseEndPointsJSON("src/EndPoints.json", routerEndPoints);
		
		assertEquals(expectedEndPointsSize, routerEndPoints.size());	
	}	
	
	@Test
	void testInvalidEndPointsJSON() throws IOException, ParseException {
		EndPointJSONParser parser = new EndPointJSONParser();
		Hashtable<String, EndPoint> routerEndPoints =  new Hashtable<String, EndPoint>();
		
		Throwable exception = assertThrows(Exception.class, () 
				-> parser.parseEndPointsJSON("tests/files/invalidEndPoints.json", routerEndPoints));
		
		assertEquals(jsonAndEndPointObjectNull, exception.getMessage());
	}	

}

