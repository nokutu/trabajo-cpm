package homework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

  public final static int ALL = 0;
  public final static int DEBUG = 1;
  public final static int WARNING = 2;
  public final static int ERROR = 3;
  public final static int OFF = 4;

  private int level;
  private PrintWriter output;

  public Log() throws IOException {
    this(WARNING);
  }

  public Log(int level) throws IOException {
    this.level = level;
    output = new PrintWriter(new FileWriter(new File("log.txt"), true));
  }

  public void close() throws IOException {
    output.close();
  }

  public void e(Object message) {
    if (level <= ERROR) {
      log(ERROR, message);
    }
  }

  public void w(Object message) {
    if (level <= WARNING) {
      log(WARNING, message);
    }
  }

  public void d(Object message) {
    if (level <= DEBUG) {
      log(DEBUG, message);
    }
  }

  private void log(int level, Object text) {
    String levelTag = getLevelText(level);
    String msg = getTime() + ": " + levelTag + ": " + text;
    output.println(msg);
    System.out.println(msg);
  }

  private String getLevelText(int level) {
    switch (level) {
    case DEBUG:
      return "DEBUG";
    case WARNING:
      return "WARNING";
    case ERROR:
      return "ERROR";
    default:
      throw new IllegalArgumentException("Invalid level");
    }
  }
  
  private String getTime() {
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("MM-DD hh:mm:ss.SSS");
    return format.format(now);
  }
}
