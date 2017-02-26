package configuration;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MimeTypes extends ConfigurationReader {
  private HashMap<String, String> types;

  public MimeTypes( String fileName ) throws FileNotFoundException {
    super( fileName );
    types = new HashMap<String, String>();
  }

  public void load() throws IOException {
    String line = null;

    while( hasMoreLines() ) {
      line = nextLine();
      
      if ( line.startsWith( "#" ) || line.isEmpty() ) {
        continue;
      }
      
      parse( line );
    }
  }

  public void parse( String line ) {
    StringTokenizer tokens = new StringTokenizer( line );
    String mimeType, extension;

    mimeType = tokens.nextToken();

    while ( tokens.hasMoreTokens() ) {
      extension = tokens.nextToken();
      types.put( extension, mimeType );
    }
  }

  public String lookup( String extension ) {
    String mimeType = types.get( extension );
    
    if( mimeType == null ) {
      mimeType = "text/text";
    }
    
    return mimeType;
  }
}
