package responses;

import date.*;
import resource.*;
import java.time.LocalDateTime;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Response {
  protected static final String HTTP_VERSION = "HTTP/1.1";
  protected static final String SERVER_NAME = "CSC667-WebServer-TeamCN";
  protected static final String CRLF = "\r\n";
  protected int code;
  protected String reasonPhrase;
  protected Resource resource;
  protected byte[] body;
  protected String requestVerb;
  protected String setHeaders = "";
  
  public Response( Resource resource ) {
    this.resource = resource;
  }
  
  public Response() {
  }
  
  public abstract void send( OutputStream outputStream ) throws IOException;
  
  protected void sendCommonPreamble( BufferedWriter out ) throws IOException {
    FormattedDate date = new FormattedDate( LocalDateTime.now() );
    
    sendStatusLine( out );
    sendHeaderLine( out, "Server", SERVER_NAME );
    sendHeaderLine( out, "Date", date.toString() ); 
  }
  
  protected void sendStatusLine( BufferedWriter out ) throws IOException {
    String statusLine = HTTP_VERSION + " " + code + " " + reasonPhrase + CRLF;
    
    out.write( statusLine );
    out.flush();
  }
  
  protected void sendHeaderLine( 
      BufferedWriter out, String field, String value ) throws IOException {
      
    String headerLine = field + ": " + value + CRLF;
    
    out.write( headerLine );
    out.flush();
  }
  
  protected void setBodyDataFrom( String filePath ) throws IOException {
    body = Files.readAllBytes( Paths.get(filePath) );
  }
  
  public void setRequestMethod( String verb ) {
    requestVerb = verb;
  }
  
  public void setHeaderLine( String field, String value ) {
    setHeaders += field + ": " + value + CRLF;
  }
  
  protected void sendSetHeaders( BufferedWriter out ) throws IOException {
    if( setHeaders != null ) {
      out.write( setHeaders );
      out.flush();
    }
  }
}
