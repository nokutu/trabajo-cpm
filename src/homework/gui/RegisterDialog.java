package homework.gui;

import homework.Main;

import javax.swing.*;
import java.awt.*;

import static homework.I18n.tr;

/**
 * Created by nokutu on 02/01/2016.
 */
public class RegisterDialog extends JDialog {

  private JTextField username;
  private JPasswordField password;
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
    panel.add(email = new JTextField(20), c);

    c.gridy++;
    panel.add(tlfNumber = new JTextField(20), c);

    c.gridy++;
    panel.add(nif = new JTextField(20), c);

    c.gridy++;
    panel.add(address = new JTextField(20), c);


    add(panel, BorderLayout.CENTER);

    pack();
    setResizable(false);
    setLocationRelativeTo(Main.frame);
  }
}
