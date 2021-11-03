import org.junit.*;

import static org.junit.Assert.*;

import oop.ex3.spaceship.*;

import java.util.Arrays;
import java.util.Map;

/**
 * Represents a test for Spaceship functionalities
 */
public class SpaceshipTest {

    Spaceship ship;
    Item[][] constr = ItemFactory.getConstraintPairs();
    protected LongTermStorage longTermStorage;
    int[] ids = {1, 2, 4, 5, 6, 7, 8, 9};

    /**
     * before test class object preperation- call constructors
     */
    @Before
    public void setTestObf() {
        String name = "one";
        int numOfLockers = 2;
        longTermStorage = new LongTermStorage();
        ship = new Spaceship(name, ids, numOfLockers, constr);
    }

    /**
     * succesful addition test
     */
    @Test
    public void testCreateLocker() {
        for (Map.Entry<Integer, Locker> entry : ship.shipCrewLockers.entrySet()) {
            entry.setValue(null);
        }
        int n = ship.createLocker(1, 50);
        assertEquals("return value wrong", 0, n);
    }

    /**
     * invalid id test - addition
     */
    @Test
    public void testCreateLockerFail() {
        for (Map.Entry<Integer, Locker> entry : ship.shipCrewLockers.entrySet()) {
            entry.setValue(null);
        }
        int n = ship.createLocker(10, 50);
        assertEquals("return value wrong", -1, n);
    }

    /**
     * negative capacity test - addition
     */
    @Test
    public void testCreateLockerFailNeg() {
        for (Map.Entry<Integer, Locker> entry : ship.shipCrewLockers.entrySet()) {
            entry.setValue(null);
        }
        int n = ship.createLocker(1, -4);
        assertEquals("return value wrong", -2, n);
    }

    /**
     * test of max capcity reached for numoflockers - addition
     */
    @Test
    public void testCreateLockerCap() {
        for (Map.Entry<Integer, Locker> entry : ship.shipCrewLockers.entrySet()) {
            entry.setValue(null);
        }
        ship.createLocker(1, 2);
        ship.createLocker(2, 2);
        int n = ship.createLocker(4, 50);
        assertEquals("return value wrong", -3, n);
    }

    /**
     * test constraints created for locker
     */
    @Test
    public void testsameConstr() {
        for (Map.Entry<Integer, Locker> entry : ship.shipCrewLockers.entrySet()) {
            entry.setValue(null);
        }
        ship.createLocker(1, 2);
        boolean isSame = Arrays.equals(constr, ship.shipCrewLockers.get(1).lockerConstraints);
        assertEquals("not same constraintsfor locker", true, isSame);
    }

    /**
     * used for the testsameCrew test
     *
     * @param arr1 array 1
     * @param arr2 array 2
     * @return if arrays equal return true, else false
     */
    public static boolean compareArrays(int[] arr1, int[] arr2) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    /**
     * tests the created list of ids with the given in constructot
     */
    @Test
    public void testsameCrew() {
        boolean isSame = compareArrays(ids, ship.getCrewIDs());
        assertEquals("not same crew created", true, isSame);
    }
}
