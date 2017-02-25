import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.ListIterator;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HttpdConf extends ConfigurationReader {
  private HashMap<String,String> aliases;
  private HashMap<String,String> scriptAliases;
  private LinkedList<String> directoryIndex;
  private String serverRoot;
  private String documentRoot;
  private int listen;
  private String logFile;
  private String accessFileName = ".htaccess";
  
  public HttpdConf( String fileName ) throws FileNotFoundException {
    super( fileName );
    aliases = new HashMap<String,String>();
    scriptAliases = new HashMap<String,String>();
    directoryIndex = new LinkedList<String>();
  }
  
  public void load() throws IOException {
    String line = null;
    
    while( hasMoreLines() ) {
      line = nextLine();
      if( line.startsWith( "#" ) || line.isEmpty() ) {
        continue;
      }
      parseDirective( line );
    }
  }
  
  private void parseDirective( String line ) {
    StringTokenizer tokens = new StringTokenizer( line );
    String directive, urlPath, directoryPath;
    
    directive = tokens.nextToken();
    switch( directive ) {
      case "Alias": 
        urlPath = tokens.nextToken();
        directoryPath = tokens.nextToken().replace( "\"", "" );
        aliases.put( urlPath, directoryPath );
        break;
      case "ScriptAlias": 
        urlPath = tokens.nextToken();
        directoryPath = tokens.nextToken().replace( "\"", "" );
        scriptAliases.put( urlPath, directoryPath );
        break;
      case "DirectoryIndex":
        while( tokens.hasMoreTokens() ) {
          urlPath = tokens.nextToken().replace( "\n", "" );
          directoryIndex.add( urlPath );
        }
        break;
      case "ServerRoot":
        serverRoot = tokens.nextToken().replace( "\"", "" );
        break;
      case "DocumentRoot": 
        documentRoot = tokens.nextToken().replace( "\"", "" );
        break;
      case "Listen":
        listen = Integer.parseInt( tokens.nextToken() );
        break;
      case "LogFile":
        logFile = tokens.nextToken().replace( "\"", "" );
        break;
      case "AccessFileName":
        accessFileName = tokens.nextToken().replace( "\"", "" );
        break;
    }
  }
  
  public String lookupAlias( String urlPath ) {
    return aliases.get( urlPath );
  }

  public boolean aliasesContainsKey( String key ) {
    return aliases.containsKey( key );
  }
  
  public String lookupScriptAlias( String urlPath ) {
    return scriptAliases.get( urlPath );
  }

    public boolean scriptedAliasesContainsKey( String key ) {
    return scriptAliases.containsKey( key );
  }

  public String getServerRoot() {
    return serverRoot;
  }
  
  public String getDocumentRoot() {
    return documentRoot;
  }
  
  public int getListenPort() {
    return listen;
  }
  
  public String getLogFile() {
    return logFile;
  }
  
  public String getAccessFileName() {
    return accessFileName;
  }
  
  public ListIterator<String> getDirectoryIndexes() {
    return directoryIndex.listIterator( 0 );
  }
}
