
import oop.ex3.searchengine.*;

import java.util.Comparator;
import java.lang.Math.*;

/**
 * comperator class for Hotels objects used for sorting in specific odder
 */
public class DistancePOIComperator implements Comparator<Hotel> {
    /*
     * latitude
     */
    private double lat;
    /*
     * longitue
     */
    private double longit;

    /**
     * constructor of comperator
     *
     * @param latitude  input lat.
     * @param longitude input long.
     */
    public DistancePOIComperator(double latitude, double longitude) {
        lat = latitude;
        longit = longitude;
    }

    /*
    calculates eucledian distance and returns it. between the object and given
    latitude and logitude
     */
    private double calcDist(double latitudeSec, double longitudeSec) {
        double sum = (lat - latitudeSec) * (lat - latitudeSec) +
                (longit - longitudeSec) * (longit - longitudeSec);
        return Math.sqrt(sum);
    }

    /**
     * compae function, with 2 comparing : by eucl. distance and if equals
     * compare by POI number descending.
     *
     * @param first  first hotel to compare
     * @param second second hotel to compare
     * @return returns 0 when equal, -1 when h1 is less then h2, else 1
     */
    public int compare(Hotel first, Hotel second) {
        Double distanceOne = calcDist(first.getLatitude(), first.getLongitude());
        Double distanceSec = calcDist(second.getLatitude(), second.getLongitude());
        int comp = distanceOne.compareTo(distanceSec);
        if (comp != 0) {
            return comp;
        }
        Integer poiFirst = first.getNumPOI();
        Integer poiSec = second.getNumPOI();
        return poiSec.compareTo(poiFirst);
    }
}
