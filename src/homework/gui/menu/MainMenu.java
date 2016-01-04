package homework.gui.menu;


import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static homework.I18n.tr;

/**
 * Created by nokutu on 04/01/2016.
 */
public class MainMenu extends JMenuBar {

  JMenu file;
  JMenuItem exit;

  JMenu tools;
  JMenuItem settings;

  JMenu help;
  JMenuItem helpItem;
  JMenuItem about;

  public MainMenu() {
    file = new JMenu(tr("File"));
    file.setMnemonic('f');
    exit = new JMenuItem(tr("Exit"));
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
    exit.setMnemonic('e');
    file.add(exit);
    add(file);

    tools = new JMenu(tr("Tools"));
    tools.setMnemonic('t');
    settings = new JMenuItem(tr("Settings..."));
    settings.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JDialog settings = new SettingsDialog();
        settings.setVisible(true);
      }
    });
    settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    settings.setMnemonic('s');
    tools.add(settings);
    add(tools);

    help = new JMenu(tr("Help"));
    help.setMnemonic('h');
    about = new JMenuItem(tr("About"));
    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO
      }
    });
    about.setMnemonic('a');
    about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    help.add(about);
    add(help);
  }
}
