import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Htpassword extends ConfigurationReader {
  private HashMap<String, String> users;


  public Htpassword (String fileName ) throws FileNotFoundException {
    super( fileName );
    users = new HashMap<String,String>();

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

  public void parse( String line ) {
    StringTokenizer tokens = new StringTokenizer ( line, ":" );
    String username, password;

    username = tokens.nextToken();
    password = tokens.nextToken();

    users.put( username, password );

  }

  public boolean isAuthorized( String username, String password ) {
    return ( users.containsValue( password ) && users.containsKey( username ) );
  }

  public void printHashMap() {
    for ( String key : users.keySet() ) {
      String value = users.get(key).toString();
      System.out.println( "username: " + key + " " + "password: " + value);
    }
  }


}