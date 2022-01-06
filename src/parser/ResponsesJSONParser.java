import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ResponsesJSONParser {
	
	@SuppressWarnings("unchecked")
	public void getResponsesForEndPoints(Hashtable<String, EndPoint> routerEndPoints) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		
		FileReader reader = new FileReader("src/Responses.json");
		
		Object obj = jsonParser.parse(reader);
		JSONArray responseList = (JSONArray) obj;
		
		responseList.forEach(response -> { 
			HttpResponse parsedResponse = parseResponse( (JSONObject) response);
			String httpMethod = getHttpMethod((JSONObject) response);
			String path = getPath((JSONObject) response);
			routerEndPoints.get(path).addHttpMethodAndResponse(parsedResponse, httpMethod);
		});
       
	}		

	private static  String getPath(JSONObject response) {	
        JSONObject responseObject = (JSONObject) response.get("response");
        String path = (String) responseObject.get("path");  
        
        return path;	
	}
	
	private static HttpResponse parseResponse(JSONObject response) {
        JSONObject responseObject = (JSONObject) response.get("response");
     
        String httpVersion = (String) responseObject.get("httpVersion");
        String statusCode = (String) responseObject.get("statusCode");
        String reasonPhrase = (String) responseObject.get("reasonPhrase");
        
        JSONArray JSONHeadersList = (JSONArray) responseObject.get("headers");
        String[] headers = new String[] {};
        
        if(!JSONHeadersList.isEmpty()) {
        	headers = new String[JSONHeadersList.size()];
        	
            for(int i = 0; i < JSONHeadersList.size(); ++i) {
            	headers[i] = parseHeaders((JSONObject) JSONHeadersList.get(i));
            }
        }
     
        String body = (String) responseObject.get("body");
        
        return new HttpResponse(httpVersion, statusCode, reasonPhrase, headers, body);
	}
	
	private static String parseHeaders(JSONObject headers) {
       
        JSONObject header = (JSONObject) headers.get("header");
        String key = (String) header.get("key");  
        String value = (String) header.get("value");  
        
        String result = key + " " + value;
        return result;
	}	
	
	private static String getHttpMethod(JSONObject response) {
        JSONObject responseObject = (JSONObject) response.get("response");
        String httpMethod = (String) responseObject.get("httpMethod");  
        
        return httpMethod;
	}	
}	
