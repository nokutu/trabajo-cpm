package homework.gui;

import homework.Main;
import homework.Utils;
import homework.gui.components.HomeLogo;
import homework.gui.components.SearchBar;
import homework.models.User;
import net.miginfocom.swing.MigLayout;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.JButton;
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
 * In this panel the logged user is allowed to change its personal information.
 */
public class ProfilePanel extends JPanel {

  private final JTextField username;
  private final JPasswordField password;
  private final JPasswordField password2;
  private final JTextField fullname;
  private final JTextField email;
  private final JTextField tlfNumber;
  private final JTextField id;
  private final JTextField address;

  private SearchBar sb;

  public ProfilePanel() {
    setLayout(new BorderLayout());
    add(new HomeLogo(), BorderLayout.NORTH);

    JPanel center = new JPanel();
    add(center, BorderLayout.CENTER);
    center.setLayout(new MigLayout());

    JLabel usernameLabel = new JLabel(tr("Username") + ":");
    username = new JTextField(20);
    username.setEditable(false);
    usernameLabel.setLabelFor(username);
    usernameLabel.setDisplayedMnemonic('u');
    center.add(usernameLabel);
    center.add(username, "wrap");

    JLabel passwordLabel = new JLabel(tr("New password") + ":");
    password = new JPasswordField(20);
    passwordLabel.setLabelFor(password);
    passwordLabel.setDisplayedMnemonic('p');
    center.add(passwordLabel);
    center.add(password, "wrap");

    JLabel password2Label = new JLabel(tr("Repeat new password") + ":");
    password2 = new JPasswordField(20);
    password2Label.setLabelFor(password2);
    password2Label.setDisplayedMnemonic('r');
    center.add(password2Label);
    center.add(password2, "wrap");

    JLabel fullnameLabel = new JLabel(tr("Full name") + ":");
    fullname = new JTextField(20);
    fullnameLabel.setLabelFor(fullname);
    fullnameLabel.setDisplayedMnemonic('n');
    center.add(fullnameLabel);
    center.add(fullname, "wrap");

    JLabel emailLabel = new JLabel(tr("Email") + ":");
    email = new JTextField(20);
    emailLabel.setLabelFor(email);
    emailLabel.setDisplayedMnemonic('e');
    center.add(emailLabel);
    center.add(email, "wrap");

    JLabel tlfNumberLabel = new JLabel(tr("Telephone number") + ":");
    tlfNumber = new JTextField(20);
    tlfNumberLabel.setLabelFor(tlfNumber);
    tlfNumberLabel.setDisplayedMnemonic('t');
    center.add(tlfNumberLabel);
    center.add(tlfNumber, "wrap");

    JLabel idLabel = new JLabel(tr("ID") + ":");
    center.add(idLabel);
    id = new JTextField(20);
    idLabel.setLabelFor(id);
    idLabel.setDisplayedMnemonic('i');
    center.add(id, "wrap");

    JLabel addressLabel = new JLabel(tr("Address") + ":");
    address = new JTextField(20);
    addressLabel.setLabelFor(address);
    addressLabel.setDisplayedMnemonic('d');
    center.add(addressLabel);
    center.add(address, "wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());
    btnPanel.add(new JPanel(), "pushx");

    JButton save = new JButton("Save");
    save.setMnemonic('s');
    save.addActionListener(new SaveAction());
    btnPanel.add(save);

    JButton cancel = new JButton("Cancel");
    cancel.setMnemonic('c');
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.INITIAL_PANEL);
      }
    });
    btnPanel.add(cancel);
    add(btnPanel, BorderLayout.SOUTH);
  }

  public void setUser(User user) {
    username.setText(user.getUsername());
    fullname.setText(user.getFullName());
    email.setText(user.getEmail());
    tlfNumber.setText(user.getTlfNumber());
    id.setText(user.getId());
    address.setText(user.getAddress());
  }

  private class SaveAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      password.setBorder(new JPasswordField().getBorder());
      password2.setBorder(new JPasswordField().getBorder());

      User user = User.getLoggedUser();
      if (!fullname.getText().equals(user.getFullName()) || !address.getText().equals(user.getAddress()) ||
              !id.getText().equals(user.getId()) || !tlfNumber.getText().equals(user.getTlfNumber()) ||
              !email.getText().equals(user.getEmail()) || (new String(password.getPassword()).equals(new String(password2.getPassword())) && password.getPassword().length > 3)) {
        if (Utils.checkFields(null, null, null, fullname, tlfNumber, address, id, email)) {
          while (true) {
            JPanel panel = new JPanel();
            JLabel label = new JLabel(tr("Enter your password to confirm changes") + ":");
            JPasswordField pass = new JPasswordField(10);
            panel.add(label);
            panel.add(pass);
            String[] options = new String[]{"OK", "Cancel"};
            int option = JOptionPane.showOptionDialog(Main.frame, panel, tr("Confirm password"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            if (option == 0) {
              if (new StrongPasswordEncryptor().checkPassword(new String(pass.getPassword()), user.getPasswordHash())) {
                user.setFullName(fullname.getText());
                user.setAddress(address.getText());
                user.setEmail(email.getText());
                user.setId(id.getText());
                user.setTlfNumber(tlfNumber.getText());
                if (new String(password.getPassword()).equals(new String(password2.getPassword())) && password.getPassword().length > 3) {
                  user.setPasswordHash(new StrongPasswordEncryptor().encryptPassword(new String(pass.getPassword())));
                }
                user.writeToFile();
              } else {
                continue;
              }
            }
            break;
          }
        }
      } else {
        if (!new String(password.getPassword()).equals(new String(password2.getPassword()))) {
          password.setBorder(new LineBorder(Color.red));
          password2.setBorder(new LineBorder(Color.red));
        }
      }
    }
  }
}
