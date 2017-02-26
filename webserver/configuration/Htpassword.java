package configuration;

import java.util.HashMap;
import java.util.Base64;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import java.io.IOException;

public class Htpassword extends ConfigurationReader {
  private HashMap<String, String> passwords;

  public Htpassword( String filename ) throws IOException {
    super( filename );
    this.passwords = new HashMap<String, String>();
    this.load();
  }
  
  public void load() throws IOException {
    String line = null;
    
    while( hasMoreLines() ) {
      line = nextLine();
      if( line.startsWith( "#" ) || line.isEmpty() ) {
        continue;
      }
      parseLine( line );
    }
  }
  
  protected void parseLine( String line ) {
    String[] tokens = line.split( ":" );

    if( tokens.length == 2 ) {
      passwords.put( tokens[ 0 ], tokens[ 1 ].replace( "{SHA}", "" ).trim() );
    }
  }

  public boolean isAuthorized( String authInfo ) {
    String credentials = new String(
      Base64.getDecoder().decode( authInfo ),
      Charset.forName( "UTF-8" )
    );

    String[] tokens = credentials.split( ":" );

    return verifyPassword( tokens[0], tokens[1] );
  }
  
  public static String decode( String authInfo ) {
    return new String(
      Base64.getDecoder().decode( authInfo ), Charset.forName( "UTF-8" )
    );
  }

  private boolean verifyPassword( String username, String password ) {
    String storedPassword = passwords.get( username );
    
    return encryptClearPassword( password ).equals( storedPassword );
  }

  private String encryptClearPassword( String password ) {
    try {
      MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
      byte[] result = mDigest.digest( password.getBytes() );

     return Base64.getEncoder().encodeToString( result );
    } catch( Exception e ) {
      return "";
    }
  }
}
