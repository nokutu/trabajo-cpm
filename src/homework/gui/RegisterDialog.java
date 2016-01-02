package homework.gui;

import homework.Main;
import homework.models.User;
import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static homework.I18n.tr;

/**
 * Created by nokutu on 02/01/2016.
 */
public class RegisterDialog extends JDialog {

  private JTextField username;
  private JPasswordField password;
  private JPasswordField password2;
  private JTextField tlfNumber;
  private JTextField nif;
  private JTextField email;
  private JTextField address;

  public RegisterDialog() {
    super(Main.frame, true);
    setTitle(tr("Register"));
    setSize(400, 300);

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.LINE_START;
    panel.add(new JLabel(tr("Username") + ":"), c);

    c.gridy++;
    panel.add(new JLabel(tr("Password") + ":"), c);

    c.gridy++;
    panel.add(new JLabel(tr("Repeat password") + ":"), c);

    c.gridy++;
    panel.add(new JLabel(tr("Email") + ":"), c);

    c.gridy++;
    panel.add(new JLabel(tr("Telephone number") + ":"), c);

    c.gridy++;
    panel.add(new JLabel(tr("ID") + ":"), c);

    c.gridy++;
    panel.add(new JLabel(tr("Address") + ":"), c);

    c.gridx = 1;
    c.gridy = 0;
    panel.add(username = new JTextField(20), c);

    c.gridy++;
    panel.add(password = new JPasswordField(20), c);

    c.gridy++;
    panel.add(password2 = new JPasswordField(20), c);

    c.gridy++;
    panel.add(email = new JTextField(20), c);

    c.gridy++;
    panel.add(tlfNumber = new JTextField(20), c);

    c.gridy++;
    panel.add(nif = new JTextField(20), c);

    c.gridy++;
    panel.add(address = new JTextField(20), c);


    add(panel, BorderLayout.CENTER);

    JPanel bp = new JPanel();
    JButton btnRegister = new JButton(tr("Register"));
    btnRegister.addActionListener(new RegisterAction());
    JButton btnCancel = new JButton(tr("Cancel"));
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

      boolean valid = true;
      if (username.getText().equals("") || Main.db.getUser(username.getText()) != null || username.getText().contains("%")) {
        username.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (!new String(password.getPassword()).equals(new String(password2.getPassword())) || password.getPassword().length < 3) {
        password.setBorder(new LineBorder(Color.red));
        password2.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (tlfNumber.getText().length() < 9 || !tlfNumber.getText().matches("[0-9]+")) {
        tlfNumber.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (address.getText().equals("")) {
        address.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (nif.getText().equals("")) {
        nif.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (!EmailValidator.getInstance().isValid(email.getText())) {
        email.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (valid) {
        User.register(username.getText(), new String(password.getPassword()), tlfNumber.getText(), address.getText(), nif.getText(), email.getText());
        dispose();
      }
    }

    private void resetBorders() {
      Border def = new JButton().getBorder();
      username.setBorder(def);
      password.setBorder(def);
      password2.setBorder(def);
      tlfNumber.setBorder(def);
      nif.setBorder(def);
      email.setBorder(def);
      address.setBorder(def);
    }
  }
}
