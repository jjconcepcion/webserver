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
  private MimeTypes mime;
  private ListIterator<String> index;
  private boolean isScript;
  private boolean isAlias;
  private boolean isProtected;

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    this.uri = uri;
    this.conf = conf;
    this.mime = mime;
    isAlias = false;
    isScript = false;
    isProtected = false;
    index = conf.getDirectoryIndexes();
    resolveAbsolutePath();
  }

  public String absolutePath() {
    return absolutePath;
  }

  private void resolveAbsolutePath() { 
    Path path = Paths.get( uri );
    Iterator<Path> iterator = path.iterator();
    String tempPath;
    String endOfPath = "";
    String modifiedUri = conf.getDocumentRoot().substring(0,
      conf.getDocumentRoot().length() -1 ) + uri;

    while( iterator.hasNext() ) {
      directoryPath = iterator.next().toString();

      if( isAlias() ) {
        while( iterator.hasNext() ) {
          endOfPath = iterator.next().toString();
        }

        if( aliasContainsDocumentRoot() ) {
          modifiedUri = conf.lookupAlias( "/" + directoryPath + "/" );
        } else {
        modifiedUri = conf.getDocumentRoot().substring(0, 
          conf.getDocumentRoot().length() -1 ) + 
            conf.lookupAlias( "/" + directoryPath + "/" );
        }
        break;
      }

      if( isScriptAlias() ) {
        while( iterator.hasNext() ) {
          endOfPath = iterator.next().toString();
        }

        if( scriptAliasContainsDocumentRoot() ) {
          modifiedUri = conf.lookupScriptAlias( "/" + directoryPath + "/" );
        } else {
        modifiedUri = conf.getDocumentRoot().substring(0, 
          conf.getDocumentRoot().length() -1 ) + 
            conf.lookupScriptAlias( "/" + directoryPath + "/" );
        }
        break;
      }
    }

    if( isFile( modifiedUri ) ) {
      absolutePath = modifiedUri;
    } else if( endOfPath == "" ) {
      tempPath = modifiedUri;
      File fileCheck;

      while( index.hasNext() ) {
        directoryIndex = index.next();
        tempPath = modifiedUri + directoryIndex;
        fileCheck = new File( tempPath );
        
        if( fileCheck.exists() ) {
          endOfPath = directoryIndex;
          break;
        }
      }
    }
    absolutePath = modifiedUri + endOfPath;
  }

  private void createFile( String path ) {
    file = new File( path );
  } 

  public File getFile() {
    return file;
  }

  private boolean aliasContainsDocumentRoot() {
    return ( conf.lookupAlias( "/" + directoryPath + 
      "/" ).contains(conf.getDocumentRoot() ) );
  }

  private boolean scriptAliasContainsDocumentRoot() {
    return ( conf.lookupScriptAlias( "/" + directoryPath + 
      "/").contains( conf.getDocumentRoot() ) );
  }

  private boolean isFile( String path ) {
    File tempFile = new File( path );
    return tempFile.isFile();
  }

  public MimeTypes getMimeType() {
    return mime;
  }

  public boolean isAlias() {
    isAlias = conf.aliasesContainsKey( "/" + directoryPath + "/" );
    return isAlias;
  }

  public boolean isScriptAlias() {
    isScript = conf.scriptedAliasesContainsKey( "/" + directoryPath + "/" );
    return isScript;
  }

  public boolean isScript() {
    return isScript;
  }

  public boolean isProtected() {
    String directory = absolutePath;
    File tempPath = new File( directory );

    while ( isProtected == false ) {
      tempPath = new File( directory );
      isProtected = new File( directory, conf.getAccessFileName() ).exists();
      directory = tempPath.getParent() + "/";
      if (directory.equals( conf.getDocumentRoot() ) ) {
        break;
      }
    }
    return isProtected;
  }
}
