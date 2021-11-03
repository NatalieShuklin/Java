import oop.ex3.searchengine.*;

import java.util.*;


/**
 * class represent booking site
 */
public class BoopingSite {
    /**
     * name of data set file hotels
     */
    protected String datasetName;
    /**
     * the data of hotels in site
     */
    protected Hotel[] data;
    /**
     * comperator for Hotel objects.  used for distance and poi sort
     */
    protected DistancePOIComperator distancePOIComperator;
    /**
     * comperator for Hotel objects, used for star rating and property sort
     */
    protected StarRatePropertyComparator ratePropertyComparator;

    /**
     * constructor of booking site object
     *
     * @param name the name to the site of the file hotels
     */
    public BoopingSite(String name) {
        this.datasetName = name;
        data = HotelDataset.getHotels(datasetName);
    }

    /**
     * This method returns an array of hotels located in the given city,
     * sorted from the highest star-rating to the lowest
     *
     * @param city the given city
     * @return eturns an array of hotels located in the given city,
     * sorted from the highest star-rating to the lowest
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        HashSet<Hotel> set = new HashSet<Hotel>();
        if (data == null) {
            return new Hotel[]{};
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i].getCity().equals(city)) {
                set.add(data[i]);
            }
        }
        if (set.isEmpty()) {
            return new Hotel[]{};
        }
        List<Hotel> list = new ArrayList<Hotel>(set);
        ratePropertyComparator = new StarRatePropertyComparator();
        Collections.sort(list, ratePropertyComparator);

        Hotel[] sorted = new Hotel[list.size()];
        list.toArray(sorted);
        return sorted;
    }

    /**
     * This method
     * returns an array of hotels, sorted according to their Euclidean distance from the given geographic
     * location, in ascending order
     *
     * @param latitude  the latitude input
     * @param longitude the longtitude input
     * @return an array of hotels, sorted according to their Euclidean distance from the given geographic
     * location, in ascending order
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        if (data == null) {
            return new Hotel[]{};
        }
        if (latitude > Math.toDegrees((Math.PI) / 2) ||
                latitude < Math.toDegrees(-(Math.PI) / 2)) {
            return new Hotel[]{};
        }
        if (longitude > Math.toDegrees((Math.PI)) ||
                latitude < Math.toDegrees(-(Math.PI))) {
            return new Hotel[]{};
        }
        List<Hotel> list = Arrays.asList(data);
        distancePOIComperator = new DistancePOIComperator(latitude, longitude);
        Collections.sort(list, distancePOIComperator);
        Hotel[] sorted = new Hotel[list.size()];
        list.toArray(sorted);
        return sorted;
    }

    /**
     * This method returns an array of hotels in the given city, sorted according to their Euclidean distance
     * from the given geographic location, in ascending order.
     *
     * @param city      inpout city
     * @param latitude  input latitude
     * @param longitude input longitude
     * @return an array of hotels in the given city, sorted according to their Euclidean distance
     * from the given geographic location, in ascending order.
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        if (data == null) {
            return new Hotel[]{};
        }
        if (latitude > Math.toDegrees((Math.PI) / 2) ||
                latitude < Math.toDegrees(-(Math.PI) / 2)) {
            return new Hotel[]{};
        }
        if (longitude > Math.toDegrees((Math.PI)) ||
                latitude < Math.toDegrees(-(Math.PI))) {
            return new Hotel[]{};
        }
        HashSet<Hotel> set = new HashSet<Hotel>();
        for (int i = 0; i < data.length; i++) {
            if (data[i].getCity().equals(city)) {
                set.add(data[i]);
            }
        }
        if (set.isEmpty()) {
            return new Hotel[]{};
        }
        List<Hotel> list = new ArrayList<Hotel>(set);
        distancePOIComperator = new DistancePOIComperator(latitude, longitude);
        Collections.sort(list, distancePOIComperator);
        Hotel[] sorted = new Hotel[list.size()];
        list.toArray(sorted);
        return sorted;
    }
}
