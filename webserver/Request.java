import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

public class Request {
  private HashMap<String,String> headers;
  private String uri;
  private String verb;
  private String httpVersion;
  private InputStream inputStream;
  //private Type body;
  
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
    System.out.println( line );
    parseStartLine( line );
    
    while( true ) {
      line = reader.readLine();
      
      if( line.isEmpty() ) {
        break;
      }
      
      parseHeader( line );
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
  
}
