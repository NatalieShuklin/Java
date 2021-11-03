
import org.junit.*;

import static org.junit.Assert.*;

import oop.ex3.spaceship.*;

/**
 * class represents tests for functionality of Locker object
 */
public class LockerTest {

    protected Locker locker;
    private LongTermStorage longTermStorage;
    protected int capacity;
    private Item[][] constraints;
    private Item[] items;

    /**
     * prepare objects before tests
     */
    @Before
    public void createTestObjects() {
        constraints = ItemFactory.getConstraintPairs();
        items = ItemFactory.createAllLegalItems();
        capacity = 0;
        for (int i = 0; i < items.length; i++) {
            capacity += items[i].getVolume() * 10;
        }
        longTermStorage = new LongTermStorage();
        locker = new Locker(longTermStorage, capacity, constraints);
    }

    /*
    cleans the locker for testing
     */
    private void clean() {
        if( locker.getAvailableCapacity() == locker.getCapacity()) {
            return;
        }
        for (int i = 0; i < items.length; i++) {
            locker.removeItem(items[i], locker.lockerItems.get(items[i].getType()));
        }
    }

    /*
     * find how much of item needed to pass half capacity of locker
     * @return number of items needed to pass half cap. of locker
     */
    private int addToGetHalf(Item item) {
        int n = 0;
        int counter = 0;
        while (n <= capacity / 2) {
            n += item.getVolume();
            counter++;
        }
        return counter;
    }

    /*
     * return amount of items needed to be added to locker
     * @return the amount of items of the cur. type
     */
    private int getItemsKeptInLocker(Item item) {
        int max = (int) (capacity * 0.2);
        int items = (int) (max / (item.getVolume()));
        if (locker.getAvailableCapacity() < items * (item.getVolume())) {
            return locker.getAvailableCapacity() / item.getVolume();
        }
        return items;
    }

    /*
     * returns index of item in array of items, which doesn't take more than half of
     * locker capacity
     * @return the index, else if not found return -1
     */
    private int indexItem() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].getVolume() < capacity / 2) {
                return i;
            }
        }
        return -1;
    }

    /**
     * test the addition of an item process, when locker has enough space
     * and not passing 50% of capacity
     */
    @Test
    public void testAddNewItem() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            assertEquals("Addition failed", 1, locker.getItemCount(items[item].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test success addition return value
     */
    @Test
    public void testAddSuccess() {
        clean();
        int item = indexItem();
        int n;
        if (item > 0) {
            n = locker.addItem(items[item], 1);
            assertEquals("wrong return value", 0, n);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test addition, when passing items to storage
     */
    @Test
    public void testAddItemCapacity() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            int amountAdd = addToGetHalf(items[item]);
            int itemsToAddLocker = getItemsKeptInLocker(items[item]);
            longTermStorage.resetInventory();
            locker.addItem(items[item], amountAdd);
            assertEquals("locker addition failed", itemsToAddLocker,
                    locker.getItemCount(items[item].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test addition when passing 50% of cap, return value
     */
    @Test
    public void testAddItemCapacityRe() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            int amountAdd = addToGetHalf(items[item]);
            int itemsToAddLocker = getItemsKeptInLocker(items[item]);
            longTermStorage.resetInventory();
            int n = locker.addItem(items[item], amountAdd);
            assertEquals("return value is wrong", 1, n);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * tests lts addition
     */
    @Test
    public void testStorageAddition() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            int amountAdd = addToGetHalf(items[item]);
            int itemsToAddLocker = getItemsKeptInLocker(items[item]);
            int storage = amountAdd - itemsToAddLocker;
            longTermStorage.resetInventory();
            locker.addItem(items[item], amountAdd);
            assertEquals("locker addition failed", storage,
                    longTermStorage.getItemCount(items[item].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test not enough capacity in long term s. and locker cap. full
     */
    @Test
    public void testNoCapacity() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            int n = 0;
            while (n + items[item].getVolume() < locker.getAvailableCapacity()) {
                n += items[item].getVolume();
            }
            longTermStorage.resetInventory();
            int amountAdd = addToGetHalf(items[item]);
            int c = locker.addItem(items[item], amountAdd);
            assertEquals("didnt pass the full storage test", -1, c);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test the capcity value of locker
     */
    @Test
    public void testCapacity() {
        assertEquals("capacity of locker is wrong",
                capacity, locker.getCapacity());
    }

    /**
     * test available cap.
     */
    @Test
    public void testAvailableCap() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            int expected = capacity - items[item].getVolume();
            assertEquals("available capacity not updated correct", expected,
                    locker.getAvailableCapacity());
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }

    }

    /**
     * test item count functionality
     */
    @Test
    public void testItemCount() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 1);
            assertEquals("items count wrong", 1, locker.getItemCount(items[item].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test constraints - addition
     */
    @Test
    public void testConstraints() {
        clean();
        int item = indexItem();
        if (item > 0) {
            if (constraints != null) {
                Item first = constraints[0][0];
                Item second = constraints[0][1];
                locker.addItem(first, 1);
                int n = locker.addItem(second, 1);
                assertEquals("constraint was added", 0, locker.getItemCount(second.getType()));
            } else {
                System.out.println("error - no constraints given");
            }
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test constarints return value func.
     */
    @Test
    public void testConstraintsRe() {
        clean();
        int item = indexItem();
        if (item > 0) {
            if (constraints != null) {
                Item first = constraints[0][0];
                Item second = constraints[0][1];
                locker.addItem(first, 1);
                int n = locker.addItem(second, 1);
                assertEquals("incorrect return value for constraint test", -2, n);
            } else {
                System.out.println("error - no constraints given");
            }
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test valid removal of item
     */
    @Test
    public void testRemoveValidItem() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            int n = locker.removeItem(items[item], 1);
            assertEquals("remove is wrong", 1, locker.getItemCount(items[item].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test remove null item return value
     */
    @Test
    public void testRemoveNullItem() {
        int n = locker.removeItem(null, 1);
        assertEquals("remove is wrong", -1, n);
    }

    /**
     * test remove all items
     */
    @Test
    public void testRemoveAllItem() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            int n = locker.removeItem(items[item], 2);
            assertEquals("remove is wrong", 0, locker.getItemCount(items[item].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test removal of non exiting item- return value check
     */
    @Test
    public void testRemoveNonExistItem() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            if (item + 1 < items.length) {
                int n = locker.removeItem(items[item + 1], 1);
                assertEquals("remove is wrong", -1, n);
            } else {
                int n = locker.removeItem(items[item - 1], 1);
                assertEquals("remove is wrong", -1, n);
            }
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test remove non existing  item, count check
     */
    @Test
    public void testRemoveNonExistIt() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            if (item + 1 < items.length) {
                int n = locker.removeItem(items[item + 1], 1);
                assertEquals("remove is wrong", 0,
                        locker.getItemCount(items[item + 1].getType()));
            } else {
                int n = locker.removeItem(items[item - 1], 1);
                assertEquals("remove is wrong", 0,
                        locker.getItemCount(items[item + 1].getType()));
            }
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test removing negative amount
     */
    @Test
    public void testRemoveNegItem() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            int n = locker.removeItem(items[item], -1);
            assertEquals("remove is wrong", -1, n);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test success removing
     */
    @Test
    public void testRemoveSuccess() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            int n = locker.removeItem(items[item], 1);
            assertEquals("remove is wrong", 0, n);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test remove failure
     */
    @Test
    public void testRemoveFail() {
        clean();
        int item = indexItem();
        if (item > 0) {
            locker.addItem(items[item], 2);
            int n = locker.removeItem(null, 1);
            assertEquals("remove is wrong", -1, n);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }
}

