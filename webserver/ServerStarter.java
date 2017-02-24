import java.io.IOException;

public class ServerStarter {
  public static void main(String[] args) {
    try {
      Server server = new Server();
      server.start();
    } catch( IOException exception ) {
    }
  }
}
