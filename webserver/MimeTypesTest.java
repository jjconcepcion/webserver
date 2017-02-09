import java.io.*;
// import java.util.StringTokenizer;
// import java.util.HashMap;

public class MimeTypesTest {
  public static void main( String[] args) throws FileNotFoundException {

    // String line = "application/andrew-inset   ez pz px xp";
    // String test, mimeType, extension;
    
    // HashMap<String, String> types;
    // types = new HashMap<String, String>();

    // StringTokenizer tokens = new StringTokenizer( line );

    // mimeType = tokens.nextToken();

    // while (tokens.hasMoreTokens()) {
    //   extension = tokens.nextToken();
    //   types.put( extension, mimeType );
    // }

    // for (String pairs : types.keySet()) {
    //   String key = pairs.toString();
    //   String value = types.get(pairs).toString();
    //   System.out.println( "key: " + key + " " + "value: " + value ); 
    // }

    MimeTypes configuration = null;

    try {
      configuration = new MimeTypes( args[0] );
    } catch ( ArrayIndexOutOfBoundsException | FileNotFoundException e) {
      System.out.println( e + "\nPass filename of mime config file as argument");
      System.exit(0);
    }
    
    try {
      configuration.load();
      System.out.println( "\n Loaded mime config file successfully ");
    } catch ( IOException e ) {
      System.out.println( "Failed to load config file: " + e );
      System.exit(0);
    }

    System.out.println( ">>>Printing out all MIME types and Extensions<<<");
    configuration.printHashMap();
  }
} 