/**
 * A class that has a main method that measures the run-times for "Performance Analysis"
 * @author cs natashashuklin Natalia Shuklin
 */
public class SimpleSetPerformanceAnalyzer {

    public static final int WARMUP = 70000;
    public static final int TEST_TIMES = 70000;
    /**
     * the unique id given by test helper class for linked list
     */
    public static final int LINKED_LIST = 3;
    private static final int NUM_OF_DATA_STRUCTS = 5;

    public static void main(String[] args) {
        //analyzer for data1 and data 2 set up
        TestDataStructures analyzeData1 = new TestDataStructures();
        TestDataStructures analyzeData2= new TestDataStructures();
        long[] measuresHi1 = new long[NUM_OF_DATA_STRUCTS];
        for (int i = 0; i < 5; i++) {
            measuresHi1[i] = 0;
        }
        long[] measuresHi2 = new long[NUM_OF_DATA_STRUCTS];
        for (int i = 0; i < 5; i++) {
            measuresHi2[i] = 0;
        }
        long[] measuresNum1 = new long[NUM_OF_DATA_STRUCTS];
        for (int i = 0; i < 5; i++) {
            measuresNum1[i] = 0;
        }
        long[] measuresNum2 = new long[NUM_OF_DATA_STRUCTS];
        for (int i = 0; i < 5; i++) {
            measuresNum2[i] = 0;
        }
        //get the files into array of strings
        String[] newArray1 = Ex4Utils.file2array(System.getProperty("user.dir")+"/src/data1.txt");
        String[] newArray2 = Ex4Utils.file2array(System.getProperty("user.dir")+"/src/data2.txt");

        System.out.println("Analyzing begins for data1!!!!! ");
        analyzeData1.addDataToCollections(newArray1);
        System.out.println("data1 done!!! ");
        System.out.println("Analyzing begins for data2!!!! ");
        analyzeData2.addDataToCollections(newArray2);
        System.out.println("data2 done!!!! ");
        //warmup before contain operations, except linked list
        for (int i = 0; i < WARMUP; i++) {
            for (int k = 0; k < NUM_OF_DATA_STRUCTS; k++) {
                if(k!=LINKED_LIST) {
                    analyzeData1.isContains(k, "hi");
                    analyzeData1.isContains(k, "-13170890158");
                    analyzeData2.isContains(k, "23");
                    analyzeData2.isContains(k, "hi");
                }
            }
        }
        for (int i = 0; i < TEST_TIMES; i++) {
            for (int k = 0; k < NUM_OF_DATA_STRUCTS; k++) {
                if(k!=LINKED_LIST) {
                    measuresHi1[k] += analyzeData1.isContains(k, "hi");
                    measuresNum1[k] += analyzeData1.isContains(k, "-13170890158");
                    measuresNum2[k] += analyzeData2.isContains(k, "23");
                    measuresHi2[k] += analyzeData2.isContains(k, "hi");
                }
                else {
                    measuresHi1[k] += analyzeData1.isContains(k, "hi");
                    measuresNum1[k] += analyzeData1.isContains(k, "-13170890158");
                    measuresNum2[k] += analyzeData2.isContains(k, "23");
                    measuresHi2[k] += analyzeData2.isContains(k, "hi");
                }
            }
        }
        //divide time for approximation of the operations
        for (int k = 0; k < NUM_OF_DATA_STRUCTS; k++) {
            if( k == LINKED_LIST) {
                System.out.println(analyzeData1.dataStructureType[k] + "Contains hi1 in :" +
                        measuresHi1[k] / 7000);
                System.out.println(analyzeData1.dataStructureType[k] + "Contains num1 in :" +
                        measuresNum1[k] / 7000);
                System.out.println(analyzeData2.dataStructureType[k] + "Contains num2 in :" +
                        measuresNum2[k] / 7000);
                System.out.println(analyzeData2.dataStructureType[k] + "Contains hi2 in :" +
                        measuresHi2[k] / 7000);
            }
            else {
                System.out.println(analyzeData1.dataStructureType[k] + "Contains hi1 in :" +
                        measuresHi1[k] / 70000);
                System.out.println(analyzeData1.dataStructureType[k] + "Contains num1 in :" +
                        measuresNum1[k] / 70000);
                System.out.println(analyzeData2.dataStructureType[k] + "Contains num2 in :" +
                        measuresNum2[k] / 70000);
                System.out.println(analyzeData2.dataStructureType[k] + "Contains hi2 in :" +
                        measuresHi2[k] / 70000);
            }
        }
    }

}
