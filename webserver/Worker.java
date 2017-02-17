import java.lang.Thread;
import java.net.Socket;

public class Worker extends Thread {
  private Socket client;
  private MimeTypes mimes;
  private HttpdConf config;
  private Request request;
  //private Resource resource;
  //private ResponseFactory responseFactory;
  
  public Worker( Socket socket, HttpdConf config, MimeTypes mimes ) {
    client = socket;
    this.config = config;
    this.mimes = mimes;
  }
  
  public void run() {
    /*
    - Request
    - Resource
    - Access Check
    - Response
    */
  }
}
