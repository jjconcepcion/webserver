import java.io.*;

public class HtpasswordTest {
  public static void main( String[] args) throws FileNotFoundException {
    Htpassword htpassword = null;

    try {
      htpassword = new Htpassword( args[0] );
    } catch ( ArrayIndexOutOfBoundsException | FileNotFoundException e) {
      System.out.println( e + "\nPass filename of htpassword file as argument");
      System.exit(0);
    }
    
    try {
      htpassword.load();
      System.out.println( "\n Loaded htpassword file successfully ");
    } catch ( IOException e ) {
      System.out.println( "Failed to load htpassword file: " + e );
      System.exit(0);
    }

    System.out.println( ">>> Printing out all Users and Passwords <<<" );
    htpassword.printHashMap();

    System.out.println( ">>> Testing isAuthorized <<<" );
    System.out.println( htpassword.isAuthorized( "jrob" , 
      "{SHA}cRDtpNCeBiql5KOQsKVyrA0sAiA=" ) );
  }
}