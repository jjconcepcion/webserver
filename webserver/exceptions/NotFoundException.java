package exceptions;

public class NotFoundException extends ServerException {
  public NotFoundException() {
    super(404, "Not Found");
  }
}
