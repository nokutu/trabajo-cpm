package homework.models;

import homework.Utils;

import java.text.ParseException;
import java.util.Date;


/**
 * Wrapper class storing the date when a cruise takes place.
 */
public class CruiseDate {

  private Date date;

  public CruiseDate(String date) throws ParseException {
    this(Utils.df.parse(date));
  }

  public CruiseDate(Date date) {
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public String toString() {
    return Utils.df.format(date);
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof CruiseDate && object.toString().equals(toString());
  }
}
