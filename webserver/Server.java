
import worker.*;
import configuration.*;
import java.net.*;
import java.io.*;
import java.lang.Thread;

public class Server {
  private static final String HTTPD_CONF_PATH = "./conf/httpd.conf";
  private static final String MIME_TYPES_PATH = "./conf/mime.types";
  private static int listenPort;
  private static HttpdConf configuration;
  private static MimeTypes mimeTypes;
  
  public Server() throws IOException {
    configuration = new HttpdConf( HTTPD_CONF_PATH );
    mimeTypes = new MimeTypes( MIME_TYPES_PATH );
  
    configuration.load();
    mimeTypes.load();
  }
  
  public static void start() throws IOException {
    listenPort = configuration.getListenPort();
    ServerSocket socket = new ServerSocket( listenPort );
    Socket client = null;
    Thread worker = null;

    while( true ) {
      client = socket.accept();
      worker = new Worker( client, configuration, mimeTypes );
      worker.start();
    }
  }
}
