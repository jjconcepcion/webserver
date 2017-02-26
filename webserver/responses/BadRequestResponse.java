package responses;

import resource.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class BadRequestResponse extends Response {
  public BadRequestResponse( Resource resource ) {
    super( resource );
    this.code = 400;
    this.reasonPhrase = "Bad Request";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    String body = "<html><head><title>200 Bad Request</title></head><body>" 
      + "<h1>200 - Bad Request</h1></body></html>";
    
    this.sendCommonPreamble( out );
    this.sendHeaderLine( out, "Content-Type", "text/html" );
    this.sendHeaderLine( out, "Content-Length", 
      String.valueOf( body.length() ) 
    );
    this.sendHeaderLine( out, "Connection", "closed");
    
    out.write(this.CRLF);
    out.flush();
    
    out.write( body );
    out.flush();
  }
}
