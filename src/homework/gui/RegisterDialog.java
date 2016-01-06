package homework.gui;

import homework.Main;
import homework.Utils;
import homework.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import static homework.I18n.tr;

/**
 * Dialog that asks the user for his personal information in order to register him in the application.
 */
public class RegisterDialog extends JDialog {

  private JTextField username;
  private JPasswordField password;
  private JPasswordField password2;
  private JTextField tlfNumber;
  private JTextField id;
  private JTextField email;
  private JTextField address;
  private JTextField fullname;

  public RegisterDialog() {
    super(Main.frame, true);
    setTitle(tr("Register"));
    setSize(400, 300);

    JPanel panel = new JPanel(new MigLayout("alignx left"));

    JLabel usernameLabel = new JLabel(tr("Username") + ":");
    username = new JTextField(20);
    usernameLabel.setLabelFor(username);
    usernameLabel.setDisplayedMnemonic('u');
    panel.add(usernameLabel);
    panel.add(username, "wrap");

    JLabel passwordLabel = new JLabel(tr("Password") + ":");
    password = new JPasswordField(20);
    passwordLabel.setLabelFor(password);
    passwordLabel.setDisplayedMnemonic('p');
    panel.add(passwordLabel);
    panel.add(password, "wrap");

    JLabel password2Label = new JLabel(tr("Repeat password") + ":");
    password2 = new JPasswordField(20);
    password2Label.setLabelFor(password2);
    password2Label.setDisplayedMnemonic('r');
    panel.add(password2Label);
    panel.add(password2, "wrap");

    JLabel fullnameLabel = new JLabel(tr("Full name") + ":");
    fullname = new JTextField(20);
    fullnameLabel.setLabelFor(fullname);
    fullnameLabel.setDisplayedMnemonic('n');
    panel.add(fullnameLabel);
    panel.add(fullname, "wrap");

    JLabel emailLabel = new JLabel(tr("Email") + ":");
    email = new JTextField(20);
    emailLabel.setLabelFor(email);
    emailLabel.setDisplayedMnemonic('e');
    panel.add(emailLabel);
    panel.add(email, "wrap");

    JLabel tlfNumberLabel = new JLabel(tr("Telephone number") + ":");
    tlfNumber = new JTextField(20);
    tlfNumberLabel.setLabelFor(tlfNumber);
    tlfNumberLabel.setDisplayedMnemonic('t');
    panel.add(tlfNumberLabel);
    panel.add(tlfNumber, "wrap");

    JLabel idLabel = new JLabel(tr("ID") + ":");
    panel.add(idLabel);
    id = new JTextField(20);
    idLabel.setLabelFor(id);
    idLabel.setDisplayedMnemonic('i');
    panel.add(id, "wrap");

    JLabel addressLabel = new JLabel(tr("Address") + ":");
    address = new JTextField(20);
    addressLabel.setLabelFor(address);
    addressLabel.setDisplayedMnemonic('d');
    panel.add(addressLabel);
    panel.add(address, "wrap");

    add(panel, BorderLayout.CENTER);

    JPanel bp = new JPanel();
    JButton btnRegister = new JButton(tr("Register"));
    btnRegister.setMnemonic('e');
    btnRegister.addActionListener(new RegisterAction());
    JButton btnCancel = new JButton(tr("Cancel"));
    btnCancel.setMnemonic('c');
    btnCancel.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    bp.add(btnRegister);
    bp.add(btnCancel);

    add(bp, BorderLayout.SOUTH);
    getRootPane().setDefaultButton(btnRegister);

    pack();
    setResizable(false);
    setLocationRelativeTo(Main.frame);
  }

  public class RegisterAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      resetBorders();

      if (Utils.checkFields(username, password, password2, fullname, tlfNumber, address, id, email)) {
        User.register(username.getText(), new String(password.getPassword()), fullname.getText(), tlfNumber.getText(), address.getText(), id.getText(), email.getText());
        JOptionPane.showInputDialog(RegisterDialog.this, tr("Successfully registered"));
        dispose();
      }
    }

    private void resetBorders() {
      Border def = new JButton().getBorder();
      username.setBorder(def);
      password.setBorder(def);
      password2.setBorder(def);
      tlfNumber.setBorder(def);
      id.setBorder(def);
      email.setBorder(def);
      address.setBorder(def);
    }
  }
}
