package homework;

import homework.models.City;
import homework.models.Cruise;
import homework.models.Extra;
import homework.models.Order;
import homework.models.Rute;
import homework.models.Ship;
import homework.models.User;
import homework.models.Zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Database used to store file information.
 */
public class Database {

  private HashMap<String, Cruise> cruises;
  private HashMap<String, Ship> ships;
  private HashMap<String, Zone> zones;
  private HashMap<String, City> cities;
  private HashMap<String, Extra> extras;
  private HashSet<User> users;
  private HashSet<Order> orders;


  public Database() {
    cruises = new HashMap<>();
    ships = new HashMap<>();
    zones = new HashMap<>();
    cities = new HashMap<>();
    extras = new HashMap<>();
    users = new HashSet<>();
    orders = new HashSet<>();
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
              parse[6].equals("S"), Integer.parseInt(parse[7]), parse[8], ships.get(parse[9])
      ));
    }
    generateOffers(2);
  }

  private void generateOffers(int amount) {
    List<Cruise> cruiseList = new ArrayList<>(cruises.values());
    cruises.values();
    for (int i = 0; i < amount; i++) {
      int n = new Random().nextInt(cruiseList.size());
      cruiseList.get(n).setOffer(0.15f);
      cruiseList.remove(n);
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

  public HashSet<Order> getOrders() {
    return orders;
  }

  public void addOrder(Order order) {
    orders.add(order);
    int i = Main.prefs.getInt("order.amount", 0);
    Main.prefs.putInt("orders.amount", i + 1);
    Main.prefs.put("orders." + i, order.toString());
  }

  public City getCity(String city) {
    if (cities.get(city) == null) {
      cities.put(city, new City(city));
    }
    return cities.get(city);
  }

  public void readOrders() {
    for (int i = 0; i < Main.prefs.getInt("orders.amount", 0); i++) {
      orders.add(new Order(Main.prefs.get("orders." + i, "")));
    }
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

  public User getUser(String username) {
    for (User user : users) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  public void loadUsers() {
    String userString = Main.prefs.get("users", "");
    if (!userString.equals("")) {
      for (String username : userString.split("%")) {
        User user = new User(
                username,
                Main.prefs.get("user." + username + ".password", ""),
                Main.prefs.get("user." + username + ".fullname", ""),
                Main.prefs.get("user." + username + ".tlf", ""),
                Main.prefs.get("user." + username + ".address", ""),
                Main.prefs.get("user." + username + ".nif", ""),
                Main.prefs.get("user." + username + ".email", "")
        );
        users.add(user);
      }
    }
  }
}
