import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Htaccess extends ConfigurationReader {
  private HashMap<String,String> directives;
  private LinkedList<String> users;
  private Htpassword userFile;
  private boolean validUser; // allow all valid users
  private boolean user; // allow listed users 

  public Htaccess (String fileName ) 
      throws FileNotFoundException, IOException {
    super( fileName );
    directives = new HashMap<String,String>();
    users = new LinkedList<String>();
    load();
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

  public void parse( String line ) 
      throws FileNotFoundException, IOException {
    StringTokenizer tokens = new StringTokenizer( line );
    String directive;

    directive = tokens.nextToken();
    
    switch( directive ) {
      case "AuthUserFile": 
      
        String authUserFilePath = tokens.nextToken().replace( "\"", "" ); 
        directives.put( directive, authUserFilePath );

        userFile = new Htpassword( authUserFilePath );
        userFile.load();

        break;
      case "AuthType":
        String authType = tokens.nextToken();
        directives.put( directive, authType );
        
        break;
      case "AuthName":
        String authName;
        authName = line.replaceFirst( "AuthName ", "" ).replace( "\"" , "");
        directives.put( directive, authName );
        
        break;
      case "Require":
        String require = tokens.nextToken();
        directives.put( directive, require );
        validUser = require.equals("valid-user");
        user = require.equals("user");
        
        if( user ) {
          while( tokens.hasMoreTokens() ) {
            users.add( tokens.nextToken() );
          }
        }
        break;
    }
  }

  public boolean isAuthorized( String  authInfo) {
    return userFile.isAuthorized( authInfo );
  }

  public String getAuthUserFilePath() {
    return directives.get("AuthUserFile");

  }
  public String getAuthType() {
    return directives.get("AuthType");
  }

  public String getAuthName() {
    return directives.get("AuthName");
  }
  public String getRequire() {
    return directives.get("Require");
  }
}
