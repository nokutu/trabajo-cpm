package homework;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Facade for the internationalization methods
 */
public class I18n {

  private final static String BUNDLE_PATH = "data/i18n/bundle";

  private static I18n instance;

  private ResourceBundle bundle;
  private MessageFormat formatter;

  private I18n() {
    bundle = ResourceBundle.getBundle(BUNDLE_PATH, Locale.getDefault());
    formatter = new MessageFormat("");
    formatter.setLocale(Locale.getDefault());
  }

  private static I18n getInstance() {
    if (instance == null) {
      instance = new I18n();
    }
    return instance;
  }

  /**
   * Translates a String.
   *
   * @param key the key pointing to the String
   * @return the translated string
   */
  public static String tr(String key) {
    return get(key);
  }

  /**
   * Translates a String with formatting.
   *
   * @param key       the key pointing to the String
   * @param arguments the values that will be used in the formatting
   * @return the formatted String
   */
  public static String trc(String key, Object[] arguments) {
    applyPattern(key);
    return format(arguments);
  }

  /**
   * Translates a plural.
   *
   * @param key    the key pointing to the String
   * @param amount the amount to calculate, it will be plural if amount >= 2
   * @return the translated plural String
   */
  public static String trn(String key, double amount) {
    if (amount != 1) {
      key += "_plural";
    }
    return get(key);
  }

  public static void setLocale(Locale locale) {
    Locale.setDefault(locale);
    getInstance().formatter.setLocale(locale);
    getInstance().bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
  }

  private static String get(String key) {
    return getInstance().bundle.getString(key);
  }

  private static String format(Object[] arguments) {
    return getInstance().formatter.format(arguments);
  }

  private static void applyPattern(String pattern) {
    getInstance().formatter.applyPattern(get(pattern));
  }

  public static void clear() {
    instance = null;
  }

}
