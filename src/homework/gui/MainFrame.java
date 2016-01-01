package homework.gui;

import javax.swing.*;
import java.awt.*;

import static homework.I18n.tr;

public class MainFrame extends JFrame {

  private static final long serialVersionUID = 2740437090361841747L;

  public static final String INITIAL_PANEL = "Initial panel";
  public static final String SEARCH_PANEL = "Search panel";
  public static final String CRUISE_PANEL = "Cruise panel";

  public CardLayout cl;

  public InitialPanel ip;
  public SearchPanel sp;
  public CruisePanel cp;

  public MainFrame() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(800, 600));
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(800, 600));
    setTitle(tr("title"));
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
    setVisible(true);
  }

}
