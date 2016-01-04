package homework.gui.components;

import homework.Main;
import homework.Utils;
import homework.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nokutu on 04/01/2016.
 */
public class HomeLogo extends JPanel {

  public HomeLogo() {
    ImageIcon logoImage = new ImageIcon(getClass().getResource("/images/logo.JPG"));
    JButton btn = new JButton();
    btn.setIcon(Utils.scale(logoImage, 400, 150));
    btn.setSelectedIcon(logoImage);
    btn.setBackground(null);
    btn.setBorder(null);
    btn.setFocusable(false);
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.INITIAL_PANEL);
      }
    });
    add(btn);
  }
}
