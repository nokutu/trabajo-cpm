package homework;

import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 * Utilities
 */
public class Utils {

  /**
   * Date format used in the whole application.
   */
  public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");


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

  /**
   * Rescales an ImageIcon to the given width and height.
   *
   * @param image  the image that is going to be rescaled
   * @param width  the desired width
   * @param height the desired height
   * @return the rescaled ImageIcon
   */
  public static ImageIcon scale(ImageIcon image, int width, int height) {
    BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(image.getImage(), 0, 0, width, height, null);
    g2.dispose();

    return new ImageIcon(resizedImg);
  }

  /**
   * Scales an ImageIcon to a given width maintaining the height proportional to the original ImageIcon.
   *
   * @param image the image to be scaled
   * @param width the desired width
   * @return the rescaled ImageIcon
   */
  public static ImageIcon proportionalScaleWidth(ImageIcon image, int width) {
    return scale(image, width, (int) (image.getIconHeight() / (image.getIconWidth() / (float) width)));
  }

  /**
   * Checks the filds in the register form, so all of them match the needed conditions.
   * <p/>
   * If some field is not used it can be passed as null, and it will be ignored.
   *
   * @param username  username
   * @param password  password
   * @param password2 repeated password
   * @param fullname  full name
   * @param tlfNumber telephone number
   * @param address   address
   * @param id        id
   * @param email     email
   * @return true if the non null ones are correct; false otherwise.
   */
  public static boolean checkFields(JTextField username, JPasswordField password, JPasswordField password2,
                                    JTextField fullname, JTextField tlfNumber, JTextField address, JTextField id,
                                    JTextField email) {
    if (username != null) username.setBorder(new JTextField().getBorder());
    if (password != null) password.setBorder(new JPasswordField().getBorder());
    if (password2 != null) password2.setBorder(new JPasswordField().getBorder());
    if (fullname != null) fullname.setBorder(new JTextField().getBorder());
    if (tlfNumber != null) tlfNumber.setBorder(new JTextField().getBorder());
    if (address != null) address.setBorder(new JTextField().getBorder());
    if (id != null) id.setBorder(new JTextField().getBorder());
    if (email != null) email.setBorder(new JTextField().getBorder());

    boolean valid = true;
    if (username != null && (username.getText().equals("") || Main.db.getUser(username.getText()) != null || username.getText().contains("%"))) {
      username.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    if (password != null && password2 != null && (!new String(password.getPassword()).equals(new String(password2.getPassword())) || password.getPassword().length < 3)) {
      password.setBorder(new LineBorder(Color.red));
      password2.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    if (fullname != null && fullname.getText().equals("")) {
      fullname.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    if (tlfNumber != null && (tlfNumber.getText().length() < 9 || !tlfNumber.getText().matches("[0-9]+"))) {
      tlfNumber.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    if (address != null && address.getText().equals("")) {
      address.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    if (id != null && id.getText().equals("")) {
      id.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    if (email != null && !EmailValidator.getInstance().isValid(email.getText())) {
      email.setBorder(new LineBorder(Color.red));
      valid = false;
    }
    return valid;
  }
}
