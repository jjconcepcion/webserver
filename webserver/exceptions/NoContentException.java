package exceptions;

public class NoContentException extends ServerException {
  public NoContentException() {
    super(204, "No Content");
  }
}
