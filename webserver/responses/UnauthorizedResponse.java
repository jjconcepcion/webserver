package responses;

import resource.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class UnauthorizedResponse extends Response {
  public UnauthorizedResponse( Resource resource ) {
    super( resource );
    this.code = 401;
    this.reasonPhrase = "Unauthorized";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    String body = "<html><head><title>401 Unauthorized</title></head><body>" 
      + "<h1>401 - Unauthorized</></body></html>";
    
    this.sendCommonPreamble( out );
    this.sendSetHeaders( out );
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
