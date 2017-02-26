package exceptions;

public class InternalServerErrorException extends ServerException {
  public InternalServerErrorException() {
    super(500, "Internal Server Error");
  }
}
