package homework;


import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages user preferences by storing them on a file.
 */
public class Preferences {

  private File file;
  private Map<String, String> map;


  public Preferences() throws IOException {
    file = new File(System.getProperty("user.home") + "/.cruises/settings.ini");
    map = new ConcurrentHashMap<>();
    if (file.exists()) {
      readFile();
    } else {
      file.getParentFile().mkdir();
      file.createNewFile();
    }
  }

  public void putInt(String key, int value) {
    map.put(key, Integer.toString(value));
  }

  public int getInt(String key, int def) {
    String val = map.get(key);
    return val == null ? null : Integer.parseInt(val);
  }

  public void put(String key, String value) {
    map.put(key, value);
  }

  public String get(String key, String def) {
    String val = map.get(key);
    return val == null ? def : val;
  }

  public void writeToFile() throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(new FileOutputStream(file));
    for (Map.Entry<String, String> entry : map.entrySet()) {
      writer.println(entry.getKey() + "=" + entry.getValue());
    }
    writer.close();
  }

  private void readFile() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = reader.readLine()) != null) {
      line = line.trim();
      String[] entry = line.split("=");
      if (entry.length == 2) {
        map.put(entry[0].trim(), entry[1].trim());
      }
    }
  }
}
