import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class InternalServerErrorResponse extends Response {
  public InternalServerErrorResponse( Resource resource ) {
    super( resource );
    this.code = 500;
    this.reasonPhrase = "Internal Server Error";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    String body = "<html><head><title>500 Internal Server Error</title></head><body>" 
      + "<h1>500 Internal Server Error</></body></html>";
    
    this.sendCommonPreamble( out );
    this.sendHeaderLine( out, "Content-Type", "text/html" );
    this.sendHeaderLine( out, "Content-Length", 
      String.valueOf( body.length() ) 
    );
    this.sendHeaderLine( out, "Connection", "closed" );
   
    out.write( this.CRLF );
    out.flush();
    
    out.write( body );
    out.flush();
  }
}
