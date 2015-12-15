package homework.gui;

import homework.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action used by the login button.
 */
public class LoginAction implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    JDialog d = new LoginDialog();
    d.setVisible(true);
    Main.log.i("Login");
  }
}
