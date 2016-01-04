package homework.gui.components;

import homework.Main;
import homework.gui.LoginDialog;
import homework.gui.RegisterDialog;
import homework.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static homework.I18n.tr;

/**
 * Navbar used in most of the application views.
 */
public class Navbar extends JPanel {

  private static Navbar instance;

  public JButton loginButton;
  private JButton registerButton;
  private JButton logoutButton;
  private JButton profileButton;

  public Navbar() {
    super();
    setLayout(new MigLayout("alignx right, aligny top"));
    generate();
  }

  private void generate() {
    if (User.isLogged()) {
      addUserPanel();
    } else {
      addLoginPanel();
    }
  }

  private void addUserPanel() {
    add(getProfileLogoutPanel());
  }

  private void addLoginPanel() {
    add(getLogInRegisterPanel());
  }

  public JButton getLoginButton() {
    if (loginButton == null) {
     loginButton = new TextButton(tr("Login"));
      loginButton.addActionListener(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JDialog d = new LoginDialog();
          d.setVisible(true);
          Main.log.i("Login");
        }
      });
    }
    return loginButton;
  }

  public JButton getRegisterButton() {
    if (registerButton == null) {
      registerButton = new TextButton(tr("Register"));
      registerButton.addActionListener(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JDialog d = new RegisterDialog();
          d.setVisible(true);
          Main.log.i("Register");
        }
      });
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

  public void refresh() {
    removeAll();
    generate();
    revalidate();
    repaint();
  }

  public JPanel getProfileLogoutPanel() {
    JPanel p = new JPanel();
    p.add(getProfileButton());
    p.add(new JLabel("/"));
    p.add(getLogoutButton());
    return p;
  }

  public JButton getProfileButton() {
    if (profileButton == null) {
      profileButton = new JButton(User.getLoggedUser().getUsername());
      profileButton.setBorder(null);
      profileButton.setOpaque(false);
      profileButton.setContentAreaFilled(false);
      profileButton.setBorderPainted(false);
      profileButton.addActionListener(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Main.log.i("Profile");
        }
      });
    }
    return profileButton;
  }

  public JButton getLogoutButton() {
    if (logoutButton == null) {
      logoutButton = new JButton(tr("Logout"));
      logoutButton.setBorder(null);
      logoutButton.setOpaque(false);
      logoutButton.setContentAreaFilled(false);
      logoutButton.setBorderPainted(false);
      logoutButton.addActionListener(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Main.log.i("Logout");
          User.logout();
          Main.frame.refreshNavbars();
        }
      });
    }
    return logoutButton;
  }
}