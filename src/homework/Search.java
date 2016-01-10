package homework;

import homework.models.Cruise;

import java.util.ArrayList;
import java.util.List;

/**
 * Search algorithm.
 */
public class Search {

  private static final int MIN = 5;

  /**
   * Applies the search algorithm.
   *
   * @param text the String written in the search bar
   * @return a List of Cruise ordered depending on how much they much the input String
   */
  public static List<Cruise> search(String text) {
    List<Cruise> ret = new ArrayList<>();
    List<Integer> puntuations = new ArrayList<>();
    String[] search = text.split("\\s+");
    main:
    for (Cruise c : Main.db.getCruises().values()) {
      int puntuation = 0;
      puntuation += count(search, c.getDescription(), 1);
      puntuation += count(search, c.getRute().toString(), 10);
      puntuation += count(search, c.getZone().toString(), 10);
      puntuation += count(search, c.getDenomination(), 12);
      if (c.getOffer() != 0) {
        puntuation += 5;
      }

      if (puntuation < MIN && !text.equals("")) {
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

  /**
   * Checks how many times a set of String are contained in another String and multiplies it by a factor.
   *
   * @param search an array containing the String to be contained
   * @param field  the container String
   * @param factor the factor to multiply by
   * @return the amount of times the array is found in the field multiplied by a factor
   */
  private static int count(String[] search, String field, int factor) {
    int res = 0;
    field = field.toLowerCase();
    for (String s : search) {
      if (s.length() <= 3) {
        continue;
      }
      res += count(s, field) * factor;
    }
    return res;
  }

  /**
   * Counts how many times a String is contained by another String.
   *
   * @param contained the String that is contained
   * @param container the String that contains the other one
   * @return an int representing how many times it is contained
   */
  private static int count(String contained, String container) {
    int res = 0;
    int pos = -1;
    contained = contained.toLowerCase();
    while ((pos = container.indexOf(contained, pos + 1)) != -1) {
      res++;
    }
    return res;
  }
}
