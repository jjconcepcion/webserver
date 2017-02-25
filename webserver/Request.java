import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.io.*;
import ServerExceptions.*;

public class Request {
  private HashMap<String,String> headers;
  private String uri;
  private String verb;
  private String httpVersion;
  private String queryString;
  private InputStream inputStream;
  private byte[] body;
  private static String[] validVerbs = {
    "GET", "HEAD", "POST", "PUT", "DELETE"
  };
  
  public Request( InputStream clientInputStream ) {
    headers = new HashMap<String,String>();
    inputStream = clientInputStream;
  }

  public void parse() throws IOException, BadRequestException {
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
  
  private void parseStartLine( String line ) throws BadRequestException {
    if( line == null ) {
      throw new BadRequestException();
    }
    
    String whitespaces = "\\s+";
    String[] tokens = line.split(whitespaces);
    
    if( tokens.length != 3 ) {
      throw new BadRequestException();
    }
    
    verb = tokens[0];
    if( !isValidVerb( verb ) ) {
      throw new BadRequestException();
    }

    uri = tokens[1];
    httpVersion = tokens[2];

    if( uri.contains("?") ) {
      extractQueryString(uri);
    } 
  }
  
  private void parseHeader( String line ) throws BadRequestException {
    String[] tokens = line.split( ":", 2 );
    String header, value;
    
    if( tokens.length < 2 ) {
      throw new BadRequestException();
    }
    
    header = tokens[0];
    value = tokens[1];
    value = value.trim();
    
    headers.put( header, value );
  }
  
  private void parseBody() throws IOException {
    int byteSize = Integer.parseInt( lookupHeader( "Content-Length") );
    body = new byte[byteSize];
    
    inputStream.read( body, 0, byteSize );
  }
  
  private boolean isValidVerb( String verb ) {
    return Arrays.asList( validVerbs ).contains( verb );
  }

  private void extractQueryString( String uri ) {
    String questionmark = "\\?";
    String[] tokens = uri.split(questionmark);
    this.uri = tokens[0];
    queryString = tokens[1];
  }

  public String getQueryString() {
    return queryString;
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
  
  public boolean hasBody() {
    return lookupHeader( "Content-Length" ) != null;
  }
  
  public byte[] getBody() {
    return body;
  }
  
  public boolean isConditional() {
    return headers.get( "If-Modified-Since" ) != null;
  }
  
  public String modifiedDate() {
    return headers.get( "If-Modified-Since" );
  }
}
