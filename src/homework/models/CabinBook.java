package homework.models;

import java.util.List;

import static homework.I18n.tr;
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

  public CabinBook(Cruise cruise, Cabin cabin, int people, CruiseDate cruiseDate, List<Extra> extras) {
    setCruise(cruise);
    setCabin(cabin);
    setPeople(people);
    setCruiseDate(cruiseDate);
    setExtras(extras);
  }

  public Cruise getCruise() {
    return cruise;
  }

  public void setCruise(Cruise cruise) {
    this.cruise = cruise;
  }

  public Cabin getCabin() {
    return cabin;
  }

  public void setCabin(Cabin cabin) {
    this.cabin = cabin;
  }

  public int getPeople() {
    return people;
  }

  public void setPeople(int people) {
    this.people = people;
  }

  public CruiseDate getCruiseDate() {
    return cruiseDate;
  }

  public void setCruiseDate(CruiseDate cruiseDate) {
    this.cruiseDate = cruiseDate;
  }

  public List<Extra> getExtras() {
    return extras;
  }

  public void setExtras(List<Extra> extras) {
    this.extras = extras;
  }

  public String getShoppingCartString() {
    String ret = "";

    ret += cabin.getName() + "-" + getPeople() + " " + trn("person", getPeople());

    return ret;
  }
}
