import java.util.*;
import java.net.*;
import java.io.*;

public final class HTTPRequest implements Runnable
{
    

        
        final static String CRLF = "\r\n";
                Socket socket;

                // Constructor
                public HTTPRequest(Socket socket) throws Exception 
                {
                        this.socket = socket;
                }

                // Implement the run() method of the Runnable interface.
                public void run()
                {
                       
                    try {
                        processRequest();
                        }
                    catch (Exception e) {
                        System.out.println(e);
                        }   
                     
                }

                private void processRequest() throws Exception
                {
                    
                // Get a reference to the socket's input and output streams.
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                // Set up input stream filters.
                
                BufferedReader br = new BufferedReader( new InputStreamReader(is));
                
                DataOutputStream dos = new DataOutputStream(os);
                
                String requestLine = br.readLine();
                
                // Display the request line.
                System.out.println();
                System.out.println(requestLine);
                
                // Get and display the header lines.
                String headerLine = null;
                while ((headerLine = br.readLine()).length() != 0) {
                System.out.println(headerLine);
                }
                
                os.close();
                br.close();
                socket.close();
                    
                    
                    
                        
                }
        
        
}