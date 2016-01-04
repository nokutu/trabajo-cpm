package homework.models;

/**
 * Created by nokutu on 02/01/2016.
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
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  @Override
  public String toString() {
    return name + " (" + price + " \u20ac)";
  }


}
