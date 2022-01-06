package server;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.simple.parser.ParseException;

import parser.RequestParser;
import router.Router;

public class Server {

	public static void main(String[] args) throws IOException, ParseException {
		int portNumber = 5000;		
		Router myRouter = new Router();
		
		myRouter.initalizeEndPointsWithResponses();
		
		try(ServerSocket serverSocket = new ServerSocket(portNumber)){
			while(true) {
				try(Socket clientSocket = serverSocket.accept()){
								
					OutputStream outStream = clientSocket.getOutputStream();
					RequestParser clientInputParser = new RequestParser(clientSocket.getInputStream());	
					
					String[] requestParams = clientInputParser.parse();				
					byte[] response = myRouter.getResponse(requestParams[1], requestParams[0]);
				
					outStream.write(response);
					
					outStream.flush();
					clientSocket.close();
				}
			}		
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}				