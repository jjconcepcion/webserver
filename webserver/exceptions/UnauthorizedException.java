package exceptions;

public class UnauthorizedException extends ServerException {
  public UnauthorizedException() {
    super(401, "Unathorized");
  }
}
