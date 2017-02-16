public class Response {
  protected static String httpVersion = "HTTP/1.1";
  protected int code;
  protected String reasonPhrase;
  /*
  protected Resource resource;
  
  public Response( Resource resource ) {
  }
  */
  public Response() {
    code = 200;
    reasonPhrase = "OK";
  }
  
  public void send() {
  }
  
  protected String getStatusLine() {
    return  httpVersion + " " + code + " " + reasonPhrase + "\r\n";
  }
}
