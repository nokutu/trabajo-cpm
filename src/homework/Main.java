package homework;

import homework.gui.MainFrame;

import javax.swing.*;

public class Main {

  public static Log log;
  public static Pref prefs;

  public static MainFrame frame;

  public static void main(String[] args) {
    log = new Log();
    prefs = new Pref();
    try {
      new Main().run();
    } catch (Exception e) {
      log.e("Program closed because of an exception e: " + Utils.getStackTrace(e));
    } finally {
      log.i("Program ended");
    }
  }

  private void run() {
    log.i("Program starts");

    frame = new MainFrame();
    frame.start();
  }
}
