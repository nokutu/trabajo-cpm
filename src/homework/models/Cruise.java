package homework.models;

import homework.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model class representing a cruise
 */
public class Cruise {

  private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");


  private String code;
  private Zone zone;
  private String denomination;
  private City startPort;
  private Rute rute;
  private String description;
  private boolean minorAllowed;
  private int duration;
  private List<Date> dates;
  private Ship ship;

  public Cruise(String code, Zone zone, String denomination, City startPort, Rute rute, String description,
                boolean minorAllowed, int duration, String datesString, Ship ship) {
    setCode(code);
    setZone(zone);
    setDenomination(denomination);
    setStartPort(startPort);
    setRute(rute);
    setDescription(description);
    setMinorAllowed(minorAllowed);
    setDuration(duration);
    setShip(ship);

    dates = new ArrayList<>();
    for (String date : datesString.split("%")) {
      try {
        dates.add(df.parse(date));
      } catch (ParseException e) {
        Main.log.e(e);
        dates.add(null);
      }
    }
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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Zone getZone() {
    return zone;
  }

  public void setZone(Zone zone) {
    this.zone = zone;
  }

  public String getDenomination() {
    return denomination;
  }

  public void setDenomination(String denomination) {
    this.denomination = denomination;
  }

  public City getStartPort() {
    return startPort;
  }

  public void setStartPort(City startPort) {
    this.startPort = startPort;
  }

  public Rute getRute() {
    return rute;
  }

  public void setRute(Rute rute) {
    this.rute = rute;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isMinorAllowed() {
    return minorAllowed;
  }

  public void setMinorAllowed(boolean minorAllowed) {
    this.minorAllowed = minorAllowed;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Ship getShip() {
    return ship;
  }

  public void setShip(Ship ship) {
    this.ship = ship;
  }
}
