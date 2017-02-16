import java.time.LocalDateTime;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Response {
  protected static String httpVersion = "HTTP/1.1";
  protected int code;
  protected String reasonPhrase;
  /*
  protected Resource resource;
  
  public Response( Resource resource ) {
  }
  */
  public Response() {
    code = 200;
    reasonPhrase = "OK";
  }
  
  public void send( OutputStream outputStream ) {
    PrintWriter out = new PrintWriter( outputStream, true);
    
    out.write( getStatusLine() );
    out.write( "Date: " + getDateNowFormatted() );
    out.write("\r\n");
  }
  
  protected String getStatusLine() {
    return  httpVersion + " " + code + " " + reasonPhrase + "\r\n";
  }
  
  protected String getDateNowFormatted() {
    FormattedDate date = new FormattedDate( LocalDateTime.now() );
    
    return date.toString();
  }
  
}
