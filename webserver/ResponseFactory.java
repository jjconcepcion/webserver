import ServerExceptions.*;
import java.io.IOException;
import java.io.File;

public class ResponseFactory {
  public static Response getResponse( Request request, Resource resource, 
      ServerException exception ) {
    
    Response response = null;
    
    if( exception instanceof BadRequestException ) {
      //response = new BadRequestException( resource );
    } else if( exception instanceof CreatedException ) {
      //response = new CreatedResponse( resource );
    } else if( exception instanceof ForbiddenException ) {
      //response = new ForbiddenResponse( resource );
    } else if( exception instanceof InternalServerErrorException ) {
      //response = new InternalServerErrorResponse( resource );
    } else if( exception instanceof NoContentException ) {
      //response = new NoContentResponse( resource );
    } else if( exception instanceof NotFoundException ) {
      //response = new NotFoundResponse( resource );
    } else if( exception instanceof NotModifiedException ) {
      //response = new NotModifiedResponse( resource );
    } else if( exception instanceof OKException ) {
      response = new OKResponse( resource );
    } else if( exception instanceof UnauthorizedException) {
      //response = new UnauthorizedResponse( resource );
    } 
    
    return response;
  }
  
  public static Response getResponse( Request request, Resource resource ) 
      throws IOException {
    Response response = null;
    String requestMethod = request.getVerb();
    File file;
    
    requestMethod = request.getVerb();
    file = resource.getFile();
    //file = new File( resource.getAbsolutePath() );
    
    System.out.println("FACTORY");
    System.out.println(file);
    
    if(requestMethod.equals("GET")) {
      response = new OKResponse( resource );
      response.setRequestMethod(requestMethod );
      response.setBodyDataFrom( file );
    }
    
    return response;
  }
  
}
