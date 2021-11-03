

/**
 * Class represents a hash-set based on closed-hashing with quadratic probing.
 *
 * @author Natalia Shuklin, cs : natashashuklin
 */
public class ClosedHashSet extends SimpleHashSet {

    /*
    the hash table
     */
    private java.lang.String[] hashTable;
    private static final int HASH_TABLE_GROWTH_FACTOR = 2;
    private static final int DELETED = -1;
    private static final String NULL_STRING = "";
    private int deletedValueIndex = DELETED;

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashTable = new java.lang.String[INITIAL_CAPACITY];
    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper
     * load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        this.hashTable = new java.lang.String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values
     * should be ignored. The new table has the default values of initial capacity (16), upper load factor
     * (0.75), and lower load factor (0.25).
     *
     * @param data - Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data) {
        super();
        this.hashTable = new java.lang.String[INITIAL_CAPACITY];
        for (String value : data)
            this.add(value);
    }

    /**
     * returns capacity of the hash set
     *
     * @return the capacity of the hash set
     */
    public int capacity() {
        return this.currentCapacity;
    }


    /**
     * checks if an element in index  was deleted
     *
     * @param index
     * @return
     */
    boolean isDeletedElem(int index) {
        if (this.hashTable[index].equals(NULL_STRING)) {
            if (this.deletedValueIndex == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     *
     * @param index the given index to perform clamps on
     * @return the clampes correctly index for the hash set
     */
    protected int clamp(int index) {
        int hashedIndex = 0;
        for (int i = 0; i < this.capacity(); i++) {
            hashedIndex =  (index + (i + i * i) / 2) & (this.capacityLessOne);
            if (this.hashTable[hashedIndex] == null) {
                return hashedIndex;
            }
            else if (this.isDeletedElem(hashedIndex)) {
                break;
            }
        }
        return this.deletedValueIndex;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue) {
        if (!this.contains(newValue)) {
            float currentLoadFactor = (float)(this.size() + 1) / this.capacity();
            if (currentLoadFactor > this.getUpperLoadFactor()) {
                this.rehashTable(this.capacity() * HASH_TABLE_GROWTH_FACTOR);
            }
            int index = getHashedValue(newValue);
            this.hashTable[this.clamp(newValue.hashCode())] = newValue;
            this.elementsCounter++;
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal) {
        int index = getHashedValue(searchVal);
        if (this.hashTable[index] != null) {
            if (this.hashTable[index].equals(searchVal) && index != this.deletedValueIndex) {
                return true;
            }
        }
        return false;
    }

    /** hashes the value into indes in hash table
     * @param value value
     * @return hashed value
     */
    private int getHashedValue(java.lang.String value) {
        int hashedIndex = 0;
        for (int i = 0; i < this.capacity(); i++) {
            hashedIndex = (value.hashCode() + (i + (i * i)) / 2) & (this.capacityLessOne);
            if (this.hashTable[hashedIndex] != null) {
                if (this.hashTable[hashedIndex].equals(value) && hashedIndex != this.deletedValueIndex) {
                    break;
                }
            }
        }
        return hashedIndex;
    }

    /** remove the value from hash table and mark the deleted index
     * @param index to remove
     */
    private void removeElem(int index) {
        if (this.size() == 1) {
            this.hashTable[index] = null;
            this.elementsCounter--;
        } else {
            this.elementsCounter--;
            this.hashTable[index] = NULL_STRING;
            this.deletedValueIndex = index;
        }
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete) {
        if (this.contains(toDelete)) {
            int index = getHashedValue(toDelete);
            removeElem(index);
            if (this.getCurrentLoadFactor() < this.getLowerLoadFactor()) {
                this.rehashTable(this.capacity() / HASH_TABLE_GROWTH_FACTOR);
            }
            return true;
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return this.elementsCounter;
    }

    /** rehash the table
     * @param capacity new capacity
     */
    private void rehashTable(int capacity) {
        java.lang.String[] copy= this.hashTable.clone();
        this.elementsCounter = 0;
        this.currentCapacity = capacity;
        this.capacityLessOne = capacity - 1;

        this.hashTable = new java.lang.String[capacity];
        if (capacity <= 1) {
            this.capacityLessOne = capacity;
        }
        for (String value : copy) {
            if (value != null) {
                if (!(value.equals(NULL_STRING) && this.deletedValueIndex != DELETED)) {
                    this.add(value);
                }
            }
        }
    }
}



