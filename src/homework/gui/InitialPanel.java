package homework.gui;

import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel {

  private static final long serialVersionUID = 3817197481081978522L;

  public InitialPanel() {
    setLayout(new BorderLayout());
    add(new Navbar(), BorderLayout.NORTH);
  }

}
