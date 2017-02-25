import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class NotModifiedResponse extends Response {
  public NotModifiedResponse( Resource resource ) {
    super( resource );
    this.code = 304;
    this.reasonPhrase = "Not Modified";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    this.sendCommonPreamble( out );
    this.sendSetHeaders( out );
   
    out.write( this.CRLF );
    out.flush();
  }
}
