import java.time.LocalDateTime;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Response {
  protected static final String HTTP_VERSION = "HTTP/1.1";
  protected static final String SERVER_NAME = "CSC667-WebServer-TeamCN";
  protected static final String CRLF = "\r\n";
  protected int code;
  protected String reasonPhrase;
  protected Resource resource;
  
  public Response( Resource resource ) {
    this.resource = resource;
  }
  
  public Response() {
  }
  
  abstract void send( OutputStream outputStream ) throws IOException;
  
  protected void sendCommonPreamble( BufferedWriter out ) throws IOException {
    FormattedDate date = new FormattedDate( LocalDateTime.now() );
    
    sendStatusLine( out );
    sendHeaderLine( out, "Server", "TeamCN");
    sendHeaderLine( out, "Date", date.toString() ); 
  }
  
  protected void sendStatusLine( BufferedWriter out ) throws IOException {
    String statusLine = HTTP_VERSION + " " + code + " " + reasonPhrase + CRLF;
    
    out.write(statusLine);
    out.flush();
  }
  
  protected void sendHeaderLine( 
      BufferedWriter out, String field, String value ) throws IOException {
      
    String headerLine = field + ": " + value + CRLF;
    
    out.write(headerLine);
    out.flush();
  }
  
}