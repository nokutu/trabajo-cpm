package homework.gui.menu;

import java.util.Locale;

/**
 * Created by nokutu on 05/01/2016.
 */
public class LocaleWrapper implements Comparable<LocaleWrapper> {

  String name;
  Locale locale;

  public LocaleWrapper(String name, String language) {
    locale = new Locale(language);
    this.name = name;
  }

  public Locale getLocale() {
    return locale;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof LocaleWrapper && ((LocaleWrapper) obj).locale.equals(locale);
  }

  @Override
  public int compareTo(LocaleWrapper o) {
    return name.compareTo(o.name);
  }
}
