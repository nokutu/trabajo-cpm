package homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

  public static List<String[]> parse(File file) throws IOException {
    List<String[]> ret = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = reader.readLine()) != null) {
      ret.add(line.trim().split(";"));
    }
    reader.close();
    return ret;
  }
}
