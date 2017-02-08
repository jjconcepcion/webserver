import java.io.*;

public class ConfigurationReaderTest {
  public static void main(String[] args) throws FileNotFoundException {
    ConfigurationReader reader;
    
    try {
      reader = new SubClassedReader( args[0] );
      System.out.println( "\n>>>SubClassedReader Instantiated" );
      
      try {
        System.out.println( "\n>>>Printing file contents" );
        
        if( !reader.hasMoreLines() ) {
          System.out.println( "Empty File" );
        }
        
        while( reader.hasMoreLines() ) {
          System.out.println( reader.nextLine() );
        }

        System.out.println( "\n>>>Invoking abstract method load()" );
        reader.load();
        
      } catch( IOException IOE ) {
      }
    } catch( ArrayIndexOutOfBoundsException | FileNotFoundException e ) {
      System.out.println( "Pass filename of config file as argument" );
    }
  
    
  }
  
  public static class SubClassedReader extends ConfigurationReader {
    public SubClassedReader( String fileName ) throws FileNotFoundException {
      super( fileName );
    }
    
    public void load() {
      System.out.println( "Invocation successful" );
    }
  }
  
}
