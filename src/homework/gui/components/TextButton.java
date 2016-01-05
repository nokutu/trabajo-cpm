package homework.gui.components;

import javax.swing.JButton;

/**
 * Created by nokutu on 04/01/2016.
 */
public class TextButton extends JButton {

  public TextButton(String text) {
    super(text);
    setBorder(null);
    setOpaque(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
  }
}
