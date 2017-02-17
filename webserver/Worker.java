import java.lang.Thread;
import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import ServerExceptions.*;

public class Worker extends Thread {
  private Socket socket;
  private MimeTypes mimes;
  private HttpdConf config;
  private Request request;
  //private Resource resource;
  //private ResponseFactory responseFactory;
  
  public Worker( Socket socket, HttpdConf config, MimeTypes mimes ) {
    this.socket = socket;
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
    try {
      request = parseRequest( socket.getInputStream() );
    } catch( IOException | BadRequestException e ) {
      ;
    }
    
  }
  
  public Request parseRequest( InputStream inputStream ) throws BadRequestException, IOException {
    request = new Request( inputStream );
    request.parse();
   
    return request;
  }
}
