package responses;

import resource.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class CreatedResponse extends Response {
  public CreatedResponse( Resource resource ) {
    super( resource );
    this.code = 201;
    this.reasonPhrase = "Created";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    this.sendCommonPreamble( out );
    this.sendSetHeaders( out );
    this.sendHeaderLine( out, "Content-Type", this.resource.getMimeType() );
    
    out.write(this.CRLF);
    out.flush();
  }
}
