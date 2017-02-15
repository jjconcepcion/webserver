import java.io.*;

public class MimeTypesTest {
  public static void main( String[] args) throws FileNotFoundException {

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

    System.out.println( ">>>Printing out all MIME types and Extensions<<<" );
    configuration.printHashMap();

    System.out.println( "Testing lookup..." );
    System.out.println( "Looking up extension 'jpeg' " );
    System.out.println( configuration.lookup( "jpeg" ) ) ;


  }
} 