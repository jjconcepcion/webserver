import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ListIterator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class Resource {
  private HttpdConf conf;
  private String uri;
  private String directoryPath;
  private String directoryIndex;
  private String absolutePath;
  private File file;
  private MimeTypes mimes;
  private ListIterator<String> indexes;
  private boolean isScript;
  private boolean isAlias;
  private boolean isProtected;

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    this.uri = uri;
    this.conf = conf;
    this.mimes = mime;
    isAlias = false;
    isScript = false;
    isProtected = false;
    indexes = conf.getDirectoryIndexes();
    resolveAbsolutePath();
  }

  public String absolutePath() {
    return absolutePath;
  }

 // Initialize absolutePath and sets isScript to true if script-aliased
  public void resolveAbsolutePath() {
    absolutePath = "";
    
    if( uri.equals( "/" ) ) {
      absolutePath = conf.getDocumentRoot();
    } else {
      StringTokenizer tokens = new StringTokenizer( uri, "/" );
      String temporaryPath = "/";
      boolean isDirectory = this.uri.endsWith("/");
      
      while( tokens.hasMoreTokens() ) {
        temporaryPath += tokens.nextToken();
        
        if( tokens.hasMoreTokens() || isDirectory ) {
          temporaryPath += "/" ;
        }
        
        if( isAlias( temporaryPath.toString() ) ) {
          absolutePath = conf.lookupAlias( temporaryPath ) + 
            remainingPath( tokens, isDirectory );
          break;
        }
        
        if( isScriptAlias( temporaryPath ) ) {
          absolutePath = conf.lookupScriptAlias( temporaryPath ) +
            remainingPath( tokens, isDirectory );
            this.isScript = true;
            break;
        }
      }
      
      if( absolutePath.equals( "" ) ) {
        absolutePath = conf.getDocumentRoot() + uri.replaceFirst( "/", "");
      }
    }
    
    if( absolutePath.endsWith("/") ) {
      addDirectoryIndexToAbsolutePath();
    }
  }
  
  private String remainingPath( StringTokenizer tokens, 
      boolean trailingSlash ) {
    String remainder = "";
    
    while( tokens.hasMoreTokens() ) {
      remainder += tokens.nextToken();
      
      if( tokens.hasMoreTokens() || trailingSlash ) {
        remainder += "/";
      }
    }

    return remainder;
  }
  
  private void addDirectoryIndexToAbsolutePath() {
    directoryIndex = "";
    
    while( directoryIndex.equals("") && indexes.hasNext() ) {
      directoryIndex = indexes.next();
    }
    
    absolutePath += directoryIndex;
  }
  
  private boolean isAlias(String path) {
    return conf.lookupAlias( path ) != null ;
  }
  
  private boolean isScriptAlias(String path) {
    return conf.lookupScriptAlias( path ) != null ;
  }
  
  public boolean isScript() {
    return isScript;
  }
  
  public boolean isProtected() {
    String directory = absolutePath;
    File tempPath = new File( directory );
    while ( isProtected == false ) {
      tempPath = new File( directory );
      directory = tempPath.getParent() + "/";
      isProtected = new File( directory, conf.getAccessFileName() ).exists();
      if (directory.equals( conf.getDocumentRoot()) ) {
        break;
      }
    }
    return isProtected;
  }
  
  public String getMimeType() {
    String[] pathTokens;
    String extensions;
    
    pathTokens = absolutePath.split("\\.");
    extensions = pathTokens[ pathTokens.length - 1 ];
    
    return mimes.lookup( extensions );
  }
}
