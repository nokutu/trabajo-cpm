package homework.gui;

import homework.Main;
import homework.gui.components.BookPanel;
import homework.gui.components.Navbar;
import homework.gui.components.ScrollablePanel;
import homework.gui.components.SearchBar;
import homework.models.Cruise;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseWheelListener;

import static homework.I18n.tr;
import static homework.I18n.trn;


/**
 * In this panel the information about a cruise is shown and it is possible to book cabins.
 */
public class CruisePanel extends JPanel implements HasSearchBar {

  private JLabel cruisePicLabel;
  private JLabel shipPicLabel;

  private ScrollablePanel centerPanel;
  private JScrollPane scroll;

  private SearchBar sb;

  private JTextArea description;
  private JLabel rute;
  private JLabel zone;
  private JLabel duration;
  private JLabel denomination;

  private BookPanel bp;

  public CruisePanel() {
    setLayout(new BorderLayout());
    add(sb = new SearchBar(), BorderLayout.NORTH);

    description = new JTextArea();
    description.setLineWrap(true);
    description.setWrapStyleWord(true);
    description.setEditable(false);
    description.setFocusable(false);
    rute = new JLabel();
    zone = new JLabel();
    duration = new JLabel();
    denomination = new JLabel();

    getCenterPanel().add(denomination, "span 2, alignx center, wrap");

    getCenterPanel().add(zone, "span 2, alignx center, wrap");

    getCenterPanel().add(rute, "span 2, alignx center, wrap");

    getCenterPanel().add(duration, "span 2, alignx center, wrap");

    JScrollPane container = new JScrollPane(description);
    container.setBorder(null);
    for (MouseWheelListener mwl : container.getMouseWheelListeners()) {
      container.removeMouseWheelListener(mwl);
    }

    getCenterPanel().add(container, "aligny top, gaptop 50, growx, pushx");

    getCenterPanel().add(bp = new BookPanel(), "aligny top, spany, wrap");

    cruisePicLabel = new JLabel();
    getCenterPanel().add(cruisePicLabel, "alignx center, wrap");

    shipPicLabel = new JLabel();
    getCenterPanel().add(shipPicLabel, "alignx center");
  }


  private ScrollablePanel getCenterPanel() {
    if (centerPanel == null) {
      centerPanel = new ScrollablePanel();
      centerPanel.setLayout(new MigLayout());
      scroll = new JScrollPane();
      scroll.setViewportView(centerPanel);
      add(scroll, BorderLayout.CENTER);
    }
    return centerPanel;
  }

  /**
   * Sets the cruise whose information is being represented on the panel.
   *
   * @param cruise the cruise whose data is going to be represented
   */
  public void setCruise(Cruise cruise) {
    String descriptionText = "";
    descriptionText += cruise.getDescription() + "\n\n";
    descriptionText += tr("Other data") + "\n------------------------------\n";
    if (cruise.isMinorAllowed()) {
      descriptionText += tr("Minor allowed") + "\n";
    } else {
      descriptionText += tr("Minor not allowed") + "\n";
    }
    descriptionText += tr("Start port") + " " + cruise.getStartPort();
    description.setText(descriptionText);

    denomination.setText(cruise.getDenomination());
    rute.setText(cruise.getRute().toString());
    zone.setText(cruise.getZone().toString());
    duration.setText(cruise.getDuration() + " " + trn("day", cruise.getDuration()));

    bp.setCruise(cruise);

    shipPicLabel.setIcon(cruise.getShip().getImage());
    cruisePicLabel.setIcon(cruise.getImage());

    revalidate();
    repaint();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Main.frame.cp.scroll.getViewport().setViewPosition(new Point(0, 0));
      }
    });
  }

  @Override
  public Navbar getNavbar() {
    return sb.getNavbar();
  }

  @Override
  public SearchBar getSearchBar() {
    return sb;
  }

  public BookPanel getBookPanel() {
    return bp;
  }

}
