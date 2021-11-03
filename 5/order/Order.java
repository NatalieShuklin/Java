
package order;

import java.io.File;
import java.util.LinkedList;

/**
 * Abstract class represents Order class
 *
 * @author natashashuklin cs
 */
abstract public class Order {
    /**
     * ABS order type
     */
    public static final String ABS = "abs";
    /**
     * TYPE order type
     */
    public static final String TYPE = "type";
    /**
     * Size order type
     */
    public static final String SIZE = "size";
    /**
     * the valid orders
     */
    static LinkedList<String> orders = new LinkedList<>();
    /*
    the ordered files
     */
    private static String[] filesOrder;
    /*
    zero size
     */
    private static final int ZERO = 0;

    /**
     * creates the orders - initiates valid orders available for the program to use
     */
    protected static void createOrders() {
        orders = new LinkedList<>();
        orders.add(ABS);
        orders.add(TYPE);
        orders.add(SIZE);
    }

    /**
     * Returns the valid ordered list available
     *
     * @return linked list of valid orders
     */
    public static LinkedList<String> getOrders() {
        if (orders.size() == ZERO)
            createOrders();
        return orders;
    }

    /**
     * performs the order, given in input
     *
     * @param order     the order input
     * @param files     the files to perform the current order on
     * @param isReverse true/false if order is reverse or not
     */
    public static void performOrder(String order, LinkedList<File> files, boolean isReverse) {
        createOrders();
        switch (order) {
            case ABS:
                OrderAbs abs = new OrderAbs(files, isReverse);
                filesOrder = abs.getSorted();
                break;
            case TYPE:
                OrderType type = new OrderType(files, isReverse);
                filesOrder = type.getSorted();
                break;
            case SIZE:
                OrderSize size = new OrderSize(files, isReverse);
                filesOrder = size.getSorted();
                break;
        }
    }

    /**
     * checks if order name given is a valid name in the valid order lists
     *
     * @param str the given order command name
     * @return true if name is valid, else return false
     */
    public static boolean isValidOrderName(String str) {
        if (orders.size() == ZERO)
            createOrders();
        return orders.contains(str);
    }

    /**
     * gets the sorted files by current order
     *
     * @return ordered files
     */
    public static String[] getSortedFiles() {
        return filesOrder;
    }
}
