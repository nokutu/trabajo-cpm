package homework.gui;

import homework.Main;
import homework.models.Cruise;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nokutu on 01/01/2016.
 */
public class CruisePanel extends JPanel {

  public ScrollablePanel centerPanel;
  public JScrollPane scroll;

  public JTextArea description;
  public JLabel rute;
  public JLabel zone;
  public JLabel duration;
  public JLabel denomination;

  public CruisePanel() {
    setLayout(new BorderLayout());
    add(new SearchBar(), BorderLayout.NORTH);

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
    getCenterPanel().add(denomination, c);

    c.gridy = 1;
    getCenterPanel().add(zone, c);

    c.gridy = 2;
    getCenterPanel().add(rute, c);

    c.gridy = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 1;
    getCenterPanel().add(description, c);

    add(getCenterPanel(), BorderLayout.CENTER);
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
    duration.setText("" + cruise.getDuration());

    revalidate();
    repaint();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Main.frame.cp.scroll.getViewport().setViewPosition(new Point(0, 0));
      }
    });
  }
}
