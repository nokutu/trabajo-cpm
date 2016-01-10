package homework.models;

import homework.Main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nokutu on 07/01/2016.
 */
public class Order {

  private static Order currentOrder;

  private Cruise cruise;
  private CruiseDate date;
  private List<Cabin> cabins = new ArrayList<>();
  private User user;
  private List<Integer> people = new ArrayList<>();
  private List<Extra[]> extras = new ArrayList<>();

  public static Order createOrder(Cruise cruise, CruiseDate date) {
    if (currentOrder != null) {
      currentOrder.destroy();
    }
    currentOrder = new Order(cruise, date);
    return currentOrder;
  }

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

  public void addCabin(Cabin cabin, int people, List<Extra> extras) {
    cabins.add(cabin);
    setCabinBooked(cabin);

    boolean bool = false;
    Extra[] extrasarray = new Extra[extras.size()];
    int i = 0;
    for (Extra e : extras) {
      extrasarray[i++] = e;
    }
    this.people.add(people);
    this.extras.add(extrasarray);
    Main.frame.cp.getBookPanel().refreshCabins();
  }

  public boolean hasExtraBed(int n) {
    for (Extra e : extras.get(n)) {
      if (e.isSuplementaryBed()) {
        return true;
      }
    }
    return false;
  }

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

  public int getPrice() {
    return (int) ((getPriceCabin() + getPriceExtras()) * (1 - getOffer()));
  }

  public int getPrice(int i) {
    return (int) ((getPriceCabin(i) + getPriceExtras(i)) * (1 - getOffer()));
  }

  public int getPriceExtras() {
    int price = 0;
    for (int i = 0; i < extras.size(); i++) {
      price += getPriceExtras(i);
    }
    return price;
  }

  public int getPriceExtras(int n) {
    int priceExtras = 0;
    for (int i = 0; i < extras.get(n).length; i++) {
      priceExtras += extras.get(n)[i].getTotalPrice(people.get(n), cruise.getDuration());
    }
    return priceExtras;
  }

  public int getPriceCabin() {
    int price = 0;
    for (int i = 0; i < cabins.size(); i++) {
      price += getPriceCabin(i);
    }
    return price;
  }

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

  public void setCabins(List<Cabin> cabins) {
    this.cabins = cabins;
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
