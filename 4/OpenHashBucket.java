import java.util.Collection;
import java.util.LinkedList;

/**
 * Define a wrapper-class that has a LinkedList<String> and delegates methods to it
 * @author Natalia Shuklin cs natashashuklin
 */
public class OpenHashBucket implements SimpleSet {
    /*
     * the Linked list for keeping elements of hash table, that hashed into same cell
     */
    private LinkedList<String> chainedArray;

    /**
     * default constructor of OpenHashBucket
     */
    public OpenHashBucket() {
        chainedArray = new LinkedList<>();
    }

    /**
     * returns the chained elements in the same cell in hashtable
     * @return the chained elements in the same cell in hashtable
     */
    protected LinkedList<String> getChainedArray(){
        return chainedArray;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if(!this.chainedArray.contains(newValue)) {
            this.chainedArray.add(newValue);
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        return this.chainedArray.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if( this.chainedArray.contains(toDelete)) {
            this.chainedArray.remove(toDelete);
            return true;
        }
        return false;
    }

    /** The number of elements currently in the set
     * @return The number of elements currently in the set
     */
    public int size() {
        return this.chainedArray.size();
    }
}
