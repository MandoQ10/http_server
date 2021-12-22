import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;

public class RequestParser {
	private InputStreamReader in; 
	private BufferedReader br; 
	private HashSet<String> acceptedHttpMethods = new HashSet<String>(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
	
	public RequestParser(InputStream clientInputStream) {
		this.in = new InputStreamReader(clientInputStream);
		this.br = new BufferedReader(in);
	}
	
	public String[] parse() throws Exception {
		String line;
		String[] requestParams; 
	
		line = br.readLine();
		
		String[] params = null;
		boolean firstIteration = true;
		
		while(!line.isBlank()) {
			if(firstIteration) {
				params = line.split(" ");
			}
			firstIteration = false;
			line = br.readLine();
		}
	
		String httpMethod = params[0];
		
		if(!acceptedHttpMethods.contains(httpMethod)) {
			throw new Exception("Invalid http method was requested");
		}
		
		String endPoint = params[1];	
		requestParams = new String[] {httpMethod, endPoint};
		
		return requestParams;
	}
}	