import ServerExceptions.*;
import java.io.IOException;
import java.io.File;

public class ResponseFactory {
  public static Response getResponse( Request request, Resource resource, 
      ServerException exception ) {
    
    Response response = null;
    
    if( exception instanceof BadRequestException ) {
    } else if( exception instanceof UnauthorizedException) {
    } else if( exception instanceof ForbiddenException ) {
    } else if( exception instanceof NotFoundException ) {
    } else if( exception instanceof InternalServerErrorException ) {
    } 
    
    return response;
  }
  
  public static Response getResponse( Request request, Resource resource ) 
      throws IOException {
    Response response = null;
    String requestMethod;
    
    requestMethod = request.getVerb();
    
    if(requestMethod.equals("GET")) {
      response = new OKResponse( resource );
      response.setRequestMethod(requestMethod );
    }
    
    return response;
  }
  
}
