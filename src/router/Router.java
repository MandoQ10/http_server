import java.util.Hashtable;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Router {
	private Hashtable<String, EndPoint> endPoints;
	
	public Router() {
		this.endPoints = new Hashtable<String, EndPoint>();
	}
	
	public void GetRequestsJSON() throws IOException, ParseException {
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		parsedRequests.GetRequestsJSON(endPoints);
	}
	
	public void getResponsesForEndPoints() throws IOException, ParseException {
		ResponsesJSONParser responsesParser = new ResponsesJSONParser();
		responsesParser.getResponsesForEndPoints(endPoints);
	}
	
	public byte[] getRequest(String endPointPath, String httpMethod) throws ParseException, IOException {
		if(endPoints.containsKey(endPointPath)) {
			EndPoint clientRequest = endPoints.get(endPointPath);
			return clientRequest.getResponseForMethod(httpMethod);
		}
	
		HttpResponse endPointNotFoundResponse = new HttpResponse("HTTP/1.1", "404", "NOT FOUND", new String[]{}, "");
		return endPointNotFoundResponse.getFormattedResponse();
	}
	
}				