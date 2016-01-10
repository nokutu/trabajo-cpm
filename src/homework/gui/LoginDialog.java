package homework.gui;

import homework.Main;
import homework.models.User;
import net.miginfocom.swing.MigLayout;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
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

    JPanel panel = new JPanel(new MigLayout());

    lbUsername = new JLabel(tr("Username") + ": ");
    tfUsername = new JTextField(20);
    tfUsername.setText(Main.prefs.get("login.last-username", ""));
    lbPassword = new JLabel(tr("Password") + ": ");
    pfPassword = new JPasswordField(20);

    lbUsername.setLabelFor(tfUsername);
    lbUsername.setDisplayedMnemonic('u');
    lbPassword.setLabelFor(pfPassword);
    lbPassword.setDisplayedMnemonic('p');

    panel.add(lbUsername);
    panel.add(tfUsername, "growx, wrap");

    panel.add(lbPassword);
    panel.add(pfPassword, "growx, wrap");

    btnLogin = new JButton(tr("Login"));
    btnLogin.setMnemonic('l');
    btnLogin.addActionListener(new LoginButtonAction());

    btnCancel = new JButton(tr("Cancel"));
    btnCancel.setMnemonic('c');
    btnCancel.addActionListener(new CancelButtonAction());

    JPanel bp = new JPanel();
    bp.add(btnLogin);
    bp.add(btnCancel);

    getContentPane().add(panel, BorderLayout.CENTER);
    getContentPane().add(bp, BorderLayout.SOUTH);
    getRootPane().setDefaultButton(btnLogin);

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
          if (passwordEncryptor.checkPassword(new String(pfPassword.getPassword()), u.getPasswordHash())) {
            User.setLoggedUser(u);
            Main.log.i("Logged in as: " + u.getUsername());
            dispose();
            return;
          } else {
            tfUsername.setBorder(new LineBorder(Color.red));
            pfPassword.setBorder(new LineBorder(Color.red));
            Main.log.i("Invalid login credentials");
            JOptionPane.showMessageDialog(LoginDialog.this, tr("Invalid username or password"), tr("Login error"), JOptionPane.PLAIN_MESSAGE);
          }
        }
      }
      if (!correctUsername) {
        tfUsername.setBorder(new LineBorder(Color.red));
        pfPassword.setBorder(new LineBorder(Color.red));
        Main.log.i("Invalid login credentials");
        JOptionPane.showMessageDialog(LoginDialog.this, tr("Invalid username or password"), tr("Login error"), JOptionPane.PLAIN_MESSAGE);
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
