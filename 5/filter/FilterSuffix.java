package filter;

import java.io.File;

/**
 * class represents a filter suffix
 */
public class FilterSuffix extends Filter {
    /*
    delimeter
    */
    private static final String REGEX = "#";
    /*
    between first value
     */
    private static final int FIRST = 1;
    /*
    number of bytes in kb
    */
    private static final int BYTES = 1024;

    /**
     * constrcuts filter object- performs the filter
     *
     * @param sourceFiles the files
     * @param command     the command
     * @param isNot       isnot flag
     */
    public FilterSuffix(File[] sourceFiles, String command, boolean isNot) {
        String[] arr = command.split(REGEX);
        if (!isNot) {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (file.getName().endsWith(arr[FIRST])) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        } else {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (!file.getName().endsWith(arr[FIRST])) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        }
    }
}
