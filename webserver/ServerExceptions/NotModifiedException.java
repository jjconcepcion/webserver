package ServerExceptions;

public class NotModifiedException extends ServerException {
  public NotModifiedException() {
    super(304, "Not Modified");
  }
}
