package homework.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Navbar used in most of the application views.
 */
public class Navbar extends JPanel {

  public JButton loginButton;

  public Navbar() {
    super();
    setLayout(new GridBagLayout());
    addLoginButton();
  }

  private void addLoginButton() {
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.LINE_END;
    c.gridx = 1;
    c.weightx = 1;
    c.insets = new Insets(0, 0, 0, 20);
    add(getLoginButton(), c);
  }

  public JButton getLoginButton() {
    if (loginButton == null) {
      loginButton = new JButton("Login");
      loginButton.setBorder(null);
      loginButton.setOpaque(false);
      loginButton.setContentAreaFilled(false);
      loginButton.setBorderPainted(false);
      loginButton.addActionListener(new LoginAction());
    }
    return loginButton;
  }

}
