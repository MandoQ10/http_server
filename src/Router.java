import java.util.Hashtable;

public class Router {
	private Hashtable<String, EndPoint> endPoints;
	
	public Router() {
		this.endPoints = new Hashtable<String, EndPoint>();
	}
	
	public byte[] getRequestResponse(String httpMethod, String endPoint) {
		byte[] response;
		
		if(endPoints.containsKey(endPoint)) {
			EndPoint clientRequest = endPoints.get(endPoint);
			response = clientRequest.getResponseForMethod(httpMethod);	
		}else {
			response = "HTTP/1.1 404 NOT FOUND\r\n \r\n".getBytes();
		}
		return response;
	}
}
