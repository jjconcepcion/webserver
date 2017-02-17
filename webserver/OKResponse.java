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
  
  public OKResponse() {
    this.code = 200;
    this.reasonPhrase = "OK";
  }
  
  public void send( OutputStream outputStream ) throws IOException {
    BufferedWriter out = new BufferedWriter( 
      new OutputStreamWriter( outputStream )
    );
    
    // get get from resource
    String type = "text/html";
    int byteSize = 1024;
    
    this.sendCommonPreamble( out );
    this.sendHeaderLine( out, "Content-Type", type );
    this.sendHeaderLine( out, "Content-Length", String.valueOf(byteSize) );
   
    //this.sendHeaderLine( out, "Connection", "close" );
    out.write(this.CRLF);
    out.flush();
    
    //if HEAD work is done
    //if GET send body
    //if POST ?
  }
  
  public static void main(String[] args ) {
    Response response = new OKResponse();
    
    try {
      response.send(System.out);
    } catch (IOException ex) {
    }
  }
  
}
