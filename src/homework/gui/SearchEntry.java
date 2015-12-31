package homework.gui;

import homework.Main;
import homework.Utils;
import homework.models.Cruise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * JPanel representing each of the result gotten after performing a search.
 */
public class SearchEntry extends JPanel {

  JTextArea area;

  public SearchEntry(Cruise cruise) {
    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createTitledBorder(cruise.getZone().toString()));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;

    JLabel rute = new JLabel(cruise.getRute().toString());
    add(rute, c);

    c.gridy = 1;
    c.weighty = 1;
    c.weightx = 1;

    area = new JTextArea();
    area.setColumns(30);
    area.setLineWrap(true);
    area.setSize((int) (800 / 1.2), (int) (600 / 1.2));
    area.setWrapStyleWord(true);
    area.setText(cruise.getDescription());
    add(area, c);

    Main.frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        resize();

      }
    });
  }

  private void resize() {
    int width = Main.frame.sp.centerPanel.getWidth();
    int height = Main.frame.sp.centerPanel.getHeight();
    area.setSize((int) (width / 1.2f), (int) (height / 1.2f));
  }
}
