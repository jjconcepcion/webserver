import java.net.*;
import java.io.*;

public class Server {
  public static final int DEFAULT_PORT = 3100;

  public static void main( String[] args ) throws IOException {

    ServerSocket socket = new ServerSocket( DEFAULT_PORT );
    Socket client = null;

    while( true ) {
      client = socket.accept();
      client.close();
    }
  }
}