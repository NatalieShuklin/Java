package filter;

import java.io.File;

/**
 * CLASS REPRESNTS A FILTER HIDDEN
 */
public class FilterHidden extends Filter {
    /*
    delimeter
    */
    private static final String REGEX = "#";
    /*
    dot
    */
    private static final String DOT = ".";
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
    public FilterHidden(File[] sourceFiles, String command, boolean isNot) {
        String[] arr = command.split(REGEX);
        if (!isNot) {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    if (arr[FIRST].equals(YES)) {
                        if (file.isHidden() || file.getName().startsWith(DOT)) {
                            namesList.add(file.getName());
                            afterFilter.add(file);
                        }
                    } else if (arr[FIRST].equals(NO)) {
                        if (!(file.isHidden() || file.getName().startsWith(DOT))) {
                            namesList.add(file.getName());
                            afterFilter.add(file);
                        }
                    }
                }
            }
        } else {
            notFilter(sourceFiles, command);
        }
    }

    /*
    not filter perform
     */
    private void notFilter(File[] sourceFiles, String command) {
        String[] arr = command.split(REGEX);
        for (File file : sourceFiles) {
            if (file.isFile()) {
                if (arr[FIRST].equals(YES)) {
                    if (!(file.isHidden() || file.getName().startsWith(DOT))) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                } else if (arr[FIRST].equals(NO)) {
                    if (file.isHidden() || file.getName().startsWith(DOT)) {
                        namesList.add(file.getName());
                        afterFilter.add(file);
                    }
                }
            }
        }
    }
}
