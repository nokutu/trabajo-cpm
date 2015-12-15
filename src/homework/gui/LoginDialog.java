package homework.gui;

import homework.Main;

import javax.swing.*;

import static homework.I18n.tr;

/**
 * Dialog used for login.
 */
public class LoginDialog extends JDialog {

  public LoginDialog() {
    super(Main.frame, true);
    setSize(400, 300);
    setLocationRelativeTo(Main.frame);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    add(new JLabel(tr("User") + ":"));
    JTextField userText = new JTextField(20);
    userText.setMaximumSize( userText.getPreferredSize() );
    add(userText);
    add(new JLabel(tr("Password") + ":"));
  }
}
