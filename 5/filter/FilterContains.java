package filter;

import java.io.File;

/**
 * FILTER contains class
 */
public class FilterContains extends Filter {
    /*
    delimeter
     */
    private static final String REGEX = "#";
    /*
    between first value
     */
    private static final int FIRST = 1;
    /*
    between sec. value
     */
    private static final int SECOND = 2;
    /*
    number of bytes in kb
    */
    private static final int BYTES = 1024;
    /*
    INIT VALUE
    */
    private static final int ZERO = 0;

    /**
     * constrcuts filter object- performs the filter
     *
     * @param sourceFiles the files
     * @param command     the command
     * @param isNot       isnot flag
     */
    public FilterContains(File[] sourceFiles, String command, boolean isNot) {
        String[] arr = command.split(REGEX);
        if (arr.length == FIRST) {
            String[] b = new String[SECOND];
            b[ZERO] = arr[ZERO];
            b[FIRST] = "";
            arr = b;
        }
        if (!isNot) {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (file.getName().contains(arr[FIRST])) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        } else {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (!file.getName().contains(arr[FIRST])) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        }
    }
}
