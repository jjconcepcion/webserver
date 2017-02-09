import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HttpdConf extends ConfigurationReader {
  private HashMap<String,String> aliases;
  private HashMap<String,String> scriptAliases;
  private String serverRoot;
  private String documentRoot;
  private int listen;
  private String logFile;
  
  public HttpdConf( String fileName ) throws FileNotFoundException {
    super( fileName );
    aliases = new HashMap<String,String>();
    scriptAliases = new HashMap<String,String>();
  }
  
  public void load() throws IOException {
    String line = null;
    
    while( hasMoreLines() ) {
      line = nextLine();
      if( line.startsWith( "#" ) ) {
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
        directoryPath = tokens.nextToken().replace("\"","");
        aliases.put( urlPath, directoryPath );
        break;
      case "ScriptAlias": 
        urlPath = tokens.nextToken();
        directoryPath = tokens.nextToken().replace("\"","");
        scriptAliases.put( urlPath, directoryPath );
        break;
      case "ServerRoot":
        serverRoot = tokens.nextToken().replace("\"","");
        break;
      case "DocumentRoot": 
        documentRoot = tokens.nextToken().replace("\"","");
        break;
      case "Listen":
        listen = Integer.parseInt( tokens.nextToken() );
        break;
      case "LogFile":
        logFile = tokens.nextToken().replace("\"","");
        break;
    }
  }
  
  public String lookupAlias( String urlPath ) {
    return aliases.get( urlPath );
  }
  
  public String lookupScriptAlias( String urlPath ) {
    return scriptAliases.get( urlPath );
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
}
