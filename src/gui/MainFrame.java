package gui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

  private static final long serialVersionUID = 2740437090361841747L;
  
  public MainFrame() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  public void start() {
    setContentPane(new InitialPanel());
    setVisible(true);
  }

}
