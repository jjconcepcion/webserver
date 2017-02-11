import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

public class Request {
  private HashMap<String,String> headers;
  private String uri;
  private String verb;
  private String httpVersion;
  private InputStream inputStream;
  private byte[] body;
  
  public Request( InputStream clientInputStream ) {
    headers = new HashMap<String,String>();
    inputStream = clientInputStream;
  }

  public void parse() throws IOException {
    String line;
    
    BufferedReader reader = new BufferedReader(
      new InputStreamReader( inputStream )
    );
    
    line = reader.readLine();
    parseStartLine( line );
    
    while( true ) {
      line = reader.readLine();
      
      if( line.isEmpty() ) {
        break;
      }
      
      parseHeader( line );
    }
    
    if( hasBody() ) {
      parseBody();
    }
  }
  
  private void parseStartLine( String line ) {
    StringTokenizer tokens = new StringTokenizer( line );
    
    verb = tokens.nextToken();
    uri = tokens.nextToken();
    httpVersion = tokens.nextToken();
  }
  
  private void parseHeader( String line ) {
    StringTokenizer tokens = new StringTokenizer( line );
    String header, value;
    
    header = tokens.nextToken().replace( ":", "");
    value = tokens.nextToken();
    
    headers.put( header, value );
  }
  
  private void parseBody() throws IOException {
    int byteSize = Integer.parseInt( lookupHeader( "Content-Length") );
    body = new byte[byteSize];
    
    inputStream.read( body, 0, byteSize );
  }
  
  public String getUri() {
    return uri;
  }
  
  public String getVerb() {
    return verb;
  }
  
  public String getHttpVersion() {
    return httpVersion;
  }
  
  public String lookupHeader( String header ) {
    return headers.get( header );
  }
  
  public Boolean hasBody() {
    return lookupHeader( "Content-Length" ) != null;
  }
  
  public byte[] getBody() {
    return body;
  }
}
