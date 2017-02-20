import java.io.*;

public class ResourceTest {
  public static void main(String[] args) {
    String uri1 = "/ab/index.html";
    String uri2 = "/cgi-bin/perl_env";
    String uri3 = "/ab/";
    String uri4 = "/~traciely/";
    String uri5 = "/";

    System.out.println( "uri1: " + uri1 );
    System.out.println( "uri2: " + uri2 );
    System.out.println( "uri3: " + uri3 );
    System.out.println( "uri4: " + uri4 );
    System.out.println( "uri5: " + uri5 );
    System.out.println();
    

    try {
      HttpdConf conf = new HttpdConf("conf/httpd.conf");
      MimeTypes mimetype = new MimeTypes("conf/mime.types");

      try {
        conf.load();
        mimetype.load();

      } catch (IOException e ) {}

      Resource resource1 = new Resource( uri1, conf, mimetype );
      Resource resource2 = new Resource( uri2, conf, mimetype );
      Resource resource3 = new Resource( uri3, conf, mimetype );
      Resource resource4 = new Resource( uri4, conf, mimetype );
      Resource resource5 = new Resource( uri5, conf, mimetype );

      // Test case for Alias
      System.out.println( "uri1: " + uri1 );
      System.out.println( "First Segment: " + resource1.getFirstSegment() );
      System.out.println( "Last Segment: " + resource1.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource1.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource1.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource1.getAbsolutePath() );

      System.out.println();

      System.out.println( "uri2: " + uri2 );

      System.out.println( "First Segment: " + resource2.getFirstSegment() );
      System.out.println( "Last Segment: " + resource2.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource2.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource2.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource2.getAbsolutePath() );

      System.out.println();

      // Test case for Alias with no file

      System.out.println( "uri3: " + uri3 );
      System.out.println( "First Segment: " + resource3.getFirstSegment() );
      System.out.println( "Last Segment: " + resource3.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource3.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource3.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource3.getAbsolutePath() );

      System.out.println();

      // Test case for Alias (uri4) with no file 
      System.out.println( "uri4: " + uri4 );
      System.out.println( "First Segment: " + resource4.getFirstSegment() );
      System.out.println( "Last Segment: " + resource4.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource4.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource4.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource4.getAbsolutePath() );

      System.out.println();

      // Test case for Alias (uri5) with no file and no directory name 
      System.out.println( "uri5: " + uri5 );
      System.out.println( "First Segment: " + resource5.getFirstSegment() );
      System.out.println( "Last Segment: " + resource5.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource5.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource5.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource5.getAbsolutePath() );
    } catch ( FileNotFoundException e ) {}

    

  }
}