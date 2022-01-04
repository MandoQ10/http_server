import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EndPointJSONParser {
	
	@SuppressWarnings("unchecked")
	public void GetRequestsJSON(Hashtable<String, EndPoint> routerEndPoints) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader("src/EndPoints.json");
		
		Object obj = jsonParser.parse(reader);
		JSONArray endPointList = (JSONArray) obj;
		
		endPointList.forEach(endPoint -> { 
			EndPoint parsedEndPoint = parseEndPointObject( (JSONObject) endPoint);
			routerEndPoints.put(parsedEndPoint.path, parsedEndPoint);
		});
	}
	
	@SuppressWarnings("unchecked")
	private static EndPoint parseEndPointObject(JSONObject endPoint) {
        JSONObject endPointObject = (JSONObject) endPoint.get("endPoint");
        
        String path = (String) endPointObject.get("path");  
        JSONArray httpMethods = (JSONArray) endPointObject.get("allowedHttpMethods");
        
        String[] httpMethodArray = (String[]) httpMethods.toArray(new String[httpMethods.size()]);
        
        return new EndPoint(path, httpMethodArray);
	}
}	