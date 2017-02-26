package exceptions;

public class NotModifiedException extends ServerException {
  public NotModifiedException() {
    super(304, "Not Modified");
  }
}
