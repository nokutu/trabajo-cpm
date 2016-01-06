package homework.models;

import homework.Utils;

import java.util.Date;


/**
 * Wrapper class storing the date when a cruise takes place.
 */
public class CruiseDate {

  private Date date;

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
}
