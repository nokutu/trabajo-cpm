package homework.gui.components;

import javax.swing.JButton;

/**
 * JButton without background or borders that just displays some text.
 */
public class TextButton extends JButton {

  public TextButton(String text) {
    super(text);
    setBorder(null);
    setOpaque(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
    setRolloverEnabled(false);
  }
}
