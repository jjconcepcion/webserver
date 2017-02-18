import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FormattedDate {
  private LocalDateTime date;
  private static DateTimeFormatter RFC_1123 = (     
    DateTimeFormatter.RFC_1123_DATE_TIME
  );
  
  public FormattedDate( LocalDateTime date ) {
    this.date = date;
  }
  
  public String toString() {
    return ZonedDateTime.of( this.date, ZoneId.of( "GMT" ) ).format( RFC_1123 );
  }
}
