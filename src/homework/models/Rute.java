package homework.models;

import homework.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents the rute the cruise is going to take.
 */
public class Rute {

    private List<City> cities;
    private List<Date> dates;

    public Rute(String rute, String datesString) {
        cities = new ArrayList<>();
        dates = new ArrayList<>();
        String[] citiesArray = rute.split("-");
        for (String name : citiesArray) {
            cities.add(Main.db.getCity(name));
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (String date : datesString.split("%")) {
            try {
                dates.add(df.parse(date));
            } catch (ParseException e) {
                dates.add(null);
            }
        }
    }
}
