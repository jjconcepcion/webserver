package ServerExceptions;

public class CreatedException extends ServerException {
  public CreatedException() {
    super(201, "Created");
  }
}
