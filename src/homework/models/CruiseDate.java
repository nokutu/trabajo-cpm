package homework.models;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by nokutu on 02/01/2016.
 */
public class CruiseDate {

  private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

  private Date date;

  public CruiseDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return df.format(date);
  }
}
