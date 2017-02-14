import java.io.*;

public class HtaccessTest {
  public static void main( String[] args) throws FileNotFoundException {
    Htaccess htaccess = null;

    try {
      htaccess = new Htaccess( args[0] );
    } catch ( ArrayIndexOutOfBoundsException | FileNotFoundException e) {
      System.out.println( e + "\nPass filename of htaccess file as argument");
      System.exit(0);
    }
    
    try {
      htaccess.load();
      System.out.println( "\n Loaded htaccess file successfully ");
    } catch ( IOException e ) {
      System.out.println( "Failed to load htaccess file: " + e );
      System.exit(0);
    }

    System.out.println( ">>> Printing out AuthUserFile Path <<<" );
    System.out.println( htaccess.getAuthUserFilePath() );
    System.out.println( ">>> Printing out AuthType <<<" );
    System.out.println( htaccess.getAuthType() );
    System.out.println( ">>> Printing out AuthName <<<" );
    System.out.println( htaccess.getAuthName() );
    System.out.println( ">>> Printing out Require <<<" );
    System.out.println( htaccess.getRequire() );
    System.out.println( ">>> Testing isAuthorized() <<< " );
    System.out.println( htaccess.isAuthorized( "jrob", "{SHA}cRDtpNCeBiql5KOQsKVyrA0sAiA=" ) );
  }
}