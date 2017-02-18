import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.net.*;
import java.util.ListIterator;

public class Resource {
  private String absolutePath;
  private HttpdConf httpdConf;
  private String requestUri;
  private String path;
  private String lastSegment;
  private String firstSegment;
  private File file;

  public Resource( String uri, HttpdConf conf ) {
    requestUri = uri;
    httpdConf = conf;
    file = new File(requestUri);

  }

  public String absolutePath() {
    Resource resource = new Resource( requestUri, httpdConf);
    resource.parseUri();
    
    if ( resource.isAlias() ) {
      absolutePath = httpdConf.lookupAlias( firstSegment);
      if ( isDirectory() ) {
        if ( httpdConf.getDirectoryIndexes().equals(null) ) {
          return absolutePath;
        } else {
          absolutePath += httpdConf.getDirectoryIndexes().next();
        }
      }
    }
    
    if ( resource.isScriptAlias() ) {
      absolutePath = httpdConf.lookupScriptAlias( firstSegment );
      if ( isDirectory() ) {
        if ( httpdConf.getDirectoryIndexes().equals(null) ) {
          return absolutePath;
        } else {
          absolutePath += httpdConf.getDirectoryIndexes().next();
        }
      }
    }

    if ( isDirectory() ) {
      if ( httpdConf.getDirectoryIndexes().equals(null) ) {
        return absolutePath;
      } else {
        absolutePath += httpdConf.getDirectoryIndexes().next();
      }
    } else {
      absolutePath = httpdConf.getDocumentRoot() + requestUri;
    }
    return absolutePath;
  }

  public boolean isAlias() {
    return httpdConf.aliasesContainsKey( firstSegment );
  }

  public boolean isScriptAlias() {
    return httpdConf.scriptedAliasesContainsKey( firstSegment );

  }

  public boolean isProtected() {
    return false;
  }

  public void parseUri() {
    // try {
      // URI uri = new URI(requestUri); 
      // path = uri.getPath();
      // lastSegment = path.substring( path.lastIndexOf( '/' ) + 1 );
      
      // File file = new File(requestUri);
      path = file.getPath();
      firstSegment = path.replaceAll(file.getName(),"");
      lastSegment = path.substring( path.lastIndexOf( '/' ) + 1 );

    // } catch (URISyntaxException e) {} 

  }
  public boolean isFile() {
    File file = new File(absolutePath);
    return file.isFile();
  }

  public boolean isDirectory() {
    File file = new File(absolutePath);
    return file.isDirectory();
  }

  public String getPath() {
    return path;
  }

  public String getFirstSegment() {
    return firstSegment;
  }

  public String getLastSegment() {
    return lastSegment;
  }
}