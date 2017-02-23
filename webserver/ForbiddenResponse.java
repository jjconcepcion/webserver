import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class ForbiddenResponse extends Response {
  public ForbiddenResponse( Resource resource ) {
    super( resource );
    this.code = 403;
    this.reasonPhrase = "ForbiddenResponse";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    String body = "<html><head><title>403 Forbidden</title></head><body>" 
      + "<h1>403 - Forbidden</h1></body></html>";
    
    this.sendCommonPreamble( out );
    this.sendSetHeaders( out );
    this.sendHeaderLine( out, "Content-Type", "text/html" );
    this.sendHeaderLine( out, "Content-Length", 
      String.valueOf( body.length() ) 
    );
   
    out.write(this.CRLF);
    out.flush();
    
    out.write( body );
    out.flush();
  }
  
}
