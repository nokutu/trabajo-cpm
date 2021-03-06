package homework.gui.components;

import homework.Main;
import homework.gui.MainFrame;
import homework.models.Cruise;
import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelListener;

import static homework.I18n.tr;

/**
 * JPanel representing each of the result gotten after performing a search.
 */
public class SearchEntry extends JPanel {

  private JTextArea area;

  public SearchEntry(final Cruise cruise) {
    setLayout(new MigLayout());
    setBorder(BorderFactory.createTitledBorder(cruise.getDenomination()));

    if (cruise.getOffer() != 0) {
      JLabel offer = new JLabel(tr("Special offer (-15%)"));
      offer.setForeground(Color.red);
      add(offer, "alignx center, wrap");
    }

    JLabel rute = new JLabel(cruise.getRute().toString());
    add(rute, "alignx center");

    JButton view = new JButton(tr("View"));
    view.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.cp.setCruise(cruise);
        Main.frame.show(MainFrame.CRUISE_PANEL);
      }
    });
    view.setToolTipText(tr("Show more information about this cruise"));
    add(view, "spany 2, growy, wrap");

    area = new JTextArea();
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setText(cruise.getDescription());
    area.setFocusable(false);
    area.setEditable(false);
    JScrollPane container = new JScrollPane(area);
    container.setBorder(null);
    for (MouseWheelListener mwl : container.getMouseWheelListeners()) {
      container.removeMouseWheelListener(mwl);
    }
    add(container, "growx, pushx");
  }
}
