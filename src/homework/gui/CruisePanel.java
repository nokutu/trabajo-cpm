package homework.gui;

import homework.Main;
import homework.models.Cruise;
import homework.models.Extra;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static homework.I18n.trn;


/**
 * Created by nokutu on 01/01/2016.
 */
public class CruisePanel extends JPanel implements HasSearchBar{

  private ScrollablePanel centerPanel;
  private JScrollPane scroll;

  private SearchBar sb;

  private JTextArea description;
  private JLabel rute;
  private JLabel zone;
  private JLabel duration;
  private JLabel denomination;
  private List<JCheckBox> extras = new ArrayList<>();

  public CruisePanel() {
    setLayout(new BorderLayout());
    add(sb = new SearchBar(), BorderLayout.NORTH);

    description = new JTextArea();
    description.setLineWrap(true);
    description.setWrapStyleWord(true);
    rute = new JLabel();
    zone = new JLabel();
    duration = new JLabel();
    denomination = new JLabel();

    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    getCenterPanel().add(denomination, c);

    c.gridy = 1;
    getCenterPanel().add(zone, c);

    c.gridy = 2;
    getCenterPanel().add(rute, c);

    c.gridy = 3;
    getCenterPanel().add(duration, c);

    c.gridwidth = 1;
    c.gridy = 4;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 1;
    c.insets = new Insets(10, 10, 10, 10);
    getCenterPanel().add(description, c);

    c.insets = new Insets(0, 0, 0, 0);
    c.fill = GridBagConstraints.NONE;
    c.gridy = 4;
    c.gridx = 1;
    c.weightx = 0;
    c.weighty = 0;
    c.anchor = GridBagConstraints.PAGE_START;

    getCenterPanel().add(createExtrasPanel(), c);
  }

  private ScrollablePanel createExtrasPanel() {
    ScrollablePanel extrasPanel = new ScrollablePanel();
    extrasPanel.setBorder(BorderFactory.createTitledBorder("Extras"));
    extrasPanel.setLayout(new GridBagLayout());
    GridBagConstraints c2 = new GridBagConstraints();
    c2.gridx = 0;
    c2.gridy = 0;
    c2.anchor = GridBagConstraints.LINE_START;
    for (Extra e : Main.db.getExtras().values()) {
      JCheckBox checkBox = new JCheckBox(e.getName() + " (" + e.getPrice() + " " + e.getUnit() + ")");
      extras.add(checkBox);
      extrasPanel.add(checkBox, c2);
      c2.gridy++;
    }
    return extrasPanel;
  }


  public ScrollablePanel getCenterPanel() {
    if (centerPanel == null) {
      centerPanel = new ScrollablePanel();
      centerPanel.setLayout(new GridBagLayout());
      scroll = new JScrollPane();
      scroll.setViewportView(centerPanel);
      add(scroll, BorderLayout.CENTER);
    }
    return centerPanel;
  }

  public void setCruise(Cruise cruise) {
    description.setText(cruise.getDescription());
    denomination.setText(cruise.getDenomination());
    rute.setText(cruise.getRute().toString());
    zone.setText(cruise.getZone().toString());
    duration.setText(cruise.getDuration() + " " + trn("day", cruise.getDuration()));

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
}
