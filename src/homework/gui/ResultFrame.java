package homework.gui;

import homework.models.Cruise;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel representing each of the result gotten after performing a search.
 */
public class ResultFrame extends JPanel {

  public ResultFrame(Cruise cruise) {
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    JLabel zone = new JLabel(cruise.getZone().toString());
    zone.setFont(zone.getFont().deriveFont(15));
  }
}
