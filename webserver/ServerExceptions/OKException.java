package ServerExceptions;

public class OKException extends ServerException {
  public OKException() {
    super(200, "OK");
  }
}
