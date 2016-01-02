package homework.gui;

import homework.Main;
import homework.models.User;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static homework.I18n.tr;

/**
 * Dialog used for login.
 */
public class LoginDialog extends JDialog {

  private final JLabel lbUsername;
  private final JTextField tfUsername;
  private final JLabel lbPassword;
  private final JPasswordField pfPassword;
  private final JButton btnLogin;
  private final JButton btnCancel;

  public LoginDialog() {
    super(Main.frame, true);
    setTitle(tr("Login"));
    setSize(400, 300);

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints cs = new GridBagConstraints();

    cs.fill = GridBagConstraints.HORIZONTAL;

    lbUsername = new JLabel(tr("Username") + ": ");
    cs.gridx = 0;
    cs.gridy = 0;
    cs.gridwidth = 1;
    panel.add(lbUsername, cs);

    tfUsername = new JTextField(20);
    tfUsername.setText(Main.prefs.get("login.last-username", ""));
    cs.gridx = 1;
    cs.gridy = 0;
    cs.gridwidth = 2;
    panel.add(tfUsername, cs);

    lbPassword = new JLabel(tr("Password") + ": ");
    cs.gridx = 0;
    cs.gridy = 1;
    cs.gridwidth = 1;
    panel.add(lbPassword, cs);

    pfPassword = new JPasswordField(20);
    cs.gridx = 1;
    cs.gridy = 1;
    cs.gridwidth = 2;
    panel.add(pfPassword, cs);

    btnLogin = new JButton(tr("Login"));
    btnLogin.addActionListener(new LoginButtonAction());

    btnCancel = new JButton(tr("Cancel"));
    btnCancel.addActionListener(new CancelButtonAction());

    JPanel bp = new JPanel();
    bp.add(btnLogin);
    bp.add(btnCancel);

    getContentPane().add(panel, BorderLayout.CENTER);
    getContentPane().add(bp, BorderLayout.SOUTH);

    pack();
    setResizable(false);
    setLocationRelativeTo(Main.frame);
  }

  private class LoginButtonAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      Main.prefs.put("login.last-username", tfUsername.getText());

      tfUsername.setBorder(new JTextField().getBorder());
      pfPassword.setBorder(new JTextField().getBorder());

      boolean correctUsername = false;
      for (User u : Main.db.getUsers()) {
        if (u.getUsername().equals(tfUsername.getText())) {
          correctUsername = true;
          StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
          if (passwordEncryptor.checkPassword(new String(pfPassword.getPassword()), u.getPasswordHash())){
            User.setLoggedUser(u);
          } else {
            pfPassword.setBorder(new LineBorder(Color.red));
          }
        }
      }
      if (!correctUsername)  {
        tfUsername.setBorder(new LineBorder(Color.red));
      }
    }
  }

  private class CancelButtonAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      LoginDialog.this.dispose();
    }
  }

}
