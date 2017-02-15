import java.io.*;

public class Resource {

  private String uri;
  private HttpdConf config;

  public Resource( String uri, HttpdConf config ) {

    uri = uri;
    config = config;
    
  }

  public String absolutePath() {
    return path;
  }

  public boolean isScript() {
    return false;
  }

  public boolean isProtected() {
    return false;
  }
}