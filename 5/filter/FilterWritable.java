package filter;

import java.io.File;

/**
 * CLASS REPRESENTS WRITABLE FILTER
 */
public class FilterWritable extends Filter {
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
    public FilterWritable(File[] sourceFiles, String command, boolean isNot) {
        String[] arr = command.split(REGEX);
        if (isNot) {
            notFilter(sourceFiles, command);
        } else {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (arr[FIRST].equals(YES)) {
                        if (file.canWrite()) {
                            namesList.add(file.getName());
                            afterFilter.add(file);
                        }
                    } else if (arr[FIRST].equals(NO)) {
                        if (!file.canWrite()) {
                            namesList.add(file.getName());
                            afterFilter.add(file);
                        }
                    }
                }
            }
        }
    }

    /*
    not filter writable perform
     */
    private void notFilter(File[] sourceFiles, String command) {
        String[] arr = command.split(REGEX);
        for (File file : sourceFiles) {
            if (file.isFile()) {
                if (arr[FIRST].equals(YES)) {
                    if (!file.canWrite()) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                } else if (arr[FIRST].equals(NO)) {
                    if (file.canWrite()) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        }
    }

}
