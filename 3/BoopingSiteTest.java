import oop.ex3.searchengine.*;
import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * test class for BookingSite object
 */
public class BoopingSiteTest {

    BoopingSite boopingSitefirst, boopingSitesec, boopingSiteThree;

    /**
     * set obect before test
     */
    @Before
    public void createTestObjects() {
        boopingSitefirst = new BoopingSite("hotels_tst1.txt");
        boopingSitesec = new BoopingSite("hotels_tst2.txt");
        boopingSiteThree = new BoopingSite("hotels_dataset.txt");
    }

    /**
     * check name
     */
    @Test
    public void testName() {
        assertEquals("name of site wrong", "hotels_tst1.txt", boopingSitefirst.datasetName);
    }

    /**
     * check sorting of city, by star ranking function getHotelsInCityByRating
     */
    @Test
    public void testCityByRankSort() {
        if (boopingSitefirst.data != null) {
            Hotel[] test = boopingSitefirst.getHotelsInCityByRating(boopingSitefirst.data[0].getCity());
            for (int i = 0; i < test.length - 1; i++) {
                int j = i + 1;
                assertTrue("Sort Rating is wrong", Integer.valueOf(test[i].getStarRating()).
                        compareTo(Integer.valueOf(test[j].getStarRating())) >= 0);
            }
            for (int i = 0; i < test.length - 1; i++) {
                int j = i + 1;
                if( test[i].getStarRating() == test[j].getStarRating()) {
                    assertTrue("sort property is wrong ", test[i].getPropertyName().
                            compareTo(test[j].getPropertyName()) <= 0);
                }
            }
        } else {
            Hotel[] test = boopingSitefirst.getHotelsInCityByRating(null);
            Hotel[] empty = new Hotel[]{};
            assertSame("no empty array returned", empty, test);
        }
    }

    /**
     * check illegal input getHotelsInCityByRating
     */
    @Test
    public void testIllegalInput() {
        Hotel[] test = boopingSitefirst.getHotelsInCityByRating("FailNoSuchCity");
        Hotel[] empty = new Hotel[]{};
        for( int i =0; i< test.length; i++) {
            assertEquals(null,test[i]);
        }

    }

    /**
     * test illegal input getHotelsByProximity
     */
    @Test
    public void testIllegalInputTwo() {
        Hotel[] test = boopingSitefirst.getHotelsByProximity(-800, 9);
        for( int i =0; i< test.length; i++) {
            assertEquals(null,test[i]);
        }
    }

    /**
     * test illegal input file  getHotelsInCityByProximity
     */
    @Test
    public void testIllegalInputThree() {
        if (boopingSitefirst.data != null) {
            Hotel[] test = boopingSitefirst.getHotelsInCityByProximity(boopingSitefirst.data[0].getCity(),
                    -800, 9);
            for( int i =0; i< test.length; i++) {
                assertEquals(null,test[i]);
            }
        } else {
            Hotel[] test = boopingSitefirst.getHotelsInCityByProximity("city",
                    -800, 9);
            for( int i =0; i< test.length; i++) {
                assertEquals(null,test[i]);
            }
        }
    }

    /**
     * test sorting of  getHotelsByProximity
     */
    @Test
    public void testByProximity() {
        Hotel[] test = boopingSitefirst.getHotelsByProximity(20.6, 12.1);
        double distances[] = new double[test.length];
        for (int i = 0; i < test.length; i++) {
            double sum = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                    (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
            distances[i] = Math.sqrt(sum);
        }
        Arrays.sort(distances);

        for (int i = 0; i < test.length - 1; i++) {
            int j = i + 1;
            double d = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                    (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
            if(distances[i] == distances[j]) {
                assertTrue("Sort POI is wrong", Integer.valueOf(test[i].getNumPOI())
                        .compareTo(Integer.valueOf(test[j].getNumPOI())) >= 0);
            }
        }
        for (int i = 0; i < test.length - 1; i++) {
            double d = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                    (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
            d = Math.sqrt(d);
            assertEquals("Sort distance is wrong", distances[i], d, 0.0);
        }
    }

    /**
     * test sorting of  getHotelsInCityByProximity
     */
    @Test
    public void testCityByProximity() {
        if (boopingSitefirst.data != null) {
            Hotel[] test = boopingSitefirst.getHotelsInCityByProximity("Village Simsa",
                    23, 33);
            double distances[] = new double[test.length];
            for (int i = 0; i < test.length; i++) {
                double sum = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                        (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
                distances[i] = Math.sqrt(sum);
            }
            Arrays.sort(distances);

            for (int i = 0; i < test.length - 1; i++) {
                int j = i + 1;
                if(distances[i] == distances[j]) {
                    assertTrue("Sort POI is wrong", Integer.valueOf(test[i].getNumPOI())
                            .compareTo(Integer.valueOf(test[j].getNumPOI())) >= 0);
                }
            }
            for (int i = 0; i < test.length - 1; i++) {
                double d = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                        (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
                d = Math.sqrt(d);
                assertEquals("Sort distance is wrong", distances[i], d, 0.0);
            }
        }

    }

    /**
     * test empty file  getHotelsInCityByRating
     */
    @Test
    public void testEmptyFileOne() {
        Hotel[] test = boopingSitesec.getHotelsInCityByRating("city");
        for( int i =0; i< test.length; i++) {
            assertEquals(null,test[i]);
        }
    }

    /**
     * test empty file getHotelsByProximity
     */
    @Test
    public void testEmptyFileTwo() {
        Hotel[] test = boopingSitesec.getHotelsByProximity(78, 89);
        for( int i =0; i< test.length; i++) {
            assertEquals(null,test[i]);
        }
    }

    /**
     * test empty file  -getHotelsInCityByProximity
     */
    @Test
    public void testEmptyFileThree() {
        Hotel[] test = boopingSitesec.getHotelsInCityByProximity("city", 89, 67);
        for( int i =0; i< test.length; i++) {
            assertEquals(null,test[i]);
        }
    }

    /**
     * test big file functionalities
     */
    @Test
    public void testBigFile() {
        if (boopingSitefirst.data != null) {
            Hotel[] test = boopingSitefirst.getHotelsInCityByProximity("Village Simsa",
                    23, 33);
            double distances[] = new double[test.length];
            for (int i = 0; i < test.length; i++) {
                double sum = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                        (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
                distances[i] = Math.sqrt(sum);
            }
            Arrays.sort(distances);

            for (int i = 0; i < test.length - 1; i++) {
                int j = i + 1;
                if(distances[i] == distances[j]) {
                    assertTrue("Sort POI is wrong", Integer.valueOf(test[i].getNumPOI())
                            .compareTo(Integer.valueOf(test[j].getNumPOI())) >= 0);
                }
            }
            for (int i = 0; i < test.length - 1; i++) {
                double d = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                        (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
                d = Math.sqrt(d);
                assertEquals("Sort distance is wrong", distances[i], d, 0.0);
            }
        }
    }
    /**
     * test another functionality of big file
     */
    @Test
    public void testBigFile2 (){
        if (boopingSitefirst.data != null) {
            Hotel[] test = boopingSitefirst.getHotelsInCityByRating(boopingSitefirst.data[0].getCity());
            for (int i = 0; i < test.length - 1; i++) {
                int j = i + 1;
                assertTrue("Sort Rating is wrong", Integer.valueOf(test[i].getStarRating()).
                        compareTo(Integer.valueOf(test[j].getStarRating())) >= 0);
            }
            for (int i = 0; i < test.length - 1; i++) {
                int j = i + 1;
                if( test[i].getStarRating() == test[j].getStarRating()) {
                    assertTrue("sort property is wrong ", test[i].getPropertyName().
                            compareTo(test[j].getPropertyName()) <= 0);
                }
            }
        } else {
            Hotel[] test = boopingSitefirst.getHotelsInCityByRating(null);
            Hotel[] empty = new Hotel[]{};
            assertSame("no empty array returned", empty, test);
        }
    }

    /**
     * test big file
     */
    @Test
    public void testBigFileFour() {
        Hotel[] test = boopingSitefirst.getHotelsByProximity(20.6, 12.1);
        double distances[] = new double[test.length];
        for (int i = 0; i < test.length; i++) {
            double sum = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                    (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
            distances[i] = Math.sqrt(sum);
        }
        Arrays.sort(distances);

        for (int i = 0; i < test.length - 1; i++) {
            int j = i + 1;
            double d = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                    (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
            if(distances[i] == distances[j]) {
                assertTrue("Sort POI is wrong", Integer.valueOf(test[i].getNumPOI())
                        .compareTo(Integer.valueOf(test[j].getNumPOI())) >= 0);
            }
        }
        for (int i = 0; i < test.length - 1; i++) {
            double d = (test[i].getLatitude() - 20.6) * (test[i].getLatitude() - 20.6) +
                    (test[i].getLongitude() - 12.1) * (test[i].getLongitude() - 12.1);
            d = Math.sqrt(d);
            assertEquals("Sort distance is wrong", distances[i], d, 0.0);
        }
    }
}
