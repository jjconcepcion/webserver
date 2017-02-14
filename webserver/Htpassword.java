import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Htpassword extends ConfigurationReader {
  private HashMap<String, String> users;
  private String delimiter = ":{SHA}";


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
    String [] lineParts = line.split( ":" );
    StringTokenizer tokens = new StringTokenizer ( line, ":" );
    String username, password;

    username = lineParts[0];
    password = lineParts[1];

    users.put( username, password );

  }

  public boolean isAuthorized( String username, String password ) {
    return ( users.get( username ).equals( password ) );
  }

  public void getUserNames() {
    System.out.println( users.keySet() );
  }

  public void getPasswords() {
    System.out.println( users.values()) ;
  }


  public void printHashMap() {
    for ( String key : users.keySet() ) {
      String value = users.get(key).toString();
      System.out.println( "username: " + key + " " + "password: " + value);
    }
  }


}