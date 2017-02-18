import java.io.*;

public class ResourceTest {
  public static void main(String[] args) {
    String uri1 = "/ab/index.html";
    String uri2 = "/cgi-bin/index.html";
    System.out.println( "uri1: " + uri1 );
    System.out.println( "uri2: " + uri2 + "\n" );

    try {
      HttpdConf conf = new HttpdConf("conf/httpd.conf");

      try {
        conf.load();
      } catch (IOException e ) {}

      Resource resource1 = new Resource( uri1, conf );
      Resource resource2 = new Resource( uri2, conf );

      resource1.parseUri();
      resource2.parseUri();
      resource1.absolutePath();
      System.out.println( "Path: " + resource1.getPath() );
      System.out.println( "First Segment: " + resource1.getFirstSegment() );
      System.out.println( "Last Segment: " + resource1.getLastSegment() );
      System.out.println( "Testing isFile(): " + resource1.isFile() );
      System.out.println( "Testing isDirectory(): " + resource1.isDirectory() );
      System.out.println( "Testing isAlias(): " + resource1.isAlias() );
      System.out.println( "Testing isScriptedAlias(): " + resource1.isScriptAlias() );
      System.out.println( "Testing absolutePath(): " + resource1.absolutePath() );
    } catch ( FileNotFoundException e ) {}

    

  }
}