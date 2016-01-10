package homework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formatter for logging output.
 */
public class LogFormatter extends Formatter {

  private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM-hh:mm:ss.SSS");
  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  @Override
  public String format(LogRecord record) {
    return format.format(new Date(record.getMillis())) + " - " + record.getLevel() + ": " + record.getMessage() + LINE_SEPARATOR;
  }
}
