/**
 * A class that represents a hash-set based on chaining.
 * @author Natalia Shuklin, cs : natashashuklin
 */
public class OpenHashSet extends SimpleHashSet {
    /**
     * the hashtable
     */
    private OpenHashBucket[] hashTable;
    /**
     * increase/decrease table size value
     */
    private static final int HASH_TABLE_GROWTH_FACTOR = 2;

    /**
     * A default constructor.Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.hashTable = new OpenHashBucket[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor  The upper load factor of the hash table.
     * @param lowerLoadFactor  The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        this.hashTable = new OpenHashBucket[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data  Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data) {
        super();
        this.hashTable = new OpenHashBucket[INITIAL_CAPACITY];
        for( String value : data) {
            this.add(value);
        }
    }
    /**
     * The current capacity (number of cells) of the table.
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.hashTable.length;
    }

    /**
     * rehashes hash set table when adding/deleting element causes load factor to be above upperfactor
     * or below lowerFactor of the set
     * @param capacity the new capacity for rehashed set
     */
    private void rehashTable(int capacity) {
        OpenHashBucket [] rehashed = hashTable.clone();
        this.hashTable = new OpenHashBucket[capacity];
        this.capacityLessOne = capacity - 1;
        this.currentCapacity = capacity;
        this.elementsCounter = 0;
        for(OpenHashBucket cell : rehashed) {
            if(cell != null) {
                for( String value: cell.getChainedArray()){
                    this.add(value);
                }
            }
        }

    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue) {
        if(!this.contains(newValue)) {
            int index = this.clamp(newValue.hashCode());
            if(this.hashTable[index] == null) {
                this.hashTable[index] = new OpenHashBucket();
            }
            this.elementsCounter ++ ;
            this.hashTable[index].add(newValue);

            if(this.getCurrentLoadFactor() > this.getUpperLoadFactor()) {
                this.rehashTable(this.capacity() * HASH_TABLE_GROWTH_FACTOR);
            }
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal) {
        int index = this.clamp(searchVal.hashCode());
        if(this.hashTable[index]!=null){
            return this.hashTable[index].contains(searchVal);
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete) {
        if(this.contains(toDelete)) {
            float load = (float)(this.size()-1)/this.capacity();
            if(load < this.getLowerLoadFactor()) {
                this.rehashTable(this.capacity()/HASH_TABLE_GROWTH_FACTOR);
            }
            this.elementsCounter -- ;
            return hashTable[this.clamp(toDelete.hashCode())].delete(toDelete);
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return this.elementsCounter;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index the given index to perform clamps on
     * @return the clampes correctly index for the hash set
     */
    protected int clamp(int index){
        return index&(this.capacityLessOne);
    }
}
