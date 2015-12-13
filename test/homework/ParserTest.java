package homework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class ParserTest {

  @Test
  public void parseTest() {
    File f = null;
    PrintWriter w = null;
    try {
      f = new File("temp.txt");
      w = new PrintWriter(f);
      w.println("0;1;2;3;4");
      w.println("0;1;2;3;4");
      for (String[] line : Parser.parse(f)) {
        for (int i = 0; i < 5; i++) {
          assertEquals("" + i, line[i]);
        }
      }
    } catch (IOException e) {
      fail();
    } finally {
      if (w != null) {
        w.close();
      }
      if (f != null) {
        f.delete();
      }
    }
  }
}
