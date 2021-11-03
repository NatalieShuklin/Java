
import org.junit.*;

import static org.junit.Assert.*;

import oop.ex3.spaceship.*;


public class LongTermTest {

    LongTermStorage longTerm;
    private Item[] items;

    /**
     * set test object before testing
     */
    @Before
    public void createTestObj() {
        longTerm = new LongTermStorage();
        items = ItemFactory.createAllLegalItems();
    }

    /*
    gets index of item in items that doesnt pass the cap. of the storage
     */
    private int indexItem() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].getVolume() < longTerm.getCapacity()) {
                return i;
            }
        }
        return -1;
    }

    /*
    cleans storage
     */
    private void clean() {
        longTerm.longStorage.clear();
    }

    /*
    fills the whole storage
     */
    private void fill() {
        int n = longTerm.getAvailableCapacity();
        int index = indexItem();
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                longTerm.addItem(items[index], 1);
            }
        }
    }

    /**
     * test addition
     */
    @Test
    public void testAdd() {
        clean();
        int index = indexItem();
        if (index > 0) {
            longTerm.addItem(items[index], 1);
            assertEquals("not correct addition", 1,
                    longTerm.getItemCount(items[index].getType()));
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller" +
                    " volume item needed");
        }
    }

    /**
     * test add negative amount
     */
    @Test
    public void testAddNeg() {
        clean();
        int index = indexItem();
        if (index > 0) {
            //longTerm.addItem(items[index], 1);
            int n = longTerm.addItem(items[index], -1);
            assertEquals("wrong return value", -1, n);
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }

    /**
     * test addition when cap is full
     */
    @Test
    public void testAddFull() {
        clean();
        fill();
        int n = longTerm.addItem(items[0], 1);
        assertEquals("wrong return value", -1, n);
    }

    /**
     * test available capacity
     */
    @Test
    public void testCap() {
        clean();
        int index = indexItem();
        if (index > 0) {
            //longTerm.addItem(items[index], 1);
            int n = longTerm.addItem(items[index], 1);
            int cur = longTerm.getCapacity();
            int m = cur - items[index].getVolume();
            assertEquals("wrong capacity", m, longTerm.getAvailableCapacity());
        } else {
            System.out.println("error - or the capacity of locker too small or a smaller volume item needed");
        }
    }
}
