import ServerExceptions.*;
import java.io.IOException;
import java.io.File;

public class ResponseFactory {
  public static Response getResponse( Request request, Resource resource, 
      ServerException exception ) {
    
    Response response = null;
    
    if( exception instanceof BadRequestException ) {
      response = new BadRequestResponse( resource );
    } else if( exception instanceof UnauthorizedException) {
    } else if( exception instanceof ForbiddenException ) {
    } else if( exception instanceof NotFoundException ) {
      response = new NotFoundResponse( resource );
    } else if( exception instanceof InternalServerErrorException ) {
    } 
    
    return response;
  }
  
  public static Response getResponse( Request request, Resource resource ) 
      throws IOException {
    Response response = null;
    String requestMethod = request.getVerb();
    
    if( requestMethod.equals("GET") || requestMethod.equals("HEAD") ) {
      response = new OKResponse( resource );
      response.setRequestMethod(requestMethod );
    }
    
    return response;
  }
  
}
