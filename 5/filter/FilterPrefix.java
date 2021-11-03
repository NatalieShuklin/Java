package filter;

import java.io.File;

/**
 * CLASS represents a filter prefix
 */
public class FilterPrefix extends Filter {
    /*
    delimeter
    */
    private static final String REGEX = "#";
    /*
    between first value
     */
    private static final int FIRST = 1;

    /**
     * constrcuts filter object- performs the filter
     *
     * @param sourceFiles the files
     * @param command     the command
     * @param isNot       isnot flag
     */
    public FilterPrefix(File[] sourceFiles, String command, boolean isNot) {
        String[] arr = command.split(REGEX);
        if (!isNot) {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (file.getName().startsWith(arr[FIRST])) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        } else {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (!file.getName().startsWith(arr[FIRST])) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        }
    }
}
