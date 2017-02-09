import java.io.*;

public class HttpdConfTest {
  public static void main(String[] args) throws FileNotFoundException {
    HttpdConf configuration = null;
    
    try {
      configuration = new HttpdConf( args[0] );
    } catch( ArrayIndexOutOfBoundsException | FileNotFoundException e ) {
      System.out.println( e + "\n>>>Pass filename of config file as argument" );
      System.exit(0);
    }
    
    try {
      configuration.load();
      System.out.println( "\n>>>Coniguration loaded without error" );
    } catch ( IOException e) {
      System.out.println("Failed to load configuration:" + e);
      System.exit(0);
    }
    
    System.out.println( "\n>>>Display server root path" );
    System.out.println( configuration.getServerRoot() );
    System.out.println( "\n>>>Display document root path" );
    System.out.println( configuration.getDocumentRoot() );
    System.out.println( "\n>>>Display listen port" );
    System.out.println( configuration.getListenPort() );
    System.out.println( "\n>>>Display log file path" );
    System.out.println( configuration.getLogFile() );
    String alias = "/ab/";
    System.out.println( "\n>>>Resolve alias " + "\"" + alias + "\"");
    System.out.println( configuration.lookupAlias( alias ) );
    String scriptAlias = "/cgi-bin/";
    System.out.println( "\n>>>Resolve script alias " + "\"" + scriptAlias + "\"");
    System.out.println( configuration.lookupScriptAlias( scriptAlias ) );
    
  }
}
