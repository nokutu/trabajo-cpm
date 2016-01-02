package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utilities
 */
public class Utils {

  /**
   * Returns a StackTrace array as a String.
   *
   * @param e the Exception containing the StackTrace
   * @return a String representing the StackTrace
   */
  public static String getStackTrace(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }

  public static ImageIcon scale(ImageIcon image, int width, int height) {

    BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(image.getImage(), 0, 0, width, height, null);
    g2.dispose();

    return new ImageIcon(resizedImg);

  }
}
