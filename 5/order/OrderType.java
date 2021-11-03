package order;

import java.io.File;
import java.util.LinkedList;

/**
 * class represents ordering by type of file
 *
 * @author natashashuklin cs
 */
public class OrderType extends Order {
    /*
    pair array of sorted result
     */
    private Pair[] sortedByType;
    /*
    is reverse flag
     */
    private boolean isReverse;
    /*
    sortef file names
     */
    private String[] sortedFileNames;
    /*
    init zero value
    */
    private static final int ZERO = 0;
    /*
    init one value
     */
    private static final int ONE = 1;
    /*
    division by half for sort use
     */
    private static final int TWO = 2;
    /*
    a dot delimeter
     */
    private static final char DOT = '.';
    /*
    negative one index check return value
    */
    private static final int MINUS_ONE = -1;

    /**
     * constructs the order by type for given files list, sorts and saves it
     *
     * @param files     the files to sort
     * @param isReverse isreverse flag
     */
    public OrderType(LinkedList<File> files, boolean isReverse) {
        this.isReverse = isReverse;
        sortedByType = new Pair[files.size()];
        int i = ZERO;
        for (File file : files) {
            String extension;
            int index = file.getName().lastIndexOf(DOT);
            if (index == MINUS_ONE || index == ZERO || index + ONE >= file.getName().length()) {
                extension = "";
            } else
                extension = file.getName().substring(index + ONE);
            sortedByType[i] = new Pair(file.getAbsolutePath(), extension);
            i++;
        }
        sort(sortedByType, ZERO, sortedByType.length - ONE);
        sortedFileNames = new String[sortedByType.length];
        sortedFileNames = getSorted();
    }

    /*
    reverse merge sort help function
     */
    private void reverseMerge(Pair[] types, int left, int middle, int right) {
        int first = middle - left + ONE;
        int second = right - middle;
        Pair[] leftArr = new Pair[first];
        Pair[] rightArr = new Pair[second];
        for (int i = 0; i < first; ++i)
            leftArr[i] = types[left + i];
        for (int j = 0; j < second; ++j)
            rightArr[j] = types[middle + ONE + j];
        int i = ZERO, j = ZERO;
        int k = left;
        while (i < first && j < second) {
            if (leftArr[i].getType().compareTo(rightArr[j].getType()) == ZERO) {
                if (leftArr[i].getName().compareTo(rightArr[j].getName()) >= ZERO) {
                    types[k] = leftArr[i];
                    i++;
                } else {
                    types[k] = rightArr[j];
                    j++;
                }
                k++;
            } else {
                if (leftArr[i].getType().compareTo(rightArr[j].getType()) > ZERO) {
                    types[k] = leftArr[i];
                    i++;
                } else {
                    types[k] = rightArr[j];
                    j++;
                }
                k++;
            }
        }
        while (i < first) {
            types[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < second) {
            types[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /*
    merge sort help function
     */
    private void merge(Pair[] types, int left, int middle, int right) {
        int first = middle - left + ONE;
        int second = right - middle;
        Pair[] leftArr = new Pair[first];
        Pair[] rightArr = new Pair[second];
        for (int i = 0; i < first; ++i)
            leftArr[i] = types[left + i];
        for (int j = 0; j < second; ++j)
            rightArr[j] = types[middle + ONE + j];
        int i = ZERO, j = ZERO;
        int k = left;
        while (i < first && j < second) {
            if (leftArr[i].getType().compareTo(rightArr[j].getType()) == ZERO) {
                if (leftArr[i].getName().compareTo(rightArr[j].getName()) <= ZERO) {
                    types[k] = leftArr[i];
                    i++;
                } else {
                    types[k] = rightArr[j];
                    j++;
                }
                k++;
            } else {
                if (leftArr[i].getType().compareTo(rightArr[j].getType()) < ZERO) {
                    types[k] = leftArr[i];
                    i++;
                } else {
                    types[k] = rightArr[j];
                    j++;
                }
                k++;
            }
        }
        while (i < first) {
            types[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < second) {
            types[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /*
    sorts array using merge sort alg.
     */
    private void sort(Pair[] sizes, int left, int right) {
        if (left < right) {
            int middle = (left + right) / TWO;
            sort(sizes, left, middle);
            sort(sizes, middle + ONE, right);
            if (this.isReverse)
                reverseMerge(sizes, left, middle, right);
            else
                merge(sizes, left, middle, right);
        }
    }

    /**
     * returns sorted files
     *
     * @return sorted files names
     */
    public String[] getSorted() {
        String[] arr = new String[sortedByType.length];
        for (int i = 0; i < arr.length; i++) {
            File f = new File(sortedByType[i].getName());
            arr[i] = f.getName();
        }
        return arr;
    }

    /*
    pair used for sorting files by abs path and type, and then getting the file names
     */
    private class Pair {
        /*
        name of file abs path
         */
        String fileName;
        /*
        file type
         */
        String type;

        /*
        constructor
         */
        private Pair(String file, String type) {
            this.fileName = file;
            this.type = type;
        }

        /*
        gets abs path file
         */
        private String getName() {
            return this.fileName;
        }

        /*
        gets type file
         */
        private String getType() {
            return this.type;
        }
    }
}
