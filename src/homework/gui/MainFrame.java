package homework.gui;

import javax.swing.*;
import java.awt.*;

import static homework.I18n.tr;

public class MainFrame extends JFrame {

  private static final long serialVersionUID = 2740437090361841747L;

  public MainFrame() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(800, 600));
    setLocationRelativeTo(null);
    setTitle(tr("title"));
  }

  public void start() {
    setContentPane(new InitialPanel());
    setVisible(true);
  }

}
