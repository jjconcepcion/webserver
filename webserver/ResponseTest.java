import java.net.*;
import java.io.*;
import ServerExceptions.*;

public class ResponseTest {
  public static final int DEFAULT_PORT = 3100;

  public static void main( String[] args ) throws IOException {

    ServerSocket socket = new ServerSocket( DEFAULT_PORT );
    Socket client = null;
    Request request = null;

    client = socket.accept();
    request = new Request( client.getInputStream() );
    
    try {
      request.parse();
    } catch( BadRequestException e ) {
      System.out.println( "Exception: " + e.getMessage() );
      System.exit(1);
    }
    
    System.out.println("Sending Response");
    Response response = new Response();
    response.send( client.getOutputStream() );
    
  }
  
}
