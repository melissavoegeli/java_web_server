import java.util.*;
import java.net.*;
import java.io.*;


public class Server {
    
    Date now = new Date();
    
	public static void main(String[] args) {
    
    
	try
	{
		ServerSocket serverSock = new ServerSocket(8675);
		
		while (true)
		{
        
			Socket connectionSock = serverSock.accept();
			
			// Construct an object to process the HTTP request message.
			 HTTPRequest request = new HTTPRequest( connectionSock );

			// Create a new thread to process the request.
			Thread thread = new Thread(request);
			
			thread.start();
	
	
		}
	
	
	}
	
	catch (IOException e)
	{
		System.out.println(e.getMessage());
	}
	catch (Exception e)
	{
		
		System.out.println(e.getMessage());
           
	}//End of main
	
	}
	
}
