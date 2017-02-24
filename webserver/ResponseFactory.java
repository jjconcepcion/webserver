import ServerExceptions.*;
import java.time.LocalDateTime;
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
      response = new UnauthorizedResponse( resource );
    } else if( exception instanceof ForbiddenException ) {
      response = new ForbiddenResponse( resource );
    } else if( exception instanceof NotFoundException ) {
      response = new NotFoundResponse( resource );
    } else if( exception instanceof InternalServerErrorException ) {
    } 
    
    return response;
  }
  
  public static Response getResponse( Request request, Resource resource ) 
      throws IOException, NotFoundException {
    Response response = null;
    Path filePath;
    String requestMethod; 
    FormattedDate modifiedDate;
    
    filePath = Paths.get( resource.absolutePath() );
    requestMethod = request.getVerb();
    
    
    if( resource.isProtected() ) {
      Htaccess access = new Htaccess( resource.accessFilePath() );
      
      try {
        checkValidAccessFor( request, resource );
        
      } catch( ServerException exception ) {
        response = getResponse( request, resource, exception );
      
        if( exception instanceof UnauthorizedException ) {
          String field = "WWW-Authenticate";
          String value = access.getAuthType() + " realm=\"" +
                          access.getAuthName() +"\"";
                          
          response.setHeaderLine( field, value );
        }
       
        return response;
      }
    }
    

    if( !requestMethod.equals("PUT") && !Files.exists( filePath ) ) {
      throw new NotFoundException();
    }

    if( requestMethod.equals( "GET" ) || requestMethod.equals( "HEAD" ) ||
        requestMethod.equals( "POST" ) ) {
        
      if( requestMethod.equals( "POST") ) {
        writeToFile( filePath.toString(), request.getBody() );
      }
        
      modifiedDate = new FormattedDate(
        Files.getLastModifiedTime( filePath ).toMillis()
      ); 
      
      if( request.isConditional() &&
        request.modifiedDate().equals( modifiedDate.toString() )) {
        
        response = new NotModifiedResponse( resource );
        
      } else {
        response = new OKResponse( resource );
        
        response.setRequestMethod(requestMethod );
      }
      
      response.setHeaderLine( "Last-Modified", modifiedDate.toString() );
      response.setHeaderLine( "Cache-Control", "max-age=3600" );
      
      FormattedDate expiration = new FormattedDate(
        LocalDateTime.now().plusSeconds(3600)
      );
      
      response.setHeaderLine( "Expires", expiration.toString() );
      
    } else if( requestMethod.equals( "PUT" ) ) {
      writeToFile( filePath.toString(), request.getBody() );
      
      response = new CreatedResponse( resource );
      
      response.setHeaderLine( "Location", request.getUri() );
    } else if ( requestMethod.equals("DELETE") ) {
      Files.delete( filePath );
      
      response = new NoContentResponse( resource );
    }
    
    return response;
  }
  
  public static void writeToFile( String fileName, byte[] data ) 
      throws IOException {
    FileOutputStream out = new FileOutputStream( fileName, false );
    
    out.write( data );
    out.close();
  }
  
  public static void checkValidAccessFor( Request request, Resource resource )
      throws ServerException, IOException {
    Htaccess access = new Htaccess( resource.accessFilePath() );
      
    String credentials = request.lookupHeader( "Authorization" );
    
    if(credentials == null ) {
      throw new UnauthorizedException();
    }
    
    String authInfo = credentials.split("\\s")[1];
    
    if( !access.isAuthorized( authInfo ) ) {
      throw new UnauthorizedException();
    }
    
    if( !access.isValid( authInfo ) ) {
      throw new ForbiddenException();
    }
    
  }
}
