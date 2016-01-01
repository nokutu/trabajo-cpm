package homework;

import homework.models.Cruise;

import java.util.ArrayList;
import java.util.List;

/**
 * Search algorithm.
 */
public class Search {

  private static final int MIN = 5;

  public static List<Cruise> search(String text) {
    List<Cruise> ret = new ArrayList<>();
    List<Integer> puntuations = new ArrayList<>();
    String[] search = text.split("\\s+");
    main : for (Cruise c : Main.db.getCruises().values()) {
      int puntuation = 0;
      puntuation += count(search, c.getDescription(), 1);
      puntuation += count(search, c.getRute().toString(), 10);
      puntuation += count(search, c.getZone().toString(), 10);

      if (puntuation < MIN) {
        continue;
      }

      for (int i = 0; i < puntuations.size(); i++) {
        if (puntuations.get(i) < puntuation) {
          ret.add(i, c);
          puntuations.add(i, puntuation);
          continue main;
        }
      }
      ret.add(c);
      puntuations.add(puntuation);
    }
    if (ret.isEmpty()) {
      ret = new ArrayList<>(Main.db.getCruises().values());
    }
    return ret;
  }

  private static int count(String[] search, String field, int multiplicator) {
    int res = 0;
    field = field.toLowerCase();
    for (String s : search) {
      if (s.length() <= 3) {
        continue;
      }
      res += count(s, field) * multiplicator;
    }
    return res;
  }

  private static int count(String small, String container) {
    int res = 0;
    int pos = -1;
    small = small.toLowerCase();
    while ((pos = container.indexOf(small, pos + 1)) != -1) {
      res++;
    }
    return res;
  }
}
