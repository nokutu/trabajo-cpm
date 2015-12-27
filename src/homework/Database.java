package homework;

import homework.models.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Database used to store file information.
 */
public class Database {

    private ConcurrentHashMap<String, Cruise> cruises;
    private ConcurrentHashMap<String, Ship> ships;
    private ConcurrentHashMap<String, Zone> zones;
    private ConcurrentHashMap<String, City> cities;


    public Database() {
        cruises = new ConcurrentHashMap<>();
        ships = new ConcurrentHashMap<>();
        zones = new ConcurrentHashMap<>();
        cities = new ConcurrentHashMap<>();
    }

    public void addCruises(List<String[]> list) {
        for (String[] parse : list) {
            cruises.put(parse[0], new Cruise(
                    parse[0], getZone(parse[1]), parse[2], getCity(parse[3]), getRute(parse[4]), parse[5],
                    parse[6].equals("Y") ? true : false, Integer.parseInt(parse[7]), getDate(parse[8]), ships.get(parse[9])
            ));
        }
    }

    public void addShips(List<String[]> list) {
        for (String[] parse : list) {
            ships.put(parse[0], new Ship(
                    parse[0], parse[1], parse[2],
                    Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5]), Integer.parseInt(parse[6]),
                    Float.parseFloat(parse[7]), Float.parseFloat(parse[8]), Float.parseFloat(parse[9]), Float.parseFloat(parse[10])
            ));
        }
    }

    private Zone getZone(String zone) {
        if (zones.get(zone) == null) {
            zones.put(zone, new Zone(zone));
        }
        return zones.get(zone);
    }

    private City getCity(String city) {
        if (cities.get(city) == null) {
            cities.put(city, new City(city));
        }
        return cities.get(city);
    }

    private Rute getRute(String rute) {
        // TODO
        return null;
    }

    private Date getDate(String date) {
        // TODO
        return null;
    }
}
