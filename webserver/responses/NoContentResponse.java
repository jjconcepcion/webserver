package responses;

import resource.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class NoContentResponse extends Response {
  public NoContentResponse( Resource resource ) {
    super( resource );
    this.code = 204;
    this.reasonPhrase = "No Content";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    this.sendCommonPreamble( out );
    out.write( this.CRLF );
    out.flush();
  }
}
