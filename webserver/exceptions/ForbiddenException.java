package exceptions;

public class ForbiddenException extends ServerException {
  public ForbiddenException() {
    super(403, "Forbidden");
  }
}
