package homework.gui;

import homework.Main;
import homework.models.Cruise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel representing each of the result gotten after performing a search.
 */
public class SearchEntry extends JPanel {

  JTextArea area;

  public SearchEntry(final Cruise cruise) {
    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createTitledBorder(cruise.getZone().toString()));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;

    JLabel rute = new JLabel(cruise.getRute().toString());
    add(rute, c);
    c.fill = GridBagConstraints.BOTH;

    c.gridy = 1;
    c.weightx = 1;

    area = new JTextArea();
    //area.setColumns(30);
    area.setLineWrap(true);
    //area.setSize((int) (800 / 1.2), (int) (600 / 1.2));
    area.setWrapStyleWord(true);
    area.setText(cruise.getDescription());
    add(area, c);

    c.weightx = 0.1;
    c.gridx = 1;
    c.gridy = 0;
    c.gridheight = 2;
    JButton view = new JButton("View");
    view.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.cp.setCruise(cruise);
        Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.CRUISE_PANEL);
      }
    });
    add(view, c);
  }
}
