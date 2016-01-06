package homework.models;

/**
 * Represents each of the zones where the cruises take place.
 */
public class Zone {

  private String name;

  public Zone(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
