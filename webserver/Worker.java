import java.lang.Thread;
import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import ServerExceptions.*;

public class Worker extends Thread {
  private Socket client;
  private MimeTypes mimes;
  private HttpdConf config;
  private Request request;
  //private Resource resource;
  //private ResponseFactory responseFactory;
  
  public Worker( Socket socket, HttpdConf config, MimeTypes mimes ) {
    this.client = socket;
    this.config = config;
    this.mimes = mimes;
  }
  
  public void run() {
    System.out.println("WORKER:run()");
    try {
      parseRequest( client.getInputStream() );
      
      System.out.println(request.getVerb());
      client.close();
    } catch (IOException | ServerException ex ) {
        
    }
    
  }
  
  public void parseRequest( InputStream inputStream ) throws BadRequestException, IOException {
    request = new Request( inputStream );
    request.parse();
  }
}
