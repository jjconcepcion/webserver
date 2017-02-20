import java.io.*;

public class ResourceTest {
  public static void main(String[] args) {
    String uri1 = "/ab/index.html";
    String uri2 = "/ab/";
    String uri3 = "/cgi-bin/perl_env";
    String uri4 = "/cgi-bin/";
    String uri5 = "/~traciely/";
    String uri6 = "/";

    System.out.println( "uri1: " + uri1 );
    System.out.println( "uri2: " + uri2 );
    System.out.println( "uri3: " + uri3 );
    System.out.println( "uri4: " + uri4 );
    System.out.println( "uri5: " + uri5 );
    System.out.println( "uri6: " + uri6 );

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
      Resource resource6 = new Resource( uri6, conf, mimetype );


      // Test case for Alias /ab/index.html
      System.out.println( "uri1: " + uri1 );
      System.out.println( "First Segment: " + resource1.getFirstSegment() );
      System.out.println( "Last Segment: " + resource1.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource1.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource1.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource1.getAbsolutePath() );
      System.out.println( "Testing isProtected(): " + resource1.isProtected() );    

      System.out.println();

      // Test case for Alias with no file /ab/
      System.out.println( "uri2: " + uri2 );
      System.out.println( "First Segment: " + resource2.getFirstSegment() );
      System.out.println( "Last Segment: " + resource2.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource2.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource2.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource2.getAbsolutePath() );
      System.out.println( "Testing isProtected(): " + resource2.isProtected() );    

      System.out.println();

      // Test case for Script Alias /cgi-bin/perl_env
      System.out.println( "uri3: " + uri3 );
      System.out.println( "First Segment: " + resource3.getFirstSegment() );
      System.out.println( "Last Segment: " + resource3.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource3.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource3.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource3.getAbsolutePath() );
      System.out.println( "Testing isProtected(): " + resource3.isProtected() );    

      System.out.println();

      // Test case for Script Alias with no file /cgi-bin/
      System.out.println( "uri4: " + uri4 );
      System.out.println( "First Segment: " + resource4.getFirstSegment() );
      System.out.println( "Last Segment: " + resource4.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource4.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource4.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource4.getAbsolutePath() );
      System.out.println( "Testing isProtected(): " + resource4.isProtected() );    

      System.out.println();

      // Test case for Alias /~traciely/
      System.out.println( "uri5: " + uri5 );
      System.out.println( "First Segment: " + resource5.getFirstSegment() );
      System.out.println( "Last Segment: " + resource5.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource5.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource5.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource5.getAbsolutePath() );
      System.out.println( "Testing isProtected(): " + resource5.isProtected() );    


      System.out.println();

      // Test case for / 
      System.out.println( "uri6: " + uri6 );
      System.out.println( "First Segment: " + resource6.getFirstSegment() );
      System.out.println( "Last Segment: " + resource6.getLastSegment() );
      System.out.println( "Testing isAlias(): " + resource6.isAlias() );
      System.out.println( "Testing isScriptAlias(): " + resource6.isScriptAlias() );
      System.out.println( "Testing getAbsolutePath(): " + resource6.getAbsolutePath() );
      System.out.println( "Testing isProtected(): " + resource6.isProtected() );    

    } catch ( FileNotFoundException e ) {}

    

  }
}