package homework;

import java.io.IOException;

import homework.gui.MainFrame;

public class Main {

  private static Log log;

  public static void main(String[] args) {
    try {
      log = new Log();
      new Main().run();
    } catch (IOException e) {
      System.err.println(e);
    }
    finally {
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
