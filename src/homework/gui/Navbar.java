package homework.gui;

import javax.swing.*;
import java.awt.*;

import static homework.I18n.tr;

/**
 * Navbar used in most of the application views.
 */
public class Navbar extends JPanel {

  public JButton loginButton;
  private JButton registerButton;

  public Navbar() {
    super();
    setLayout(new GridBagLayout());
    addLoginPanel();
  }

  private void addLoginPanel() {
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.LINE_END;
    c.gridx = 0;
    c.weightx = 1;
    c.insets = new Insets(0, 0, 0, 20);
    add(getLogInRegisterPanel(), c);
  }

  public JButton getLoginButton() {
    if (loginButton == null) {
      loginButton = new JButton(tr("Login"));
      loginButton.setBorder(null);
      loginButton.setOpaque(false);
      loginButton.setContentAreaFilled(false);
      loginButton.setBorderPainted(false);
      loginButton.addActionListener(new LoginAction());
    }
    return loginButton;
  }

  public JButton getRegisterButton() {
    if (registerButton == null) {
      registerButton = new JButton(tr("Register"));
      registerButton.setBorder(null);
      registerButton.setOpaque(false);
      registerButton.setContentAreaFilled(false);
      registerButton.setBorderPainted(false);
      //registerButton.addActionListener(new RegisterAction());
    }
    return registerButton;
  }

  private JPanel getLogInRegisterPanel() {
    JPanel p = new JPanel();
    p.add(getLoginButton());
    p.add(new JLabel("/"));
    p.add(getRegisterButton());
    return p;
  }

}
