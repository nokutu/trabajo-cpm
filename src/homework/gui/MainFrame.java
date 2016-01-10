package homework.gui;

import homework.Main;
import homework.gui.menu.MainMenu;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static homework.I18n.tr;

/**
 * Main frame of the application. Stores all the visual components.
 */
public class MainFrame extends JFrame {

  private static final long serialVersionUID = 2740437090361841747L;

  public static final String INITIAL_PANEL = "Initial panel";
  public static final String SEARCH_PANEL = "Search panel";
  public static final String CRUISE_PANEL = "Cruise panel";
  public static final String PASSENGER_INFO_PANEL = "Passenger info panel";
  public static final String PROFILE_PANEL = "Profile panel";
  public static final String PAYMENT_PANEL = "Payment panel";
  public static final String FINAL_PANEL = "Final panel";

  private MainMenu mainMenu;

  private CardLayout cl;

  public InitialPanel ip;
  public SearchPanel sp;
  public CruisePanel cp;
  public PassengerInfoPanel pip;
  public ProfilePanel pp;
  public PaymentPanel payp;
  public FinalPanel fp;

  public boolean started = false;
  public HelpSet hs;
  public HelpBroker hb;

  public MainFrame() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(800, 600));
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(800, 600));
    setTitle(tr("title"));
    setJMenuBar(mainMenu = new MainMenu());
  }

  /**
   * Called when the application starts.
   */
  public void start() {
    if (cl != null) {
      throw new IllegalStateException("Program has already started");
    }
    cargaAyuda();
    setContentPane(new JPanel());
    cl = new CardLayout();
    getContentPane().setLayout(cl);
    getContentPane().add(ip = new InitialPanel(), INITIAL_PANEL);
    getContentPane().add(sp = new SearchPanel(), SEARCH_PANEL);
    getContentPane().add(cp = new CruisePanel(), CRUISE_PANEL);
    getContentPane().add(pip = new PassengerInfoPanel(), PASSENGER_INFO_PANEL);
    getContentPane().add(pp = new ProfilePanel(), PROFILE_PANEL);
    getContentPane().add(payp = new PaymentPanel(), PAYMENT_PANEL);
    getContentPane().add(fp = new FinalPanel(), FINAL_PANEL);

    setVisible(true);
    started = true;
  }

  public List<JPanel> getPanels() {
    return Arrays.asList(ip, sp, cp, pp, pip);
  }

  /**
   * Refreshes all the navigation bars of the application. So they all update when a user logs in or logs out.
   */
  public void refreshNavbars() {
    for (JPanel p : Main.frame.getPanels()) {
      if (p instanceof HasNavbar) {
        ((HasNavbar) p).getNavbar().refresh();
      }
    }
  }

  /**
   * Loads JavaHelp help support.
   */
  private void cargaAyuda() {
    URL hsURL;

    try {
      File fichero = new File("help/" + Locale.getDefault().getLanguage() + "/Ayuda.hs");
      hsURL = fichero.toURI().toURL();
      hs = new HelpSet(null, hsURL);
    } catch (Exception e) {
      System.out.println("Ayuda no encontrada");
      return;
    }

    hb = hs.createHelpBroker();
    hb.initPresentation();

    hb.enableHelpKey(getRootPane(), "intro", hs);
    hb.enableHelpOnButton(mainMenu.getHelpButton(), "intro", hs);
  }

  /**
   * Shows a panel contained in the CardLayout
   *
   * @param panel the String name of the panel to be shown
   */
  public void show(String panel) {
    cl.show(getContentPane(), panel);
    switch (panel) {
      case INITIAL_PANEL:
        hb.enableHelpKey(getRootPane(), "intro", hs);
        break;
      case SEARCH_PANEL:
        hb.enableHelpKey(getRootPane(), "search", hs);
        break;
      case CRUISE_PANEL:
        hb.enableHelpKey(getRootPane(), "cruise", hs);
        break;
      case PASSENGER_INFO_PANEL:
        hb.enableHelpKey(getRootPane(), "passengerInfo", hs);
        break;
      case PROFILE_PANEL:
        hb.enableHelpKey(getRootPane(), "user", hs);
        break;
      case PAYMENT_PANEL:
        hb.enableHelpKey(getRootPane(), "payment", hs);
        break;
      case FINAL_PANEL:
        hb.enableHelpKey(getRootPane(), "final", hs);
        break;
    }
    Main.log.i("Changed panel to: " + panel);
  }
}
