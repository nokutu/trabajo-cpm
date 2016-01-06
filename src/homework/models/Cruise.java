package homework.models;

import homework.Main;
import homework.Utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static homework.I18n.tr;

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
  private List<CruiseDate> dates;
  private Ship ship;
  private ImageIcon image;

  private int[] interiorDoubleBooked;
  private int[] exteriorDoubleBooked;
  private int[] interiorFamilyBooked;
  private int[] exteriorFamilyBooked;

  private float offer = 0;

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
        dates.add(new CruiseDate(Utils.df.parse(date)));
      } catch (ParseException e) {
        Main.log.e(e);
        dates.add(null);
      }
    }
    Collections.sort(dates, new Comparator<CruiseDate>() {
      @Override
      public int compare(CruiseDate o1, CruiseDate o2) {
        return o1.getDate().compareTo(o2.getDate());
      }
    });

    interiorDoubleBooked = new int[dates.size()];
    exteriorDoubleBooked = new int[dates.size()];
    interiorFamilyBooked = new int[dates.size()];
    exteriorFamilyBooked = new int[dates.size()];

    try {
      image = Utils.proportionalScaleWidth(new ImageIcon(ImageIO.read(new File("images/" + code + ".jpg"))), 300);
    } catch (IOException e) {
      Main.log.e(e);
    }
  }

  @Override
  public int hashCode() {
    return this.code.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Cruise && code.equals(((Cruise) obj).code);
  }

  public String getCode() {
    return code;
  }

  private void setCode(String code) {
    this.code = code;
  }

  public Zone getZone() {
    return zone;
  }

  private void setZone(Zone zone) {
    this.zone = zone;
  }

  public String getDenomination() {
    return denomination;
  }

  private void setDenomination(String denomination) {
    this.denomination = denomination;
  }

  public City getStartPort() {
    return startPort;
  }

  private void setStartPort(City startPort) {
    this.startPort = startPort;
  }

  public Rute getRute() {
    return rute;
  }

  private void setRute(Rute rute) {
    this.rute = rute;
  }

  public String getDescription() {
    return description;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  public boolean isMinorAllowed() {
    return minorAllowed;
  }

  private void setMinorAllowed(boolean minorAllowed) {
    this.minorAllowed = minorAllowed;
  }

  public int getDuration() {
    return duration;
  }

  private void setDuration(int duration) {
    this.duration = duration;
  }

  public Ship getShip() {
    return ship;
  }

  private void setShip(Ship ship) {
    this.ship = ship;
  }

  public List<CruiseDate> getDates() {
    return dates;
  }

  public List<Cabin> getDefaultCabins(CruiseDate date) {
    List<Cabin> ret = new ArrayList<>();
    int i = dates.indexOf(date);
    if (i == -1) {
      return ret;
    }
    if (ship.getNumInteriorDouble() - interiorDoubleBooked[i] > 0) {
      ret.add(new Cabin(tr("Interior double"), ship.getPriceInteriorDouble(), 2));
    }
    if (ship.getNumExteriorDouble() - exteriorDoubleBooked[i] > 0) {
      ret.add(new Cabin(tr("Exterior double"), ship.getPriceExteriorDouble(), 2));
    }
    if (ship.getNumInteriorFamily() - interiorFamilyBooked[i] > 0) {
      ret.add(new Cabin(tr("Interior family"), ship.getPriceInteriorFamily(), 4));
    }
    if (ship.getNumExteriorFamily() - exteriorFamilyBooked[i] > 0) {
      ret.add(new Cabin(tr("Exterior family"), ship.getPriceInteriorFamily(), 4));
    }
    return ret;
  }

  public void setOffer(float offer) {
    this.offer = offer;
  }

  public float getOffer() {
    return offer;
  }

  public ImageIcon getImage() {
    return image;
  }
}
