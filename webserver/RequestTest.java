import java.net.*;
import java.io.*;
import ServerExceptions.*;

public class RequestTest {
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
    
    printStartLine( request );
    
    
    System.out.println( "\n>>Displaying Host header" );
    printHeader( request, "Host" );
    
    if( request.hasBody() ) {
      System.out.println( "\n>>Displaying request body as UTF-8 string" );
      System.out.println( new String( request.getBody(), "UTF-8" ) );
    } else {
      System.out.println( "\n>>Request has no body" );
    }
      
    
    client.close();
  }
  
  public static void printStartLine(Request request) {
    System.out.println( ">>Displaying request start line:" );
    System.out.print( request.getVerb() + " " + request.getUri() + " " );
    System.out.println( request.getHttpVersion() );
  }
  
  public static void printHeader( Request request, String header ) {
    System.out.println( header + ": " + request.lookupHeader( header ) );
  }
}
