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
      System.out.println( "Testing isScript(): " + resource1.isScript() );
      System.out.println( "Testing absolutePath(): " + resource1.absolutePath() );
      System.out.println( "Testing isProtected(): " + resource1.isProtected() );    

      System.out.println();

      // Test case for Alias with no file /ab/
      System.out.println( "uri2: " + uri2 );
      System.out.println( "Testing isScript(): " + resource2.isScript() );
      System.out.println( "Testing absolutePath(): " + resource2.absolutePath() );
      System.out.println( "Testing isProtected(): " + resource2.isProtected() );    

      System.out.println();

      // Test case for Script Alias /cgi-bin/perl_env
      System.out.println( "uri3: " + uri3 );
      System.out.println( "Testing isScript(): " + resource3.isScript() );
      System.out.println( "Testing absolutePath(): " + resource3.absolutePath() );
      System.out.println( "Testing isProtected(): " + resource3.isProtected() );    

      System.out.println();

      // // Test case for Script Alias with no file /cgi-bin/
      System.out.println( "uri4: " + uri4 );
      System.out.println( "Testing isScript(): " + resource4.isScript() );
      System.out.println( "Testing absolutePath(): " + resource4.absolutePath() );
      System.out.println( "Testing isProtected(): " + resource4.isProtected() );    

      System.out.println();

      // Test case for Alias /~traciely/
      System.out.println( "uri5: " + uri5 );
      System.out.println( "Testing isScript(): " + resource5.isScript() );
      System.out.println( "Testing absolutePath(): " + resource5.absolutePath() );
      System.out.println( "Testing isProtected(): " + resource5.isProtected() );    


      System.out.println();

      // Test case for / 
      System.out.println( "uri6: " + uri6 );
      System.out.println( "Testing isScript(): " + resource6.isScript() );
      System.out.println( "Testing absolutePath(): " + resource6.absolutePath() );
      System.out.println( "Testing isProtected(): " + resource6.isProtected() );    

    } catch ( FileNotFoundException e ) {}

    

  }
}