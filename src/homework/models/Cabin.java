package homework.models;

import static homework.I18n.tr;

/**
 * Represents a cabin type.
 */
public class Cabin {

  private String name;
  private int price;
  private int capacity;

  protected Cabin(String name, int price, int capacity) {
    setName(name);
    setPrice(price);
    setCapacity(capacity);
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

  @Override
  public String toString() {
    return name + " (" + price + " \u20ac)";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Cabin && ((Cabin) obj).name.equals(name);
  }
}
