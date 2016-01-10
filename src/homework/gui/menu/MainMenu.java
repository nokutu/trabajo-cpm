package homework.gui.menu;


import homework.Main;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static homework.I18n.tr;

/**
 * The menu bar used by the application.
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
        Main.frame.dispose();
      }
    });
    exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
    exit.setMnemonic('e');
    file.add(exit);
    add(file);

    tools = new JMenu(tr("Tools"));
    tools.setMnemonic('t');
    settings = new JMenuItem(tr("Settings..."));
    settings.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.log.i("Open settings");
        JDialog settings = new SettingsDialog();
        settings.setVisible(true);
        Main.log.i("Close settings");
      }
    });
    settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    settings.setMnemonic('s');
    tools.add(settings);
    add(tools);

    help = new JMenu(tr("Help"));
    help.setMnemonic('h');
    helpItem = new JMenuItem(tr("Help"));
    helpItem.setMnemonic('e');
    help.add(helpItem);
    add(help);
    about = new JMenuItem(tr("About"));
    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(Main.frame, tr("aboutMessage"), tr("About"), JOptionPane.PLAIN_MESSAGE);
      }
    });
    about.setMnemonic('a');
    about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    help.add(about);
  }

  public JMenuItem getHelpButton() {
    return helpItem;
  }
}
