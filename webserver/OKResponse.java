import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class OKResponse extends Response {
  public OKResponse( Resource resource ) {
    super( resource );
    this.code = 200;
    this.reasonPhrase = "OK";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    this.sendCommonPreamble( out );
    this.sendSetHeaders( out );
    
    // Determine source of response body
    if( resource.isScript() ) {
      body = "REDIRECTED SCRIPT OUTPUT".getBytes();
    } else {
      this.setBodyDataFrom( this.resource.absolutePath() );
      this.sendHeaderLine( out, "Content-Type", this.resource.getMimeType() );
      this.sendHeaderLine( 
        out, "Content-Length", String.valueOf( this.body.length ) 
      );
   
    }
    
    out.write(this.CRLF);
    out.flush();
    
    if( !this.requestVerb.equals("HEAD") ) {
      outputStream.write( body );
      outputStream.flush();
    }
  
    //if HEAD work is done
    
    //if POST ?
  }
  
}
