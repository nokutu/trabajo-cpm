package homework;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Locale translation tests.
 */
public class I18nTest {

  @Before
  public void setUP() {
    Locale.setDefault(Locale.ENGLISH);
    I18n.setLocale(Locale.ENGLISH);
  }

  @Test
  public void trTest() {
    assertEquals("Cruises", I18n.tr("title"));
    I18n.setLocale(new Locale("ES"));
    assertEquals("Cruceros", I18n.tr("title"));
  }
}
