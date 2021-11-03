package order;

import java.io.File;
import java.util.LinkedList;

/**
 * Class represents ordering files by size using merge sort
 *
 * @author natashashuklin cs
 */
public class OrderSize extends Order {
    /*
    array of pairs of the sorted files by size pair: <file abs path, size>
     */
    private Pair[] sortedBySize;
    /*
    is reverse flag
     */
    private boolean isReverse;
    /*
    sorted files
     */
    private String[] sortedFileNames;
    /*
    zero init value
     */
    private static final int ZERO = 0;
    /*
    number of bytes in kb
     */
    private static final int BYTES = 1024;
    /*
    init one value
    */
    private static final int ONE = 1;
    /*
    division by half for sort use
    */
    private static final int TWO = 2;

    /**
     * constrcuts the order by size object, performs the sorting and updates the current sorted list
     *
     * @param files     files to sort
     * @param isReverse isreverse flag
     */
    public OrderSize(LinkedList<File> files, boolean isReverse) {
        this.isReverse = isReverse;
        sortedBySize = new Pair[files.size()];
        int i = ZERO;
        for (File file : files) {
            sortedBySize[i] = new Pair(file.getAbsolutePath(), (double) file.length() / BYTES);
            i++;
        }
        sort(sortedBySize, ZERO, sortedBySize.length - ONE);
        sortedFileNames = new String[sortedBySize.length];
        sortedFileNames = getSorted();
    }

    /*
    reverse merge sort help function to sort the given array
     */
    private void reverseMerge(Pair[] sizes, int left, int middle, int right) {
        int first = middle - left + ONE;
        int second = right - middle;
        Pair[] leftArr = new Pair[first];
        Pair[] rightArr = new Pair[second];
        for (int i = 0; i < first; ++i)
            leftArr[i] = sizes[left + i];
        for (int j = 0; j < second; ++j)
            rightArr[j] = sizes[middle + ONE + j];
        int i = ZERO, j = ZERO;
        int k = left;
        while (i < first && j < second) {
            if (leftArr[i].getSize() == rightArr[j].getSize()) {
                if (leftArr[i].getName().compareTo(rightArr[j].getName()) >= ZERO) {
                    sizes[k] = leftArr[i];
                    i++;
                } else {
                    sizes[k] = rightArr[j];
                    j++;
                }
                k++;
            } else {
                if (leftArr[i].getSize() > rightArr[j].getSize()) {
                    sizes[k] = leftArr[i];
                    i++;
                } else {
                    sizes[k] = rightArr[j];
                    j++;
                }
                k++;
            }
        }
        while (i < first) {
            sizes[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < second) {
            sizes[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /*
    merge sort help function, merges arrays
     */
    private void merge(Pair[] sizes, int left, int middle, int right) {
        int first = middle - left + ONE;
        int second = right - middle;
        Pair[] leftArr = new Pair[first];
        Pair[] rightArr = new Pair[second];
        for (int i = 0; i < first; ++i)
            leftArr[i] = sizes[left + i];
        for (int j = 0; j < second; ++j)
            rightArr[j] = sizes[middle + ONE + j];

        int i = ZERO, j = ZERO;
        int k = left;
        while (i < first && j < second) {
            if (leftArr[i].getSize() == rightArr[j].getSize()) {
                if (leftArr[i].getName().compareTo(rightArr[j].getName()) <= ZERO) {
                    sizes[k] = leftArr[i];
                    i++;
                } else {
                    sizes[k] = rightArr[j];
                    j++;
                }
                k++;
            } else {
                if (leftArr[i].getSize() < rightArr[j].getSize()) {
                    sizes[k] = leftArr[i];
                    i++;
                } else {
                    sizes[k] = rightArr[j];
                    j++;
                }
                k++;
            }
        }
        while (i < first) {
            sizes[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < second) {
            sizes[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /*
    merge sort alg.
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
     * gets the sorted filies list
     *
     * @return the sorted files
     */
    public String[] getSorted() {
        String[] arr = new String[sortedBySize.length];
        for (int i = 0; i < arr.length; i++) {
            File f = new File(sortedBySize[i].getName());
            arr[i] = f.getName();
        }
        return arr;
    }

    /*
    inner class to represent a pair of file abs path and its size- for sorting by two values
    and then returning names of files
     */
    private class Pair {
        /*
        path abs of file
         */
        String fileName;
        /*
        size file
         */
        double size;

        /*
        constructor
         */
        private Pair(String file, double size) {
            this.fileName = file;
            this.size = size;
        }

        /*
        returns path
         */
        private String getName() {
            return this.fileName;
        }

        /*
        returns size
         */
        private double getSize() {
            return this.size;
        }
    }

}
