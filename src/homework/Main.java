package homework;

import homework.gui.MainFrame;
import org.jvnet.substance.SubstanceLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Main implements Runnable{

  public static Log log;
  public static Preferences prefs;

  public static MainFrame frame;

  public static Database db;

  public static void main(String[] args) {
    log = new Log();

    try {
      prefs = new Preferences();
      EventQueue.invokeLater(new Main());
    } catch (Exception e) {
      log.e("Program closed because of an exception e: " + Utils.getStackTrace(e));
    } finally {
      while (frame == null || frame.isVisible()) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          log.e(e);
        }
      }
      log.i("Program ended");
      try {
        prefs.writeToFile();
      } catch (FileNotFoundException e) {
        log.e("Can't write preferences file: " + e);
      }
    }
  }

  public void run() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
    try {
     UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
      log.e(e);
    }
    setUIFont(new javax.swing.plaf.FontUIResource("Serif", Font.PLAIN, 13));
    log.i("Program starts");

    try {
      parseFiles();
    } catch (IOException e) {
      log.e(e);
    }

    frame = new MainFrame();
    frame.start();

  }

  private void parseFiles() throws IOException {
    db = new Database();
    db.loadUsers();
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
