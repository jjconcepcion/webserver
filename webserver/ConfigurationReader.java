import java.io.*;

public abstract class ConfigurationReader {
  private File file;
  private BufferedReader reader;
  
  public ConfigurationReader( String fileName ) throws FileNotFoundException {
    file = new File( fileName );
    reader = new BufferedReader( new FileReader( file ) );
  }
  
  public boolean hasMoreLines() throws IOException {
    boolean hasMoreLines;
    int readAheadLimit = 1000;
    
    reader.mark(readAheadLimit);
    hasMoreLines = (nextLine() != null);
    reader.reset();
    
    return hasMoreLines;
  }
  
  public String nextLine() throws IOException {
    return reader.readLine();
  }
  
  abstract void load();
}
