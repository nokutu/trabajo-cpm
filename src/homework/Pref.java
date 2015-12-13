package homework;

import java.util.prefs.Preferences;

/**
 * Facade of Java Preferences API.
 * <p/>
 * See <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/preferences/overview.html">Pref API Overvies</a>
 */
public class Pref {

  private Preferences prefs;

  public Pref() {
    prefs = Preferences.userNodeForPackage(Main.class);
  }

  public void putInt(String key, int value) {
    prefs.putInt(key, value);
  }

  public int getInt(String key, int def) {
    return prefs.getInt(key, def);
  }

  public void put(String key, String value) {
    prefs.put(key, value);
  }

  public String get(String key, String def) {
    return prefs.get(key, def);
  }
}
