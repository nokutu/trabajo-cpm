package homework.models;

import homework.Utils;

import java.util.Date;


/**
 * Created by nokutu on 02/01/2016.
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
