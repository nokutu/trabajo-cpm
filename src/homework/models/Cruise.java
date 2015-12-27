package homework.models;

import java.util.Date;

/**
 * Model class representing a cruise
 */
public class Cruise {

  private String code;
  private Zone zone;
  private String denomination;
  private City startPort;
  private Rute rute;
  private String description;
  private boolean minorAllowed;
  private int duration;
  private Date startDate;
  private Ship ship;

  public Cruise(String code, Zone zone, String denomination, City startPort, Rute rute, String description,
                boolean minorAllowed, int duration, Date startDate, Ship ship) {
    this.code = code;
    this.zone = zone;
    this.denomination = denomination;
    this.startPort = startPort;
    this.rute = rute;
    this.description = description;
    this.minorAllowed = minorAllowed;
    this.duration = duration;
    this.startDate = startDate;
    this.ship = ship;
  }

  @Override
  public int hashCode() {
    return this.code.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Cruise) {
      return code.equals(((Cruise) obj).code);
    }
    return false;
  }
}
