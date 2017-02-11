import java.util.StringTokenizer;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Htaccess extends ConfigurationReader {
  private Htpassword userFile;
  private String authType;
  private String authName;
  private String require;
  private String authUserFilePath;

  public Htaccess (String fileName ) throws FileNotFoundException {
    super( fileName );
  }

  public void load() throws IOException {
    String line = null;

    while( hasMoreLines() ) {
      line = nextLine();
      if ( line.startsWith( "#" ) || line.isEmpty() ) {
        continue;
      }
      parse(line);
    }
  }

  public boolean isAuthorized( String username, String password ) {
    return true;
  }

  public void parse( String line ) throws FileNotFoundException {
    StringTokenizer tokens = new StringTokenizer( line );
    String directive;

    directive = tokens.nextToken();
    
    switch( directive ) {
      case "AuthUserFile": 
        authUserFilePath = tokens.nextToken().replace( "\"", "" ); 
        userFile = new Htpassword( authUserFilePath );
        break;
      case "AuthType":
        authType = tokens.nextToken();
        break;
      case "AuthName":
        authName = tokens.nextToken().replace( "\"", "" );
        while (tokens.hasMoreTokens()) {
          authName += " ";
          authName += tokens.nextToken().replace( "\"", "" );
        }
        break;
      case "Require":
        require = tokens.nextToken();
        break;
    }
  }
  public String getAuthUserFilePath() {
    return authUserFilePath;

  }
  public String getAuthType() {
    return authType;
  }

  public String getAuthName() {
    return authName;
  }
  public String getRequire() {
    return require;
  }
}