package homework.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nokutu on 07/01/2016.
 */
public class Order {

  private Cruise cruise;
  private CruiseDate date;
  private List<Cabin> cabins = new ArrayList<>();
  private User user;
  private List<Integer> people = new ArrayList<>();
  private List<Integer> priceCabin = new ArrayList<>();
  private List<Integer> priceExtras = new ArrayList<>();
  private List<Integer> offer = new ArrayList<>();
  private List<Boolean> hasExtraBed = new ArrayList<>();
  private List<Extra[]> extras = new ArrayList<>();

  public Order(Cruise cruise, CruiseDate date) {
    setCruise(cruise);
    setDate(date);
    setUser(user);
  }

  public Order(String parse) {
    String[] array = parse.split("%");
    for (String field : array) {
      // TODO
    }
  }

  private void setCabinBooked(Cabin cabin) {
    switch (cabin.getName()) {
      case Cruise.INTERIOR_DOUBLE:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]++;
        break;
      case Cruise.EXTERIOR_DOUBLE:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]++;
        break;
      case Cruise.INTERIOR_FAMILY:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]++;
        break;
      case Cruise.EXTERIOR_FAMILY:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]++;
        break;
    }
  }

  public void destroy() {
    for (Cabin cabin : cabins) {
      switch (cabin.getName()) {
        case Cruise.INTERIOR_DOUBLE:
          cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
          break;
        case Cruise.EXTERIOR_DOUBLE:
          cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
          break;
        case Cruise.INTERIOR_FAMILY:
          cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
          break;
        case Cruise.EXTERIOR_FAMILY:
          cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
          break;
      }
    }
    cabins.clear();
  }

  public void addCabin(Cabin cabin, int people, List<Extra> extras) {
    cabins.add(cabin);
    setCabinBooked(cabin);

    boolean bool = true;
    int price = 0;
    Extra[] extrasarray = new Extra[extras.size()];
    int i = 0;
    for (Extra e : extras) {
      if (e.isSuplementaryBed()) {
        hasExtraBed.add(true);
        bool = false;
      }
      price += e.getTotalPrice(people, cruise.getDuration());
      extrasarray[i++] = e;
    }
    if (bool) {
      hasExtraBed.add(false);
    }
    priceExtras.add(price);

    priceCabin.add(cabin.getPrice() * (cabin.getCapacity() * cruise.getDuration()));
    offer.add((int) ((priceCabin.get(priceCabin.size() - 1) + priceExtras.get(priceExtras.size() - 1)) * cruise.getOffer()));
    this.people.add(people);
    this.extras.add(extrasarray);
  }

  public void remove(int i) {
    switch (cabins.get(i).getName()) {
      case Cruise.INTERIOR_DOUBLE:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
        break;
      case Cruise.EXTERIOR_DOUBLE:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
        break;
      case Cruise.INTERIOR_FAMILY:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
        break;
      case Cruise.EXTERIOR_FAMILY:
        cruise.interiorDoubleBooked[cruise.getDates().indexOf(date)]--;
        break;
    }
    cabins.remove(i);
    people.remove(i);
    priceExtras.remove(i);
    priceCabin.remove(i);
    offer.remove(i);
    hasExtraBed.remove(i);
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

  public List<Integer> getPriceCabin() {
    return priceCabin;
  }

  public void setPriceCabin(List<Integer> priceCabin) {
    this.priceCabin = priceCabin;
  }

  public List<Integer> getPriceExtras() {
    return priceExtras;
  }

  public void setPriceExtras(List<Integer> priceExtras) {
    this.priceExtras = priceExtras;
  }

  public List<Integer> getOffer() {
    return offer;
  }

  public void setOffer(List<Integer> offer) {
    this.offer = offer;
  }

  public List<Boolean> getHasExtraBed() {
    return hasExtraBed;
  }

  public void setHasExtraBed(List<Boolean> hasExtraBed) {
    this.hasExtraBed = hasExtraBed;
  }
}
