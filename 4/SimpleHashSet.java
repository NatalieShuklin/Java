/**
 *  A superclass for implementations of hash-sets implementing the SimpleSet interface.
 * @author Natalia Shuklin, cs : natashashuklin
 */
public abstract class SimpleHashSet implements SimpleSet {

    /**
     * Describes the higher load factor of a newly created hash set.
     */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    /**
     * Describes the lower load factor of a newly created hash set.
     */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    /**
     * Describes the capacity of a newly created hash set.
     */
    protected static final int INITIAL_CAPACITY = 16;
    /*
     * the lower load factor of the hash set
     */
    private float lowerLoadFactor;
    /*
     * the upper load factor of the hash set
     */
    private float higherLoadFactor;
    /**
     * the current capacity of the hash set
     */
    protected int currentCapacity;

    protected int capacityLessOne;
    /*
     * elements counter of hash table init value
     */
    private static final int INIT_ELEMENTS_COUNT = 0;
    /**
     * elements counter of the hash table
     */
    protected int elementsCounter;

    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY
     * and DEFAULT_HIGHER_CAPACITY.
     */
    protected SimpleHashSet() {
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        this.higherLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.currentCapacity = INITIAL_CAPACITY;
        this.capacityLessOne = INITIAL_CAPACITY - 1;
        this.elementsCounter = INIT_ELEMENTS_COUNT;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY.
     * @param upperLoadFactor the upper load factor
     * @param lowerLoadFactor the lower loadfactor
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.lowerLoadFactor = lowerLoadFactor;
        this.higherLoadFactor = upperLoadFactor;
        this.currentCapacity = INITIAL_CAPACITY;
        this.elementsCounter = INIT_ELEMENTS_COUNT;
        this.capacityLessOne = INITIAL_CAPACITY - 1;
    }

    /**
     * returns capacity of the hash set
     * @return the capacity of the hash set
     */
    public abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index the given index to perform clamps on
     * @return the clampes correctly index for the hash set
     */
    protected abstract int clamp(int index);

    /**
     * get current load factor of the hash table
     * @return current load factor of the hash table
     */
    protected float getCurrentLoadFactor(){
        return (float)(this.size())/(this.capacity());
    }
    /**
     * returns the lower load factor of the hash set
     * @return the lower load factor of the hash set
     */
    protected float getLowerLoadFactor() {
        return this.lowerLoadFactor;
    }
    /**
     * returns the upper load factor of the hash set
     * @return the upper load factor of the hash set
     */
    protected float getUpperLoadFactor() {
        return this.higherLoadFactor;
    }

}
