package homework.gui;

import homework.Main;
import homework.gui.menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static homework.I18n.tr;

public class MainFrame extends JFrame {

  private static final long serialVersionUID = 2740437090361841747L;

  public static final String INITIAL_PANEL = "Initial panel";
  public static final String SEARCH_PANEL = "Search panel";
  public static final String CRUISE_PANEL = "Cruise panel";
  public static final String PASSENGER_INFO_PANEL = "Passenger info panel";
  public static final String PROFILE_PANEL = "Profile panel";
  public static final String PAYMENT_PANEL = "Payment panel";



  public CardLayout cl;

  public InitialPanel ip;
  public SearchPanel sp;
  public CruisePanel cp;
  public PassengerInfoPanel pip;
  public ProfilePanel pp;
  public PaymentPanel payp;

  public MainFrame() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(800, 600));
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(800, 600));
    setTitle(tr("title"));
    setJMenuBar(new MainMenu());
  }

  public void start() {
    if (cl != null) {
      throw new IllegalStateException("Program has already started");
    }
    setContentPane(new JPanel());
    cl = new CardLayout();
    getContentPane().setLayout(cl);
    getContentPane().add(ip = new InitialPanel(), INITIAL_PANEL);
    getContentPane().add(sp = new SearchPanel(), SEARCH_PANEL);
    getContentPane().add(cp = new CruisePanel(), CRUISE_PANEL);
    getContentPane().add(pip = new PassengerInfoPanel(), PASSENGER_INFO_PANEL);
    getContentPane().add(pp = new ProfilePanel(), PROFILE_PANEL);
    getContentPane().add(payp = new PaymentPanel(), PAYMENT_PANEL);


    setVisible(true);
  }

  public List<JPanel> getPanels() {
    return Arrays.asList(new JPanel[]{ip, sp, cp, pp, pip});
  }

  public void refreshNavbars() {
    for (JPanel p : Main.frame.getPanels()) {
      if (p instanceof HasNavbar) {
        ((HasNavbar) p).getNavbar().refresh();
      }
    }
  }
}
