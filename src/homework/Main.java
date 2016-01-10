package homework;

import homework.gui.MainFrame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

public class Main implements Runnable {

  public static Log log;
  public static Preferences prefs;

  public static MainFrame frame;

  public static Database db;

  public static void main(String[] args) {
    log = new Log();

    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
      log.e(e);
    }
    setUIFont(new javax.swing.plaf.FontUIResource("Serif", Font.PLAIN, 13));
    UIDefaults defs = UIManager.getDefaults();
    defs.put("TextArea.background", new Color(214, 217, 223));

    Thread main = null;
    try {
      prefs = new Preferences();
      EventQueue.invokeLater(main = new Thread(new Main()));
    } catch (Exception e) {
      log.e("Program closed because of an exception e: " + Utils.getStackTrace(e));
    } finally {
      while (frame == null ||  main.isAlive() || frame.isVisible() || !frame.started) {
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
    System.exit(0);
  }

  public void run() {
    I18n.clear();
    Locale.setDefault(new Locale(prefs.get("language", "en")));

    ToolTipManager.sharedInstance().setEnabled(!prefs.getBoolean("disable-tooltips", false));

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
    db.readOrders();
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
