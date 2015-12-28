package homework;

import homework.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

  public static Log log;
  public static Preferences prefs;

  public static MainFrame frame;

  public static Database db;

  public static void main(String[] args) {
    log = new Log();
    setUIFont(new javax.swing.plaf.FontUIResource("Serif", Font.PLAIN, 12));
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

  private void run() throws IOException {
    log.i("Program starts");

    parseFiles();

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

  private void parseFiles() throws IOException {
    db = new Database();
    db.addShips(Parser.parse(new File("./barcos.dat")));
    db.addCruises(Parser.parse(new File("./cruceros.dat")));
    db.addExtras(Parser.parse(new File("./extras.dat")));
  }

  public static void setUIFont(javax.swing.plaf.FontUIResource f) {
    java.util.Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
        UIManager.put(key, f);
    }
  }


}
