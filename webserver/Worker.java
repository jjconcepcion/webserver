import java.lang.Thread;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import ServerExceptions.*;

public class Worker extends Thread {
  private Socket client;
  private MimeTypes mimes;
  private HttpdConf config;
  private Request request;
  private Resource resource;
  private Response response;
  
  public Worker( Socket socket, HttpdConf config, MimeTypes mimes ) {
    this.client = socket;
    this.config = config;
    this.mimes = mimes;
  }
  
  public void run() {
    
    try {
      parseRequest( client.getInputStream() );
      
      resource = new Resource( request.getUri(), config, mimes );
      
      System.out.println("WORKER:run()");
      System.out.println(resource.getAbsolutePath());
      
      response = ResponseFactory.getResponse( request, resource );
      
    } catch( ServerException exception ) {
      response = ResponseFactory.getResponse( request, resource, exception );
    } catch ( IOException exception ) {
      InternalServerErrorException error = new InternalServerErrorException();
      response = ResponseFactory.getResponse( request, resource, error );
    }
    
    try {
      response.send( client.getOutputStream() );
      client.close();
    } catch( IOException exception ) {
    }
  }
  
  public void parseRequest( InputStream inputStream ) 
      throws  BadRequestException, IOException {
    request = new Request( inputStream );
    request.parse();
  }
}
