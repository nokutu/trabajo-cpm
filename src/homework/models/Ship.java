package homework.models;

/**
 * Created by nokutu on 27/12/15.
 */
public class Ship {

  private String code;
  private String denomination;
  private String description;
  private int numDoubleInterior;
  private int numDoubleExterior;
  private int numFamilyInterior;
  private int numFamilyExterior;
  private float priceDoubleInterior;
  private float priceDoubleExterior;
  private float priceFamilyInterior;
  private float priceFamilyExterior;

  public Ship(String code, String denomination, String description, int numDoubleInterior, int numDoubleExterior,
              int numFamilyInterior, int numFamilyExterior, int priceDoubleInterior, int priceDoubleExterior,
              int priceFamilyInterior, int priceFamilyExterior) {
    this.code = code;
    this.denomination = denomination;
    this.description = description;
    this.numDoubleInterior = numDoubleInterior;
    this.numDoubleExterior = numDoubleExterior;
    this.numFamilyInterior = numFamilyInterior;
    this.numFamilyExterior = numFamilyExterior;
    this.priceDoubleInterior = priceDoubleInterior;
    this.priceDoubleExterior = priceDoubleExterior;
    this.priceFamilyInterior = priceFamilyInterior;
    this.priceFamilyExterior = priceFamilyExterior;
  }

  @Override
  public int hashCode() {
    return code.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Ship) {
      return code.equals(((Ship) obj).code);
    }
    return false;
  }
}
