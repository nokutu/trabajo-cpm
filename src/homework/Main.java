package homework;

import java.io.IOException;

import homework.gui.MainFrame;

public class Main {

  private static Log log;

  public static void main(String[] args) {
    try {
      log = new Log(0);
    } catch (IOException e) {
      System.err.println(e);
      System.exit(1);
    }
    try {
      new Main().run();
    } catch (Exception e) {
      log.e("Program closed because of an exception e: " + e);
    } finally {
      try {
        log.close();
      } catch (IOException e) {
        log.e(e.toString());
      }
    }
  }

  private void run() {
    log.d("Program starts");
    MainFrame frame = new MainFrame();
    frame.start();
  }
}
