import oop.ex3.searchengine.*;

import java.util.Comparator;

/**
 * comperator class for Hotel objects
 */
public class StarRatePropertyComparator implements Comparator<Hotel> {
    /**
     * the compare function, compares by star rating descending, if equals
     * compares by property name - alphabetic order
     *
     * @param first  first hotel to compare
     * @param second second hotel to compare
     * @return 0 if equals by comparison defined prev. -1 if h1 less than h2, else 1
     */
    public int compare(Hotel first, Hotel second) {
        int rateFirst = first.getStarRating();
        int rateSec = second.getStarRating();
        if (rateFirst != rateSec) {
            return rateSec - rateFirst;
        }
        String one = first.getPropertyName();
        String two = second.getPropertyName();
        return one.compareTo(two);
    }
}
