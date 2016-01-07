package homework.models;

import static homework.I18n.tr;
import static homework.I18n.trn;

/**
 * Represents a cabin type.
 */
public class Cabin {

  private String name;
  private int price;
  private int capacity;
  private int left;

  public Cabin(String name) {
    setName(name);
  }

  protected Cabin(String name, int price, int capacity, int left) {
    setName(name);
    setPrice(price);
    setCapacity(capacity);
    setLeft(left);
  }

  public String getName() {
    return tr(name);
  }

  private void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  private void setPrice(int price) {
    this.price = price;
  }

  public int getCapacity() {
    return capacity;
  }

  private void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public void setLeft(int left) {
    this.left = left;
  }

  @Override
  public String toString() {
    return name + " (" + price + " \u20ac / " + tr("person") + ") " + left + " " + trn("left", left);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Cabin && ((Cabin) obj).name.equals(name);
  }
}
