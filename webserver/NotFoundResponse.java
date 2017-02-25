import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class NotFoundResponse extends Response {
  public NotFoundResponse( Resource resource ) {
    super( resource );
    this.code = 404;
    this.reasonPhrase = "Not Found";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    String body = "<html><head><title>404 Not Found</title></head><body>" 
      + "<h1>404 - Page Not Found</></body></html>";
    
    this.sendCommonPreamble( out );
    this.sendHeaderLine( out, "Content-Type", "text/html" );
    this.sendHeaderLine( out, "Content-Length", 
      String.valueOf( body.length() ) 
    );
   
    out.write( this.CRLF );
    out.flush();
    
    out.write( body );
    out.flush();
  }
}
