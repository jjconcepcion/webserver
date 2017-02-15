import ServerExceptions.*;

public class ServerExceptionTest {
  public static void main( String[] args ) {
    try {
      test( new InternalServerErrorException() );
    } catch( ServerException exception ) {
      System.out.println( "Status: " + exception.getStatusCode() );
      System.out.println( "Message: " + exception.getMessage() );
    }
  }
  
  public static void test( ServerException exception ) throws ServerException {
    throw exception;
  }
}
