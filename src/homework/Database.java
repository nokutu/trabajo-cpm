package homework;

import homework.models.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Database used to store file information.
 */
public class Database {

  private HashMap<String, Cruise> cruises;
  private HashMap<String, Ship> ships;
  private HashMap<String, Zone> zones;
  private HashMap<String, City> cities;
  private HashMap<String, Extra> extras;
  private Set<User> users;


  public Database() {
    cruises = new HashMap<>();
    ships = new HashMap<>();
    zones = new HashMap<>();
    cities = new HashMap<>();
    extras = new HashMap<>();
    users = new HashSet();
  }

  public HashMap<String, Extra> getExtras() {
    return extras;
  }

  public void addCruises(List<String[]> list) {
    if (ships.size() == 0) {
      throw new IllegalStateException("Ships must be parsed first");
    }
    for (String[] parse : list) {
      cruises.put(parse[0], new Cruise(
              parse[0], getZone(parse[1]), parse[2], getCity(parse[3]), new Rute(parse[4]), parse[5],
              parse[6].equals("Y") ? true : false, Integer.parseInt(parse[7]), parse[8], ships.get(parse[9])
      ));
    }
  }

  public void addShips(List<String[]> list) {
    for (String[] parse : list) {
      ships.put(parse[0], new Ship(
              parse[0], parse[1], parse[2],
              Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5]), Integer.parseInt(parse[6]),
              Integer.parseInt(parse[7]), Integer.parseInt(parse[8]), Integer.parseInt(parse[9]), Integer.parseInt(parse[10])
      ));
    }
  }

  public void addExtras(List<String[]> list) {
    for (String[] parse : list) {
      extras.put(parse[0], new Extra(
              parse[0], parse[1],
              Integer.parseInt(parse[2])
      ));
    }
  }

  public Zone getZone(String zone) {
    if (zones.get(zone) == null) {
      zones.put(zone, new Zone(zone));
    }
    return zones.get(zone);
  }

  public City getCity(String city) {
    if (cities.get(city) == null) {
      cities.put(city, new City(city));
    }
    return cities.get(city);
  }

  public HashMap<String, Cruise> getCruises() {
    return cruises;
  }

  public HashMap<String, Ship> getShips() {
    return ships;
  }

  public Set<User> getUsers() {
    return users;
  }
}
