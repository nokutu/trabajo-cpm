package homework;

import homework.gui.MainFrame;

import java.io.FileNotFoundException;

public class Main {

  public static Log log;
  public static Preferences prefs;

  public static MainFrame frame;

  public static void main(String[] args) {
    log = new Log();
    try {
      prefs = new Preferences();
      new Main().run();
    } catch (Exception e) {
      log.e("Program closed because of an exception e: " + Utils.getStackTrace(e));
    } finally {
      log.i("Program ended");
      try {
        prefs.writeToFile();
      } catch (FileNotFoundException e) {
        log.e("Can't write preferences file: " + e);
      }
    }
  }

  private void run() {
    log.i("Program starts");

    frame = new MainFrame();
    frame.start();
    while (frame.isVisible()) {
      synchronized (this) {
        try {
          wait(1000);
        } catch (InterruptedException e) {
          log.e(Utils.getStackTrace(e));
        }
      }
    }
  }
}
