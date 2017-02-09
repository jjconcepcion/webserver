import java.util.HashMap;
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
      
      if( line.startsWith( "#") ) {
        continue;
      }
      
      
      
    }
  }
  
  /*
  * Accessor Methods
  */
  
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
