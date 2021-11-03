
import oop.ex3.spaceship.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * the class represents a locker object, which stores items
 * by the requirements of the locker
 *
 * @author cs user natashashuklin
 */
public class Locker {
    /*
    the long term storage of the ship
     */
    private LongTermStorage longTermStorage;
    /*
    locker capacity value
     */
    private final int lockerCapacity;
    /**
     * the constraints pairs for the locker
     */
    protected Item[][] lockerConstraints;
    /**
     * amount of units occupied currently in locker
     */
    static private int unitsOccupied;
    /**
     * the map to store type of the item stored in locker = key, with its amount in the locker
     * =value
     */
    protected Map<String, Integer> lockerItems;
    /*
    set of the items that are currently in locker, only one of each type is displayed
     */
    private Set<Item> itemsSet;
    /**
     * describes index of first element in constraint pair
     */
    public static final int FIRST_IN_PAIR = 0;
    /**
     * describes index of second element in constraint pair
     */
    public static final int SECOND_IN_PAIR = 1;
    /**
     * describes twenty percent for storage use of locker
     */
    public static final double TWENTY_PERCENT = 0.2;
    /**
     * describes success return value of function process
     */
    public static final int SUCCESS = 0;
    /**
     * describes 0 amount of item
     */
    public static final int ZERO = 0;
    /**
     * describes failure return value of function process
     */
    public static final int FAILURE = -1;
    /**
     * describes a return value for function addItem, if was added to long term
     * storage successfully
     */
    public static final int PART_SUCCESS = 1;
    /**
     * failed addition due to constraints validation
     */
    public static final int FAIL_CONSTRAINT = -2;
    /**
     * check for negative size n of items
     */
    public static final int NEG = 0;

    /**
     * The constructor for locker, creates locker with long term storage, capacity
     * and constraints pairs of the Items
     *
     * @param lts         the long term storage of locker
     * @param capacity    the capacity of locker
     * @param constraints the constraints pairs of locker
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
        longTermStorage = lts;
        lockerCapacity = capacity;
        lockerConstraints = constraints;
        unitsOccupied = 0;
        lockerItems = new HashMap<String, Integer>();
        itemsSet = new HashSet<Item>();
    }

    /*
     * function returns true if item can be added to locker without violating the
     * constraints pairs requirement
     * @param item the item to look on the constarints
     * @return true if can be added, else false
     */
    private boolean checkConstraints(Item item) {
        for (int i = 0; i < lockerConstraints.length; i++) {
            if (item.getType().equals(lockerConstraints[i][FIRST_IN_PAIR].getType())) {
                if (lockerItems.containsKey(lockerConstraints[i][SECOND_IN_PAIR].getType())) {
                    return false;
                }
            }
            if (item.getType().equals(lockerConstraints[i][SECOND_IN_PAIR].getType())) {
                if (lockerItems.containsKey(lockerConstraints[i][FIRST_IN_PAIR].getType())) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * function calculates how much of the current item can be kept in the locker
     * due to needed move into long term storage
     * @param item the item to check on
     * @return the amount of items of the current type that can be kept in the locker
     */
    private int keepInLocker(Item item) {
        int maxKeepUnits = (int) (lockerCapacity * TWENTY_PERCENT);
        int items = (int) (maxKeepUnits / (item.getVolume()));
        if (getAvailableCapacity() < items * (item.getVolume())) {
            return getAvailableCapacity() / item.getVolume();
        }
        return items;
    }

    /**
     * this method checks if current amount of item addition causes to item to be moved to
     * long term storage, checks if it's type takes more than 50% of the locker capacity
     *
     * @param item     the item to add
     * @param n        number of items of this type attempted to add
     * @param quantity the amount of this item currently in locker
     * @return true the addition will cause passing the 50% of locker capacity
     * else return false
     */
    private boolean isAboveHalfCapacity(Item item, int n, int quantity) {
        int totalQuantity = quantity + n;
        int storageUnits = item.getVolume() * totalQuantity;
        if (lockerCapacity / 2 < storageUnits) {
            return true;
        }
        return false;
    }

    /**
     * This method adds n items of the given type to the locker.
     *
     * @param item the given item to be added
     * @param n    amount of items of the same type as given item input, to add
     * @return 0 if addition succeeded, else return -1 and print correct error, return -2
     * if constraints validation failed
     */
    public int addItem(Item item, int n) {
        if (item == null || item.getVolume() > lockerCapacity || item.getVolume()*n>lockerCapacity) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return FAILURE;
        }
        int quantity;
        if (!lockerItems.containsKey(item.getType())) {
            quantity = 0;
        } else {
            quantity = lockerItems.get(item.getType());
        }
        if (!checkConstraints(item)) {
            System.out.println("Error: Your request cannot be completed at this time." +
                    " Problem: the locker cannot contain items of type " + item.getType() +
                    ", as it contains a contradicting item");
            return FAIL_CONSTRAINT;
        }
        if (n < NEG) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return FAILURE;
        }
        if (isAboveHalfCapacity(item, n, quantity)) {
            int keepItems = keepInLocker(item);
            int moveItems = n - keepItems;
            if (moveItems > 0 && longTermStorage != null && longTermStorage.getAvailableCapacity()
                    >= moveItems * (item.getVolume())) {
                if (longTermStorage.addItem(item, moveItems) != SUCCESS) {
                    return FAILURE;
                }
                lockerItems.put(item.getType(), keepItems);
                unitsOccupied = unitsOccupied - quantity * item.getVolume() + keepItems * item.getVolume();
                System.out.println("Warning: Action successful,but has caused items to be moved to storage");
                return PART_SUCCESS;
            }
            if (moveItems * item.getVolume() > longTermStorage.getAvailableCapacity()) {
                System.out.println("Error: Your request cannot be completed at this time. Problem: no " +
                        "room for " + moveItems + " items of type " + item.getType());
                return FAILURE;
            }
        } else {
            if (getAvailableCapacity() < (quantity + n) * item.getVolume()) {
                System.out.println("Error: Your request cannot be completed at this time. Problem: no " +
                        "room for " + n + " items of type " + item.getType());
                return FAILURE;
            }
            lockerItems.put(item.getType(), quantity + n);
            unitsOccupied += n * item.getVolume();
            if (!itemsSet.contains(item)) {
                itemsSet.add(item);
            }
        }
        return SUCCESS;
    }

    /**
     * This method removes n items of the type "type" from the locker
     *
     * @param item the item to remove from
     * @param n    number of items to remove
     * @return 0 if successful removed occurred, else return -1 and print error msg
     */
    public int removeItem(Item item, int n) {
        if (item == null) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return FAILURE;
        }
        if (n < NEG) {
            System.out.println("Error: Your request cannot be completed at this time. " +
                    "Problem: cannot remove a negative number of items of type " + item.getType());
            return FAILURE;
        }
        Integer quantity = lockerItems.get(item.getType());
        if (quantity == null) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return FAILURE;
        }
        if (n > quantity.intValue()) {
            System.out.println("Error: Your request cannot be completed at this time." +
                    " Problem: the locker does not contain " + n + " items of type " + item.getType());
            return FAILURE;
        }
        if (n == quantity) {
            lockerItems.put(item.getType(), 0);
            unitsOccupied -= n * item.getVolume();
            itemsSet.remove(item);
            return SUCCESS;
        } else {
            lockerItems.put(item.getType(), lockerItems.get(item.getType()) - n);
            unitsOccupied -= n * item.getVolume();
        }
        return SUCCESS;
    }

    /**
     * This method returns a map of all the item types contained in the locker
     * and their respective quantities
     *
     * @return a map of all item types and their quantities
     */
    public Map<String, Integer> getInventory() {
        return lockerItems;
    }

    /**
     * this method returns the locker's total capacity
     *
     * @return the locker's total capacity
     */
    public int getCapacity() {
        return lockerCapacity;
    }

    /**
     * This method returns the locker's available capacity
     *
     * @return how many storage units are unoccupied by Items
     */
    public int getAvailableCapacity() {
        return lockerCapacity - unitsOccupied;
    }

    /**
     * This method returns the number of Items of type "type" the locker contains
     *
     * @param type the input item type to look for
     * @return number of items of the given type given, currently in locker
     */
    public int getItemCount(String type) {
        Integer counter = lockerItems.get(type);
        if (counter != null) {
            return counter.intValue();
        }
        return ZERO;
    }
}
