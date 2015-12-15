package homework;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utilities
 */
public class Utils {

  /**
   * Returns a StackTrace array as a String.
   *
   * @param e the Exception containing the StackTrace
   * @return a String representing the StackTrace
   */
  public static String getStackTrace(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }
}
