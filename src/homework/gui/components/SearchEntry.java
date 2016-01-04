package homework.gui.components;

import homework.Main;
import homework.gui.MainFrame;
import homework.models.Cruise;
import net.miginfocom.swing.MigLayout;

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
    setLayout(new MigLayout());
    setBorder(BorderFactory.createTitledBorder(cruise.getZone().toString()));

    JLabel rute = new JLabel(cruise.getRute().toString());
    add(rute, "alignx center");

    JButton view = new JButton("View");
    view.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.cp.setCruise(cruise);
        Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.CRUISE_PANEL);
      }
    });
    add(view, "spany 2, growy, wrap");

    area = new JTextArea();
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setText(cruise.getDescription());
    area.setFocusable(false);
    area.setEditable(false);
    area.setBackground(new JPanel().getBackground());
    JScrollPane container = new JScrollPane(area);
    container.setBorder(null);
    add(container, "growx, pushx");
  }
}
