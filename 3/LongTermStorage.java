import oop.ex3.spaceship.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * class represents a long term storage object
 */
public class LongTermStorage {
    /**
     * map stores the storage by keeping items type=key, and their quantity in storage = value
     */
    protected Map<String, Integer> longStorage;
    /**
     * amount of occupied currently units
     */
    protected static int countOccupied;
    /**
     * the total capacity of storage
     */
    protected static int capacity = 1000;
    /**
     * the set of the items by type in this storage
     */
    protected Set<Item> itemsSet;
    /**
     * initialized occupied state
     */
    private static final int ZER0 = 0;
    /**
     * describes success return value of function process
     */
    public static final int SUCCESS = 0;
    /**
     * describes failure return value of function process
     */
    public static final int FAILURE = -1;
    /**
     * check for negative size n of items
     */
    public static final int NEG = 0;

    /**
     * constructor of the long term storage
     */
    public LongTermStorage() {
        longStorage = new HashMap<String, Integer>();
        countOccupied = ZER0;
        itemsSet = new HashSet<Item>();
    }

    /**
     * Adds n items of the given type to the long term storage
     *
     * @param item the item to add
     * @param n    amount of the item to add
     * @return if added successfully return 0, else return -1 and print error msg
     */
    public int addItem(Item item, int n) {
        if (item == null) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return FAILURE;
        }
        if (n < NEG) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return FAILURE;
        }
        if (capacity - countOccupied < n * (item.getVolume())) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: no " +
                    "room for " + n + " items of type " + item.getType());
            return FAILURE;
        }
        if (!longStorage.containsKey(item.getType())) {
            longStorage.put(item.getType(), n);
        } else {
            longStorage.put(item.getType(), longStorage.get(item.getType()) + n);
        }
        countOccupied += n * (item.getVolume());
        if (!itemsSet.contains(item)) {
            itemsSet.add(item);
        }
        return SUCCESS;
    }

    /**
     * resets the inventory, so the storage doesn't contain any values
     */
    public void resetInventory() {
        countOccupied = 0;
        longStorage.clear();
        itemsSet.clear();
    }

    /**
     * the function returns item count in storage
     *
     * @param type the type of the item
     * @return the quantity of the item of this type in the storage
     */
    public int getItemCount(String type) {
        if (type == null) {
            return ZER0;
        }
        if (!longStorage.containsKey(type)) {
            return ZER0;
        }
        return longStorage.get(type).intValue();
    }

    /**
     * function returns a map of the items and their quantity
     *
     * @return a map of all the items contained in lts and their quantity
     */
    public Map<String, Integer> getInventory() {
        return longStorage;
    }

    /**
     * gets the capacity of the lts
     *
     * @return the capacity of the long term storage
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * gets the available capacity in storage
     *
     * @return the available capacity in storage
     */
    public int getAvailableCapacity() {
        return capacity - countOccupied;
    }
}
