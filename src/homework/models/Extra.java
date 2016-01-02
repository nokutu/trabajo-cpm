package homework.models;

import static homework.I18n.tr;

/**
 * Represents the extra options a cruise can offer.
 */
public class Extra {

  private String code;
  private String name;
  private int price;

  private String unit;


  public Extra(String code, String name, int price) {
    this.code = code;
    this.name = name;
    this.price = price;
    if (name.equals("Cama supletoria")) {
      unit = "\u20ac/" + tr("day");
    } else {
      unit = "\u20ac/" + tr("person") + "*" + tr("day");
    }
  }

  @Override
  public int hashCode() {
    return this.code.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Extra && code.equals(((Extra) obj).code);
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public int getPrice() {
    return price;
  }
}
