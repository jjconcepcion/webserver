import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ListIterator;

public class Resource {
  private HttpdConf conf;
  private String uri;
  private String lastSegment;
  private String firstSegment;
  private String directoryIndex;
  private String absolutePath;
  private File file;
  private MimeTypes mime;
  private ListIterator<String> index;
  private boolean isScript;
  private boolean isAlias;
  private boolean isProtected;

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    this.uri = uri;
    this.conf = conf;
    this.mime = mime;
    index = conf.getDirectoryIndexes();
    createFile( absolutePath() );
    isScript = isScriptAlias();
    isProtected = isProtected();
  }

  public String absolutePath() {
    String modifiedUri;
    String resolvePath;
    String tempPath;
    File fileCheck;

    parseUri();
    
    if( isAlias() ) {
      modifiedUri = conf.lookupAlias( firstSegment ) + lastSegment;
      resolvePath = modifiedUri;

      if ( isFile( resolvePath ) ) {
        absolutePath = resolvePath;
      } else {
        tempPath = resolvePath;

        while ( index.hasNext() ) {
          directoryIndex = index.next();
          tempPath = resolvePath + directoryIndex;
          fileCheck = new File( tempPath );

          if (fileCheck.exists()) {
            break;
          }
        }
        
        resolvePath = tempPath;
        absolutePath = resolvePath;
      }
      return absolutePath;
    }

    if ( isScriptAlias() ) {
      modifiedUri = conf.lookupScriptAlias( firstSegment ) + lastSegment;
      resolvePath = modifiedUri;

      if ( isFile ( resolvePath ) ) {
        absolutePath = resolvePath;
      } else {
        tempPath = resolvePath;

        while ( index.hasNext() ) {
          directoryIndex = index.next();
          tempPath = resolvePath + directoryIndex;
          fileCheck = new File( tempPath );
          if (fileCheck.exists()) {
            break;
          }
        }
        resolvePath = tempPath;
        absolutePath = resolvePath;
      }
      return absolutePath;
    }

    resolvePath = conf.getDocumentRoot().substring(0,
      conf.getDocumentRoot().length() -1 ) + uri;
    
    if ( isFile( resolvePath ) ) {
      absolutePath = resolvePath;
    } else {
      tempPath = resolvePath;

      while ( index.hasNext() ) {
        directoryIndex = index.next();
        tempPath = resolvePath + directoryIndex;
        fileCheck = new File( tempPath );
        if (fileCheck.exists()) {
          break;
        }
      }
      
      resolvePath = tempPath;
      absolutePath = resolvePath;
    }
    return absolutePath;
  }

  public String getAbsolutePath() {
    return absolutePath;
  }

  public void parseUri() { 
      String path;  
      File file = new File( uri );
      path = file.getPath();
      firstSegment = path.replaceAll(file.getName(),"");
      lastSegment = path.substring( path.lastIndexOf( '/' ) + 1 );

      if( firstSegment.equals( "/" ) && !lastSegment.equals("") ) {
        firstSegment += lastSegment + "/";
        lastSegment = "";
      }
  }

  public void createFile( String path ) {
    file = new File( path );
  } 

  public File getFile() {
    return file;
  }

  public boolean isAlias() {
    return ( conf.aliasesContainsKey( firstSegment ) || 
      conf.aliasesContainsKey( firstSegment + lastSegment + "/" ) );
  }

  public boolean isScriptAlias() {
    return (conf.scriptedAliasesContainsKey( firstSegment ) || 
      conf.scriptedAliasesContainsKey( firstSegment + lastSegment + "/" ));
  }

  public boolean isScript() {
    return isScript;
  }

  public boolean isProtected() {
    String directory = absolutePath;
    File tempPath = new File( absolutePath );

    while ( isProtected == false ) {
      tempPath = new File( directory );
      isProtected = new File( directory, conf.getAccessFileName() ).exists();
      directory = tempPath.getParent();

      if (directory.equals( conf.getDocumentRoot())) {
        break;
      }
    }
    return isProtected;
  }

  public boolean isFile( String path ) {
    File tempFile = new File( path );
    return tempFile.isFile();
  }

  public MimeTypes getMimeType() {
    return mime;
  }
}