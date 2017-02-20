import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.net.*;
import java.util.ListIterator;

public class Resource {
  private HttpdConf httpdConf;
  private String requestUri;
  private String path;
  private String lastSegment;
  private String firstSegment;
  private String tempPath;
  private String directoryIndex;
  private String absolutePath;
  private String workingPath;
  private File file;
  private File fileCheck;
  private MimeTypes mimeType;
  private ListIterator<String> index;
  private Htaccess htaccess;

  public Resource( String uri, HttpdConf conf, MimeTypes mime ) {
    requestUri = uri;
    httpdConf = conf;
    mimeType = mime;
    file = new File(requestUri);
    path = new String(file.getPath());
    index = httpdConf.getDirectoryIndexes();

  }

  public String absolutePath() {
    String modifiedUri;
    String resolvePath;
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

        return absolutePath;
      }
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

        return absolutePath;
      }
    }

    resolvePath = httpdConf.getDocumentRoot().substring(0, httpdConf.getDocumentRoot().length() -1 ) + requestUri;
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

    return absolutePath;

    // if( resource.isFile() ) {
    //   return absolutePath;
    // }

    // absolutePath = httpdConf.getDocumentRoot() + firstSegment + lastSegment;
    // System.out.println( "initial absolute path: " + absolutePath );
    
    // if ( resource.isAlias() ) {
    //   absolutePath = httpdConf.lookupAlias( firstSegment ) + lastSegment;
    //   // System.out.println("is directory? " + isDirectory());
    //   System.out.println( "absolute path: " + absolutePath );
    //   if ( isDirectory() ) {
    //     if ( httpdConf.getDirectoryIndexes().equals(null) ) {
    //       return absolutePath;
    //     } else {
    //       tempPath = absolutePath + directoryIndex;  
    //       // System.out.println(tempPath);        
    //       fileCheck = new File(tempPath);

    //       while ( fileCheck.exists() == false) {
    //         directoryIndex = index.next(); 
    //         tempPath = absolutePath + directoryIndex;
    //         fileCheck = new File( tempPath );
    //       }

    //       // absolutePath += httpdConf.getDirectoryIndexes().next();
    //       // fileCheck = new File(absolutePath);
    //       // System.out.println( "File exists: " + fileCheck.exists() );
    //       return absolutePath += directoryIndex;

    //     }
    //   }
    // }
    
    // if ( resource.isScriptAlias() ) {
    //   absolutePath = httpdConf.lookupScriptAlias( firstSegment ) + lastSegment;
    //   System.out.println( "absolute path: " + absolutePath );

    //   if ( isDirectory() ) {
    //     if ( httpdConf.getDirectoryIndexes().equals(null) ) {
    //       return absolutePath;
    //     } else {
          
    //       tempPath = absolutePath + directoryIndex;          
    //       fileCheck = new File(tempPath);
    //       // System.out.println("File exists? " + fileExists());
    //       while ( fileCheck.exists() == false) {
    //         directoryIndex = index.next(); 
    //         tempPath = absolutePath + directoryIndex;
    //         fileCheck = new File( tempPath );
    //       }
    //       return absolutePath += directoryIndex;
    //     }
    //   }
    // }

    // absolutePath = httpdConf.getDocumentRoot() + firstSegment + lastSegment;

    // if ( resource.isDirectory() ) {
    //   // absolutePath = httpdConf.lookupScriptAlias( firstSegment );

    //   if ( httpdConf.getDirectoryIndexes().equals(null) ) {
    //     return absolutePath;
    //   } else {
    //     absolutePath += index.next();
    //   }
    // } else {
    //   absolutePath = httpdConf.getDocumentRoot() + requestUri;
    // }
    // return absolutePath;
  }

  public void parseUri() {
    // try {
      // URI uri = new URI(requestUri); 
      // path = uri.getPath();
      // lastSegment = path.substring( path.lastIndexOf( '/' ) + 1 );
      
      // File file = new File(requestUri);

      // path = file.getPath();
      // System.out.println( "parseUri path: " + path );
      firstSegment = path.replaceAll(file.getName(),"");
      lastSegment = path.substring( path.lastIndexOf( '/' ) + 1);
      // System.out.println( "the first segment: " + firstSegment );
      // System.out.println( "the last segment: " + lastSegment );
      if( firstSegment.equals( "/" ) && !lastSegment.equals("") ) {
        firstSegment += lastSegment + "/";
        lastSegment = "";
      } else {
        lastSegment = "";
      }

    // } catch (URISyntaxException e) {} 
  }

  public boolean isAlias() {
    return ( httpdConf.aliasesContainsKey( firstSegment ) || 
      httpdConf.aliasesContainsKey( firstSegment + lastSegment + "/" ) );
  }

  public boolean isScriptAlias() {
    return ( httpdConf.scriptedAliasesContainsKey( firstSegment ) || 
      httpdConf.scriptedAliasesContainsKey( firstSegment + lastSegment + "/" ) );

  }

  public boolean isProtected() {
    return false;
  }

  public boolean isFile(String path) {
    File files = new File(path);
    return files.isFile();
  }

  public boolean isDirectory(String path) {
    File files = new File(path);
    return files.isDirectory();
  }

  public boolean fileExists() {
    return false;
  }

  // public String getPath() {
  //   return path;
  // }

  public String getFirstSegment() {
    return firstSegment;
  }

  public String getLastSegment() {
    return lastSegment;
  }
}