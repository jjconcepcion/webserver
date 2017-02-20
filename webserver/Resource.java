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

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    requestUri = uri;
    httpdConf = conf;
    mimeType = mime;
    index = httpdConf.getDirectoryIndexes();
    createFile(absolutePath());
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
        tempPath = resolvePath + directoryIndex;
        fileCheck = new File( tempPath );

        while ( fileCheck.exists() == false ) {
          directoryIndex = index.next();
          tempPath = resolvePath + directoryIndex;
          fileCheck = new File( tempPath );
        }
        resolvePath = tempPath;
        absolutePath = resolvePath;
        createFile(absolutePath);
      }
      return absolutePath;
    }

    if ( isScriptAlias() ) {
      modifiedUri = httpdConf.lookupScriptAlias( firstSegment ) + lastSegment;
      resolvePath = modifiedUri;

      if ( isFile (resolvePath ) ) {
        absolutePath = resolvePath;
      } else {
        tempPath = resolvePath + directoryIndex;
        fileCheck = new File( tempPath );

        while ( fileCheck.exists() == false ) {
          directoryIndex = index.next();
          tempPath = resolvePath + directoryIndex;
          fileCheck = new File( tempPath );
        }

        resolvePath = tempPath;
        absolutePath = resolvePath;
        createFile(absolutePath);
      }
      return absolutePath;
    }

    resolvePath = httpdConf.getDocumentRoot().substring(0,
      httpdConf.getDocumentRoot().length() -1 ) + requestUri;
    
    if ( isFile( resolvePath ) ) {
      absolutePath = resolvePath;
    } else {
      tempPath = resolvePath + directoryIndex;
      fileCheck = new File( tempPath );

      while ( fileCheck.exists() == false ) {
        directoryIndex = index.next();
        tempPath = resolvePath + directoryIndex;
        fileCheck = new File( tempPath );
      }
      
      resolvePath = tempPath;
      absolutePath = resolvePath;
    }
    createFile(absolutePath);
    return absolutePath;
  }

  public String getAbsolutePath() {
    return absolutePath;
  }

  public void parseUri() { 
      String path;  
      File file = new File(requestUri);
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
    return ( httpdConf.scriptedAliasesContainsKey( firstSegment ) || 
      httpdConf.scriptedAliasesContainsKey( firstSegment + lastSegment + "/" ));

  }

  public boolean isProtected() {
    String directory = null;
    File tempPath = new File( absolutePath );
    boolean check = false;

    while ( check == false ) {
      directory = tempPath.getParent();
      tempPath = new File (directory);
      check = new File( directory, httpdConf.getAccessFileName() ).exists();
      
      if (directory.equals( httpdConf.getDocumentRoot())) {
        break;
      }
    }
    return check;
  }

  public boolean isFile( String path ) {
    File tempFile = new File(path);
    return tempFile.isFile();
  }

  public String getFirstSegment() {
    return firstSegment;
  }

  public String getLastSegment() {
    return lastSegment;
  }
}