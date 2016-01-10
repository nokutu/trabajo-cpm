package homework.models;

import homework.Main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an user order.
 */
public class Order {

  private static Order currentOrder;

  private Cruise cruise;
  private CruiseDate date;
  private List<Cabin> cabins = new ArrayList<>();
  private User user;
  private List<Integer> people = new ArrayList<>();
  private List<Extra[]> extras = new ArrayList<>();

  private Order(Cruise cruise, CruiseDate date) {
    setCruise(cruise);
    setDate(date);
    setUser(user);
  }

  public Order(String parse) {
    String[] array = parse.split("%");
    setCruise(Main.db.getCruises().get(array[0]));
    try {
      setDate(new CruiseDate(array[1]));
    } catch (ParseException e) {
      Main.log.e(e);
    }
    setUser(Main.db.getUser(array[2]));
    String[] cabins = array[3].split(",");
    for (String cabinName : cabins) {
      Cabin c = new Cabin(cabinName);
      this.cabins.add(c);
      setCabinBooked(c);
    }
    String[] extrasArrays = array[4].split(",");
    for (String extrasArray : extrasArrays) {
      List<Extra> extras = new ArrayList<>();
      String[] extrasString = extrasArray.split("-");
      for (String elem : extrasString) {
        extras.add(Main.db.getExtras().get(elem));
      }
      this.extras.add(extras.toArray(new Extra[extras.size()]));
    }
  }

  public static Order createOrder(Cruise cruise, CruiseDate date) {
    if (currentOrder != null) {
      currentOrder.destroy();
    }
    currentOrder = new Order(cruise, date);
    return currentOrder;
  }

  @Override
  public String toString() {
    String ret = cruise.getCode() + "%" + date.toString() + "%" + user.getUsername() + "%";
    for (Cabin c : cabins) {
      ret += "," + c.name;
    }
    ret += "%";
    for (Extra[] earray : extras) {
      ret += ",";
      for (Extra e : earray) {
        ret += "-" + e.getCode();
      }
    }
    ret += "%";
    return ret;
  }

  /**
   * Sets a cabin as booked regarding amount left on the ship.
   *
   * @param cabin the cabin booked
   */
  private void setCabinBooked(Cabin cabin) {
    switch (cabin.name) {
      case Cruise.INTERIOR_DOUBLE:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]++;
        break;
      case Cruise.EXTERIOR_DOUBLE:
        cruise.exteriorDoubleBooked[cruise.getDates().indexOf(date)]++;
        break;
      case Cruise.INTERIOR_FAMILY:
        cruise.interiorFamilyBooked[cruise.getDates().indexOf(date)]++;
        break;
      case Cruise.EXTERIOR_FAMILY:
        cruise.exteriorFamilyBooked[cruise.getDates().indexOf(date)]++;
        break;
    }
    if (Main.frame != null) {
      Main.frame.cp.getBookPanel().refreshCabins();
    }
  }

  /**
   * Destroys the order.
   */
  public void destroy() {
    for (Cabin cabin : cabins) {
      switch (cabin.getName()) {
        case Cruise.INTERIOR_DOUBLE:
          cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
          break;
        case Cruise.EXTERIOR_DOUBLE:
          cruise.exteriorDoubleBooked[cruise.getDates().indexOf(date)]--;
          break;
        case Cruise.INTERIOR_FAMILY:
          cruise.interiorFamilyBooked[cruise.getDates().indexOf(date)]--;
          break;
        case Cruise.EXTERIOR_FAMILY:
          cruise.exteriorFamilyBooked[cruise.getDates().indexOf(date)]--;
          break;
      }
    }
    cabins.clear();
    Main.frame.cp.getBookPanel().refreshCabins();
  }

  /**
   * Adds a new cabin into the order.
   *
   * @param cabin  the cabin itself
   * @param people the amount of people that go in the cabin
   * @param extras a List of the extras selected
   */
  public void addCabin(Cabin cabin, int people, List<Extra> extras) {
    cabins.add(cabin);
    setCabinBooked(cabin);

    Extra[] extrasArray = new Extra[extras.size()];
    int i = 0;
    for (Extra e : extras) {
      extrasArray[i++] = e;
    }
    this.people.add(people);
    this.extras.add(extrasArray);
    Main.frame.cp.getBookPanel().refreshCabins();
  }

  /**
   * Returns whether cabin in position n has an extra bed or not.
   *
   * @param n the position of the cabin
   * @return true if the cabin has an extra bed; false otherwise
   */
  public boolean hasExtraBed(int n) {
    for (Extra e : extras.get(n)) {
      if (e.isSuplementaryBed()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes the cabin in position i.
   *
   * @param i the position of the cabin to be removed
   */
  public void remove(int i) {
    switch (cabins.get(i).getName()) {
      case Cruise.INTERIOR_DOUBLE:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
        break;
      case Cruise.EXTERIOR_DOUBLE:
        cruise.exteriorDoubleBooked[cruise.getDates().indexOf(date)]--;
        break;
      case Cruise.INTERIOR_FAMILY:
        cruise.interiorFamilyBooked[cruise.getDates().indexOf(date)]--;
        break;
      case Cruise.EXTERIOR_FAMILY:
        cruise.exteriorFamilyBooked[cruise.getDates().indexOf(date)]--;
        break;
    }
    cabins.remove(i);
    people.remove(i);
  }

  /**
   * Returns the total price of all the cabins in the order.
   *
   * @return the total price of all the cabins in the order
   */
  public int getPrice() {
    return (int) ((getPriceCabin() + getPriceExtras()) * (1 - getOffer()));
  }

  /**
   * Gets the total price of the cabin in position i.
   *
   * @param i The position of the cabin
   * @return the total price of the cabin in position i.
   */
  public int getPrice(int i) {
    return (int) ((getPriceCabin(i) + getPriceExtras(i)) * (1 - getOffer()));
  }

  /**
   * Gets the total price of the extras of all the cabins.
   *
   * @return the total price of the extras of all the cabins
   */
  public int getPriceExtras() {
    int price = 0;
    for (int i = 0; i < extras.size(); i++) {
      price += getPriceExtras(i);
    }
    return price;
  }

  /**
   * Gets the price of the extras of the cabin in position n.
   *
   * @param n the position of the cabin
   * @return the price of the extras of the cabin in position n
   */
  public int getPriceExtras(int n) {
    int priceExtras = 0;
    for (int i = 0; i < extras.get(n).length; i++) {
      priceExtras += extras.get(n)[i].getTotalPrice(people.get(n), cruise.getDuration());
    }
    return priceExtras;
  }

  /**
   * Gets the price of all cabins.
   *
   * @return the price of all the cabins in the order
   */
  public int getPriceCabin() {
    int price = 0;
    for (int i = 0; i < cabins.size(); i++) {
      price += getPriceCabin(i);
    }
    return price;
  }

  /**
   * Gets the price of the cabin in position n
   *
   * @param n the cabin number
   * @return an int representing the price of the cabin
   */
  public int getPriceCabin(int n) {
    return cabins.get(n).getPrice() * cabins.get(n).getCapacity() * cruise.getDuration();
  }

  public float getOffer() {
    return cruise.getOffer();
  }

  public List<Extra[]> getExtras() {
    return extras;
  }

  public Cruise getCruise() {
    return cruise;
  }

  public void setCruise(Cruise cruise) {
    this.cruise = cruise;
  }

  public CruiseDate getCruiseDate() {
    return date;
  }

  public void setDate(CruiseDate date) {
    this.date = date;
  }

  public List<Cabin> getCabins() {
    return cabins;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Integer> getPeople() {
    return people;
  }

  public void setPeople(List<Integer> people) {
    this.people = people;
  }

  public int size() {
    return cabins.size();
  }

}
