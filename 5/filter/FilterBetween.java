package filter;

import java.io.File;

/**
 * filter between class
 */
public class FilterBetween extends Filter {
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

    /**
     * constrcuts filter object- performs the filter
     *
     * @param sourceFiles the files
     * @param command     the command
     * @param isNot       isnot flag
     */
    public FilterBetween(File[] sourceFiles, String command, boolean isNot) {
        String[] arr = command.split(REGEX);
        double first = Double.parseDouble(arr[FIRST]);
        double second = Double.parseDouble(arr[SECOND]);
        if (!isNot) {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    double space = (double) file.length() / BYTES;
                    if (space <= second && space >= first) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        } else {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    double space = (double) file.length() / BYTES;
                    if (space > second || space < first) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        }
    }
}
