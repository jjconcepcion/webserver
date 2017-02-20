import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ListIterator;

public class Resource {
  private HttpdConf httpdConf;
  private String requestUri;
  private String lastSegment;
  private String firstSegment;
  private String directoryIndex;
  private String absolutePath;
  private File file;
  private MimeTypes mimeType;
  private ListIterator<String> index;
  private boolean isScript;
  private boolean isAlias;
  private boolean isProtected;

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    requestUri = uri;
    httpdConf = conf;
    mimeType = mime;
    index = httpdConf.getDirectoryIndexes();
    absolutePath();
    // createFile(absolutePath());
    isScript = isScriptAlias();
    // isProtected = isProtected();

  }

  public String absolutePath() {
    String modifiedUri;
    String resolvePath;
    String tempPath;
    File fileCheck;

    parseUri();
    
    if( isAlias() ) {
      modifiedUri = httpdConf.lookupAlias( firstSegment ) + lastSegment;
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
        // createFile(absolutePath);
      }
      return absolutePath;
    }

    if ( isScriptAlias() ) {
      modifiedUri = httpdConf.lookupScriptAlias( firstSegment ) + lastSegment;
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
        // createFile(absolutePath);
      }
      return absolutePath;
    }

    resolvePath = httpdConf.getDocumentRoot().substring(0,
      httpdConf.getDocumentRoot().length() -1 ) + requestUri;
    
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
    // createFile(absolutePath);
    return absolutePath;
  }

  public String getAbsolutePath() {
    return absolutePath;
  }

  public void parseUri() { 
      String path;  
      File file = new File( requestUri );
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
    return ( httpdConf.aliasesContainsKey( firstSegment ) || 
      httpdConf.aliasesContainsKey( firstSegment + lastSegment + "/" ) );
  }

  public boolean isScriptAlias() {
    return (httpdConf.scriptedAliasesContainsKey( firstSegment ) || 
      httpdConf.scriptedAliasesContainsKey( firstSegment + lastSegment + "/" ));
  }

  public boolean isScript() {
    return isScript;
  }

  public boolean isProtected() {
    String directory = absolutePath;
    File tempPath = new File( absolutePath );
    while ( isProtected == false ) {
      tempPath = new File( directory );
      isProtected = new File( directory, httpdConf.getAccessFileName() ).exists();
      directory = tempPath.getParent();
      
      if (directory.equals( httpdConf.getDocumentRoot())) {
        break;
      }
    }
    return isProtected;
  }

  public boolean isFile( String path ) {
    File tempFile = new File( path );
    return tempFile.isFile();
  }

  public String getFirstSegment() {
    return firstSegment;
  }

  public String getLastSegment() {
    return lastSegment;
  }
}