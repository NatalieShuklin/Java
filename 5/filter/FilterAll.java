package filter;

import java.io.File;

/**
 * filters all files
 */
public class FilterAll extends Filter {
    /**
     * constrcutor - filteres all files
     *
     * @param sourceFiles the files
     * @param command     the command of filter
     * @param isNot       isnot flag
     */
    public FilterAll(File[] sourceFiles, String command, boolean isNot) {
        if (isNot) {
            afterFilter.clear();
        } else {
            for (File file : sourceFiles) {
                if (file.isFile()) {
                    namesList.add(file.getName());
                    afterFilter.add(file);
                }
            }
        }
    }
}
