import ServerExceptions.*;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    Path filePath;
    String requestMethod; 
    FormattedDate modifiedDate;
    
    filePath = Paths.get( resource.getAbsolutePath() );
    requestMethod = request.getVerb();
    
    modifiedDate = new FormattedDate(
      Files.getLastModifiedTime( filePath ).toMillis()
    );
    
    if( requestMethod.equals( "GET" ) || requestMethod.equals( "HEAD" ) ) {
      response = new OKResponse( resource );
      response.setRequestMethod(requestMethod );
      response.setHeaderLine( "Last-Modified", modifiedDate.toString() );
      
    } else if( requestMethod.equals( "PUT" ) ) {
      File putFile = new File( filePath.toString() );
      FileOutputStream fileOut = new FileOutputStream( putFile, false );
  
      fileOut.write( request.getBody() );
      fileOut.close();
      
    }
    
    return response;
  }
  
}
