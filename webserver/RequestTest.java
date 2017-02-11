import java.net.*;
import java.io.*;

public class RequestTest {
  public static final int DEFAULT_PORT = 3100;

  public static void main( String[] args ) throws IOException {

    ServerSocket socket = new ServerSocket( DEFAULT_PORT );
    Socket client = null;
    Request request = null;

    client = socket.accept();
    request = new Request( client.getInputStream() );
    request.parse();
    
    printStartLine( request );
    
    if( request.lookupHeader( "Content-Length") == null ) {
      System.out.println( "Request has no body" );
    } else {
      System.out.println( "Request has a body" );
    }
      
    
    client.close();
  }
  
  public static void printStartLine(Request request) {
    System.out.println( ">>Displaying request start line:" );
    System.out.print( request.getVerb() + " " + request.getUri() + " " );
    System.out.println( request.getHttpVersion() );
  }
}
