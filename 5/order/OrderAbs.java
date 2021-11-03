package order;

import java.io.File;
import java.util.LinkedList;

/**
 * This class represents ordering by Absolute path of file in ascending order
 *
 * @author natashashuklin cs
 */
public class OrderAbs extends Order {
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
     * the ordered files by abs. path
     */
    private String[] absolute;
    /*
    if given command is should be reverse it will be true, else false
     */
    private boolean isReverse;

    /**
     * creates the order abs object, calls the sort on the files list, and updates the absolute
     * member with correct output
     *
     * @param sourceFiles the given files to order
     * @param isReverse   is reverse or not flag (true if reverse is in command)
     */
    protected OrderAbs(LinkedList<File> sourceFiles, boolean isReverse) {
        this.isReverse = isReverse;
        absolute = new String[sourceFiles.size()];
        int i = ZERO;
        for (File file : sourceFiles) {
            absolute[i] = file.getAbsolutePath();
            i++;
        }
        sort(absolute, ZERO, absolute.length - ONE);
        for (int j = 0; j < absolute.length; j++) {
            File f = new File(absolute[j]);
            absolute[j] = f.getName();
        }
    }

    /*
    reverse merge sort help function to sort the given array
     */
    private void reverseMerge(String[] paths, int left, int middle, int right) {
        int first = middle - left + ONE, second = right - middle;
        String[] leftArr = new String[first];
        String[] rightArr = new String[second];
        for (int i = 0; i < first; ++i)
            leftArr[i] = paths[left + i];
        for (int j = 0; j < second; ++j)
            rightArr[j] = paths[middle + ONE + j];
        int i = ZERO, j = ZERO, k = left;
        while (i < first && j < second) {
            if (leftArr[i].compareTo(rightArr[j]) >= ZERO) {
                paths[k] = leftArr[i];
                i++;
            } else {
                paths[k] = rightArr[j];
                j++;
            }
            k++;
        }
        while (i < first) {
            paths[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < second) {
            paths[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /*
     * the merge function of the sorting alg. for Merge Sort.
     * @param paths  the array to sort
     * @param left   left index of arr
     * @param middle middle index of arr
     * @param right  right index of arr
     */
    private void merge(String[] paths, int left, int middle, int right) {
        int first = middle - left + ONE, second = right - middle;
        String[] leftArr = new String[first];
        String[] rightArr = new String[second];
        for (int i = 0; i < first; ++i)
            leftArr[i] = paths[left + i];
        for (int j = 0; j < second; ++j)
            rightArr[j] = paths[middle + ONE + j];
        int i = ZERO, j = ZERO, k = left;
        while (i < first && j < second) {
            if (leftArr[i].compareTo(rightArr[j]) <= ZERO) {
                paths[k] = leftArr[i];
                i++;
            } else {
                paths[k] = rightArr[j];
                j++;
            }
            k++;
        }
        while (i < first) {
            paths[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < second) {
            paths[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /*
     * sort  main function, Merge sort algorithm, sorts the array of abs paths of files
     * @param paths given array of abs. paths of filef to sort
     * @param left left index of arr
     * @param right right index of arr
     */
    private void sort(String[] paths, int left, int right) {
        if (left < right) {
            int middle = (left + right) / TWO;
            sort(paths, left, middle);
            sort(paths, middle + ONE, right);
            if (this.isReverse)
                reverseMerge(paths, left, middle, right);
            else
                merge(paths, left, middle, right);
        }
    }

    /**
     * returns the sorted array of paths
     *
     * @return sorted abs paths
     */
    public String[] getSorted() {
        return this.absolute;
    }
}
