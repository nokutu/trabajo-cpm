package homework.models;

import homework.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents the rute the cruise is going to take.
 */
public class Rute {

  private List<City> cities;

  public Rute(String rute) {
    cities = new ArrayList<>();
    String[] citiesArray = rute.split("-");
    for (String name : citiesArray) {
      cities.add(Main.db.getCity(name));
    }

  }

  @Override
  public String toString() {
    String ret = cities.get(0).toString();
    for (int i = 1; i < cities.size(); i++) {
      ret += " - " + cities.get(i).toString();
    }
    return ret;
  }
}
