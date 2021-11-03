
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 *class help for analyzer, each collection is represented by unique index int
 * and uses the index as to identify the specific collection
 * @author Natalia Shuklin cs natashashuklin
 */
public class TestDataStructures {
    private  SimpleSet[] arrayOfDataStructures;
    public String[] dataStructureType;
    private static final int NUM_OF_DATA_STRUCTS = 5;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;

    /**
     * Default constructor. sets the array with the collections to test
     * each collection gets it's own unique id that used for all tests
     */
    public TestDataStructures(){
        TreeSet<String> treeSet = new TreeSet<>();
        LinkedList<String> linkedList = new LinkedList<>();
        HashSet<String> hashSet = new HashSet<>();
        dataStructureType= new String[NUM_OF_DATA_STRUCTS];
        arrayOfDataStructures = new SimpleSet[NUM_OF_DATA_STRUCTS];
        dataStructureType[FIRST] = "OpenHashSet";
        arrayOfDataStructures[FIRST] = new OpenHashSet();
        dataStructureType[SECOND] = "ClosedHashSet";
        arrayOfDataStructures[SECOND] = new ClosedHashSet();
        dataStructureType[THIRD] = "Java’s TreeSet";
        arrayOfDataStructures[THIRD] = new CollectionFacadeSet(treeSet);
        dataStructureType[FOURTH] = "Java’s LinkedList";
        arrayOfDataStructures[FOURTH] = new CollectionFacadeSet(linkedList);
        dataStructureType[FIFTH]= "Java’s HashSet";
        arrayOfDataStructures[FIFTH] = new CollectionFacadeSet(hashSet);
    }

    /**
     * adds data from files into collection and measures it's time
     * @param data: string array of data
     */
    public void addDataToCollections(String[] data){
        int dataStructIndex = 0;
        for (SimpleSet simpleSet : this.arrayOfDataStructures) {
            long timeBefore = System.nanoTime();
            for (String value : data ) {
                simpleSet.add(value);
            }
            long difference = System.nanoTime() - timeBefore;
            System.out.println(this.dataStructureType[dataStructIndex] + "adding time measure:" + difference / 1000000);
            dataStructIndex++;
        }
    }
    /**
     * measures times for collection and search value
     * calls contain() method.
     * @param structure index of collection
     * @param value some value to search
     * @return returns time measured in nano secs
     */
    public long isContains(int structure, String value){
        long timeBefore = System.nanoTime();
        this.arrayOfDataStructures[structure].contains(value);
        return  System.nanoTime() - timeBefore;
    }


}
