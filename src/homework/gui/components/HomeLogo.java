package homework.gui.components;

import homework.Main;
import homework.Utils;
import homework.gui.MainFrame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main logo of the application, that links to the initial panel.
 */
public class HomeLogo extends JPanel {

  public HomeLogo() {
    ImageIcon logoImage = new ImageIcon(getClass().getResource("/images/logo.JPG"));
    JButton btn = new JButton();
    btn.setIcon(Utils.scale(logoImage, 400, 150));
    btn.setSelectedIcon(Utils.scale(logoImage, 400, 150));
    btn.setBackground(null);
    btn.setBorder(null);
    btn.setFocusable(false);
    btn.setBorderPainted(false);
    btn.setRolloverEnabled(false);
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.show(MainFrame.INITIAL_PANEL);
      }
    });
    add(btn);
  }
}
