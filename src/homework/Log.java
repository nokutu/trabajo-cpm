package homework;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Facade of the java.util.logging class.
 */
public class Log {

  private static final Level LEVEL = Level.FINE;
  private final Logger log;
  private final StreamHandler sh;

  private ByteArrayOutputStream out;

  public Log() {
    log = Logger.getLogger("Main");
    log.setUseParentHandlers(false);
    log.setLevel(LEVEL);
    ConsoleHandler ch = new ConsoleHandler();
    ch.setFormatter(new LogFormatter());
    ch.setLevel(LEVEL);
    log.addHandler(ch);

    out = new ByteArrayOutputStream();
    sh = new StreamHandler(new PrintStream(out), new LogFormatter());
    sh.setLevel(LEVEL);
    log.addHandler(sh);
  }

  /**
   * Logs an error.
   *
   * @param message The message to be displayed in the log
   */
  public void e(Object message) {
    log.severe(message.toString());
  }

  /**
   * Logs a warning.
   *
   * @param message The message to be displayed in the log
   */
  public void w(Object message) {
    log.warning(message.toString());
  }

  /**
   * Logs a debug message.
   *
   * @param message The message to be displayed in the log
   */
  public void d(Object message) {
    log.fine(message.toString());
  }

  /**
   * Logs an information message.
   *
   * @param message The message to be displayed in the log
   */
  public void i(Object message) {
    log.info(message.toString());
  }

  public String getLog() {
    sh.flush();
    return new String(out.toByteArray()).trim();
  }
}
