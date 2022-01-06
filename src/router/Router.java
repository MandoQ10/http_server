package router;
import java.util.Hashtable;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import endPoint.EndPoint;
import httpResponse.HttpResponse;
import parser.EndPointJSONParser;
import parser.ResponsesJSONParser;

public class Router {
	private Hashtable<String, EndPoint> endPoints;
	
	public Router() {
		this.endPoints = new Hashtable<String, EndPoint>();
	}
	
	public void initalizeEndPointsWithResponses() throws IOException, ParseException {
		getRequestsJSON();
		addResponsesToEndPoints();
	}
	
	public byte[] getResponse(String endPointPath, String httpMethod) throws ParseException, IOException {
		if(endPoints.containsKey(endPointPath)) {
			EndPoint clientRequest = endPoints.get(endPointPath);
			return clientRequest.getResponseForMethod(httpMethod);
		}
	
		HttpResponse endPointNotFoundResponse = new HttpResponse("HTTP/1.1", "404", "NOT FOUND", new String[]{}, "");
		return endPointNotFoundResponse.getFormattedResponse();
	}
	
	private void getRequestsJSON() throws IOException, ParseException {
		EndPointJSONParser parsedRequests = new EndPointJSONParser();
		parsedRequests.getRequestsJSON(endPoints);
	}
	
	private void addResponsesToEndPoints() throws IOException, ParseException {
		ResponsesJSONParser responsesParser = new ResponsesJSONParser();
		responsesParser.getResponsesForEndPoints(endPoints);
	}
	
}									