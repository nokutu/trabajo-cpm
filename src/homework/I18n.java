package homework;

import com.sun.org.apache.xerces.internal.util.MessageFormatter;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Facade for the internationalization methods
 */
public class I18n {

  private static I18n instance;

  private ResourceBundle bundle;
  private MessageFormat formatter;

  private I18n() {
    bundle = ResourceBundle.getBundle("i18n", Locale.getDefault());
    formatter = new MessageFormat("");
    formatter.setLocale(Locale.getDefault());
  }

  private static I18n getInstance() {
    if (instance == null) {
      instance = new I18n();
    }
    return instance;
  }

  public static String tr(String key) {
    return getInstance().bundle.getString(key);
  }

  public static String trc(String key, Object[] arguments) {
    getInstance().formatter.applyPattern(getInstance().bundle.getString(key));
    return getInstance().formatter.format(arguments);
  }

  public static String trn(String key) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
