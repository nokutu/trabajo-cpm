package homework.models;

import java.util.List;

import static homework.I18n.trn;

/**
 * Created by nokutu on 04/01/2016.
 */
public class CabinBook {

  private Cruise cruise;
  private Cabin cabin;
  private int people;
  private CruiseDate cruiseDate;
  private List<Extra> extras;
  private int priceCabin;
  private int priceExtras;
  private int offer;
  private boolean hasExtraBed = false;

  public CabinBook(Cruise cruise, Cabin cabin, int people, CruiseDate cruiseDate, List<Extra> extras, int priceCabin, int priceExtras, int offer) {
    setCruise(cruise);
    setCabin(cabin);
    setPeople(people);
    setCruiseDate(cruiseDate);
    setExtras(extras);
    setPriceCabin(priceCabin);
    setPriceExtras(priceExtras);
    setOffer(offer);
    for (Extra e : extras) {
      if (e.isSuplementaryBed()) {
        hasExtraBed = true;
        break;
      }
    }
  }

  public Cruise getCruise() {
    return cruise;
  }

  private void setCruise(Cruise cruise) {
    this.cruise = cruise;
  }

  public Cabin getCabin() {
    return cabin;
  }

  private void setCabin(Cabin cabin) {
    this.cabin = cabin;
  }

  public int getPeople() {
    return people;
  }

  private void setPeople(int people) {
    this.people = people;
  }

  public CruiseDate getCruiseDate() {
    return cruiseDate;
  }

  private void setCruiseDate(CruiseDate cruiseDate) {
    this.cruiseDate = cruiseDate;
  }

  public List<Extra> getExtras() {
    return extras;
  }

  private void setExtras(List<Extra> extras) {
    this.extras = extras;
  }

  public String getShoppingCartString() {
    String ret = "";

    ret += cabin.getName() + "-" + getPeople() + " " + trn("person", getPeople());

    return ret;
  }

  public int getPriceCabin() {
    return priceCabin;
  }

  private void setPriceCabin(int priceCabin) {
    this.priceCabin = priceCabin;
  }

  public boolean hasExtraBed() {
    return hasExtraBed;
  }

  public int getPriceExtras() {
    return priceExtras;
  }

  private void setPriceExtras(int priceExtras) {
    this.priceExtras = priceExtras;
  }

  public int getOffer() {
    return offer;
  }

  private void setOffer(int offer) {
    this.offer = offer;
  }
}
