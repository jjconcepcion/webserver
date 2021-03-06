package resource;

import configuration.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ListIterator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Iterator;

public class Resource {
  private HttpdConf conf;
  private String uri;
  private String directoryIndex;
  private String absolutePath;
  private String accessFilePath;
  private MimeTypes mimes;
  private ListIterator<String> indexes;
  private boolean isFile;
  private boolean isScript;
  private boolean isProtected;
  private boolean exists;

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    this.uri = uri;
    this.conf = conf;
    this.mimes = mime;
    isScript = false;
    isProtected = false;
    indexes = conf.getDirectoryIndexes();
    this.resolveAbsolutePath();
    this.exists = Files.exists( Paths.get( absolutePath ));
    this.resolveAccessFilePath();
  }

  public String absolutePath() {
    return absolutePath;
  }

  private void resolveAbsolutePath() {
    absolutePath = "";
    
    if( uri.equals( "/" ) ) {
      absolutePath = conf.getDocumentRoot();
    } else {
      StringTokenizer tokens = new StringTokenizer( uri, "/" );
      String temporaryPath = "/";
      boolean isDirectory = this.uri.endsWith( "/" );
      
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
        absolutePath = conf.getDocumentRoot() + uri.replaceFirst( "/", "" );
      }
    }
    
    if( absolutePath.endsWith( "/" ) ) {
      tryAppendDirectoryIndex();
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

  private void tryAppendDirectoryIndex() {
    directoryIndex = "";
    
    while( indexes.hasNext() ) {
      directoryIndex = indexes.next();
      
      if( Files.exists( Paths.get( absolutePath + directoryIndex ) ) ) {
        absolutePath += directoryIndex;
      }
    }
  } 

  private boolean isAlias( String path ) {
    return conf.lookupAlias( path ) != null ;
  }
  
  private boolean isScriptAlias( String path ) {
    return conf.lookupScriptAlias( path ) != null ;
  }
  
  public boolean isScript() {
    return isScript;
  }
  
  public boolean isProtected() {
    return isProtected;
  }
  
  public String getMimeType() {
    String[] pathTokens;
    String extensions;
    
    pathTokens = absolutePath.split( "\\." );
    extensions = pathTokens[ pathTokens.length - 1 ];
    
    return mimes.lookup( extensions );
  }

  private void resolveAccessFilePath() {
    accessFilePath = absolutePath;
    Path tempPath;
    
    while( isProtected == false ) {
      tempPath = Paths.get( accessFilePath );
      isProtected = tempPath.resolve( 
        conf.getAccessFileName() ).toFile().exists();

      if( accessFilePath.equals( conf.getDocumentRoot() ) ) {
        break;
      }
      accessFilePath = tempPath.getParent() + "/";
    }

    if ( isProtected == true ) {
      accessFilePath += conf.getAccessFileName();
    }
  }

  public String accessFilePath() {
    return accessFilePath;
  }

  public boolean exists() {
    return exists;
  }

  public ListIterator<String> getIndexes() {
    return conf.getDirectoryIndexes();
  }
}
