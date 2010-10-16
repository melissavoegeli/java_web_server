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
                
                private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception
                {
                        
                        //Construct a 1K buffer to hold bytes on their way to socket
                        byte[] buffer = new byte[1024];
                        int bytes = 0;
                        
                        //Copy requested file into the socket's output stream
                        while((bytes = fis.read(buffer)) != -1) {
                                os.write(buffer, 0, bytes);
                        }
                        
                        
                }
                
                private static String contentType(String fileName)
                {
                        
                        if(fileName.endsWith(".htm") || fileName.endsWith(".html"))
                        {
                                return "text/html";
                        }
                        if(fileName.endsWith(".jpg"))
                        {
                                return "image/jpg";
                        }
                        if(fileName.endsWith(".gif"))
                        {
                                return "image/gif";
                        }
                        return "application/octet-stream";
                        
                        
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
                
                StringTokenizer tokens = new StringTokenizer(requestLine);
                tokens.nextToken();
                String fileName = tokens.nextToken();
                if (fileName.equals("/"))
                    {
                        fileName = "/index.html";
                    }
                fileName = "." + fileName;
                
                FileInputStream fis = null;
                boolean fileExists = true;
                try {
                        fis = new FileInputStream(fileName);
                }
                catch (FileNotFoundException e) {
                        fileExists = false;
                }
                
                String statusLine = null;
                String contentTypeLine = null;
                String entityBody = null;
                
                
                if (fileExists) {
                        
                        statusLine = "HTTP/1.0 200 OK" + CRLF;
                        contentTypeLine = "Content-type: " +
                                contentType( fileName ) + CRLF;
                }
                else {
                        
                        statusLine = "HTTP/1.0 404 NOT FOUND" + CRLF;
                        contentTypeLine = "Content-type: text/html" + CRLF;
                        entityBody = "<HTML>" +
                                "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                                "<BODY>Not Found</BODY></HTML>";
                }
                
                dos.writeBytes(statusLine);
                dos.writeBytes(contentTypeLine);
                dos.writeBytes(CRLF);
                
                if (fileExists) {
                
                        sendBytes(fis, os);
                        fis.close();
                        

                }
                else {
                        dos.writeBytes(entityBody);
                }
                
                
                os.close();
                br.close();
                socket.close();
                    
                 
                }
        
        
}