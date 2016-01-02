package homework.models;

/**
 * Created by nokutu on 02/01/2016.
 */
public class Cabin {

  private String name;
  private int price;

  protected Cabin(String name, int price) {
    setName(name);
    setPrice(price);
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

  @Override
  public String toString() {
    return name + " (" + price + " \u20ac)";
  }


}
