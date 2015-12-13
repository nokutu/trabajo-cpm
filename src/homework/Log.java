package homework;

import java.util.logging.*;

/**
 * Created by Jorge López on 13/12/15.
 */
public class Log {

  private static final Level LEVEL = Level.FINE;
  private static Logger log;

  private ConsoleHandler ch;

  public Log() {
    log = Logger.getLogger("Main");
    log.setUseParentHandlers(false);
    log.setLevel(LEVEL);
    ch = new ConsoleHandler();
    ch.setLevel(LEVEL);
    log.addHandler(ch);
  }

  public void e(Object message) {
    log.severe(message.toString());
  }

  public void w(Object message) {
    log.warning(message.toString());
  }

  public void d(Object message) {
    log.fine(message.toString());
  }

  public void i(Object message) {
    log.info(message.toString());
  }

}
