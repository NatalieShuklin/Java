
import oop.ex3.spaceship.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a spaceship, which stores crew members with their lockers
 * manages it's storage according to requirements
 *
 * @author cs user natashashuklin
 */
public class Spaceship {
    /*
     * the name of the spaceship
     */
    private final String spaceshipName;
    /*
     * the allowed total number of lockers for the spaceship to have
     */
    private int allowedNumLockers;
    /*
     * the constraint pairs of the spaceship
     */
    private Item[][] constraintPairs;
    /*
     * the long term storage of the spaceship
     */
    private LongTermStorage longTermStorage;
    /**
     * amount of total added lockers to current ship
     */
    private int currLockerAmount;
    /**
     * represnts a return value for the create function, when id is invalid
     */
    public static final int FAILURE_ID = -1;
    /**
     * represnts a return value for the create function, when capacity is invalid
     */
    public static final int FAILURE_CAPACITY = -2;
    /**
     * represnts a return value for the create function, when amount of lockers
     * reached maximum
     */
    public static final int FAILURE_LOCKER_AMOUNT = -3;
    /**
     * check if capacity is negative
     */
    public static final int IS_NEGATIVE_CAP = 0;
    /*
     * map represents the crew members by their id = key, and their lockers which they posses=value
     */
    protected Map<Integer, Locker> shipCrewLockers = new HashMap<Integer, Locker>();
    /**
     * represents a return value for function successful process
     */
    public static final int SUCCESS = 0;

    /**
     * The Spaceship constructor, constructs a ship with name, crew members, and
     * given constraints list for each locker of the ship
     *
     * @param name         the name for the ship
     * @param crewIDs      the ids of the crew members
     * @param numOfLockers the number of allowed lockers in this ship
     * @param constraints  the pairs of Items, that cannot be together in each locker
     */
    public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints) {
        spaceshipName = name;
        allowedNumLockers = numOfLockers;
        constraintPairs = constraints;
        currLockerAmount = 0;
        longTermStorage = new LongTermStorage();
        for (int id : crewIDs) {
            shipCrewLockers.put(id, null);
        }
    }

    /**
     * This method returns the long term storage object of the current spaceship
     *
     * @return the long term storage of current ship
     */
    public LongTermStorage getLongTermStorage() {
        return this.longTermStorage;
    }

    /**
     * This method creates a Locker object and adds it as part of the spaceship's storage
     * for the crewId given
     *
     * @param crewID   the given id of crew member in the ship
     * @param capacity the capacity of the new wanted locker
     * @return 0 if locker added successfully, else if id is not valid return -1,
     * if the capacity is not valid return -2, if the current ship has reached max
     * amount of lockers already return -3
     */
    public int createLocker(int crewID, int capacity) {
        if (!this.shipCrewLockers.containsKey(crewID)) {
            return FAILURE_ID;
        }
        if (capacity < IS_NEGATIVE_CAP) {
            return FAILURE_CAPACITY;
        }
        if (this.allowedNumLockers == currLockerAmount) {
            return FAILURE_LOCKER_AMOUNT;
        }
        Locker newLocker = new Locker(this.longTermStorage, capacity, this.constraintPairs);
        this.shipCrewLockers.put(crewID, newLocker);
        currLockerAmount++;
        return SUCCESS;
    }

    /**
     * This method returns an array of the crew id's in the current spaceship
     *
     * @return array with crew ids of the ship
     */
    public int[] getCrewIDs() {
        Set<Integer> keys = this.shipCrewLockers.keySet();
        int[] array = new int[keys.size()];
        int index = 0;
        for (Integer elem : keys) array[index++] = elem.intValue();
        return array;
    }

    /**
     * This method returns an array of Lockers with length numOfLockers fiven in
     * constructor of ship
     *
     * @return the array of lockers in this ship
     */
    public Locker[] getLockers() {
        int i = 0;
        Locker[] values = new Locker[this.allowedNumLockers];
        for (Map.Entry<Integer, Locker> entry : this.shipCrewLockers.entrySet()) {
            if (entry.getValue() != null) {
                values[i++] = entry.getValue();
            }
        }
        for (int k = i; k < this.allowedNumLockers; k++) {
            values[i++] = null;
        }
        return values;
    }
}
