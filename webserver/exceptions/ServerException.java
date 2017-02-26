package exceptions;

import java.lang.Exception;

public class ServerException extends Exception {
  int statusCode;
  
  public ServerException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }
  
  public int getStatusCode() {
    return statusCode;
  }

}


