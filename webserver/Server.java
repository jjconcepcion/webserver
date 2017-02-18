import java.net.*;
import java.io.*;
import java.lang.Thread;

public class Server {
  private static final String HTTPD_CONF_PATH = "conf/httpd.conf";
  private static final String MIME_TYPES_PATH = "conf/mime.types";
  private static int listenPort;
  private static HttpdConf configuration;
  private static MimeTypes mimeTypes;
  
  public static void main( String[] args ) throws IOException {
    start();
  }
  
  public static void start() throws IOException {
    
    try {
      loadServerConfiguration();
    } catch( IOException exception ) {
      System.out.println( exception.getMessage() );
      System.exit(1);
    }
    
    ServerSocket socket = new ServerSocket( listenPort );
    Socket client = null;
    Thread worker = null;

    while( true ) {
      System.out.println("SERVER:start()");
      client = socket.accept();
      worker = new Worker( client, configuration, mimeTypes );
      worker.start();
      //client.close();
    }
  }
  
  public static void loadServerConfiguration() throws IOException {
    configuration = new HttpdConf( HTTPD_CONF_PATH );
    mimeTypes = new MimeTypes( MIME_TYPES_PATH );
  
    configuration.load();
    mimeTypes.load();
    listenPort = configuration.getListenPort();
  }
}
