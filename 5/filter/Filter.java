package filter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * abstract class represents a filter
 *
 * @author natashashuklin cs
 */
abstract public class Filter {
    /**
     * greater than filter
     */
    public static final String GREATER_THAN = "greater_than";
    /**
     * between filter
     */
    public static final String BETWEEN = "between";
    /**
     * smaller than filter
     */
    public static final String SMALLER_THAN = "smaller_than";
    /**
     * file filter
     */
    public static final String FILE = "file";
    /**
     * contains filter
     */
    public static final String CONTAINS = "contains";
    /**
     * prefix filter
     */
    public static final String PREFIX = "prefix";
    /**
     * suffix filter
     */
    public static final String SUFFIX = "suffix";
    /**
     * writable filter
     */
    public static final String WRITABLE = "writable";
    /**
     * executable filter
     */
    public static final String EXECUTABLE = "executable";
    /**
     * hidden filter
     */
    public static final String HIDDEN = "hidden";
    /**
     * all filter
     */
    public static final String ALL = "all";
    /**
     * YES for filters hidden/exe/writable/..
     */
    public static final String YES = "YES";
    /**
     * No for filter
     */
    public static final String NO = "NO";
    /*
    zero for init and return value check, and size check if not 0
     */
    private static final int ZERO = 0;
    /*
    list of filters that are valid to use
     */
    protected static LinkedList<String> filters = new LinkedList<>();
    /*
    names list files
     */
    protected static LinkedList<String> namesList = new LinkedList<>();
    /*
    filtered list
     */
    protected static LinkedList<File> afterFilter = new LinkedList<>();

    /*
     * craetes filters valid
     */
    private static void createFilters() {
        filters = new LinkedList<>();
        filters.add(GREATER_THAN);
        filters.add(BETWEEN);
        filters.add(SMALLER_THAN);
        filters.add(FILE);
        filters.add(CONTAINS);
        filters.add(PREFIX);
        filters.add(SUFFIX);
        filters.add(WRITABLE);
        filters.add(EXECUTABLE);
        filters.add(HIDDEN);
        filters.add(ALL);
    }

    /**
     * returns filters valid
     *
     * @return valid filters can be used
     */
    public static LinkedList<String> getFilters() {
        if (filters.size() == ZERO)
            createFilters();
        return filters;
    }

    /**
     * static function, perform the correct filter, by creating the correct filter object and
     * performing filtering for the files, method factory
     *
     * @param filter      the filter command
     * @param sourceFiles the files to filter
     * @param command     the whole command given form commands file
     * @param isNot       isnot flag, is NOT apperes in command flag is true, otherwise false
     */
    public static void performFilter(String filter, File[] sourceFiles, String command, boolean isNot) {
        if (namesList.size() > ZERO) {
            namesList.clear();
        }
        if (afterFilter.size() > ZERO) {
            afterFilter.clear();
        }
        if (filters.size() == ZERO)
            createFilters();
        switch (filter) {
            case GREATER_THAN:
                FilterGreaterThan greater = new FilterGreaterThan(sourceFiles, command, isNot);
                break;
            case BETWEEN:
                FilterBetween between = new FilterBetween(sourceFiles, command, isNot);
                break;
            case SMALLER_THAN:
                FilterSmallerThan smaller = new FilterSmallerThan(sourceFiles, command, isNot);
                break;
            case FILE:
                FilterFile file = new FilterFile(sourceFiles, command, isNot);
                break;
            case CONTAINS:
                FilterContains contains = new FilterContains(sourceFiles, command, isNot);
                break;
            case PREFIX:
                FilterPrefix prefix = new FilterPrefix(sourceFiles, command, isNot);
                break;
            case SUFFIX:
                FilterSuffix suffix = new FilterSuffix(sourceFiles, command, isNot);
                break;
            case WRITABLE:
                FilterWritable writable = new FilterWritable(sourceFiles, command, isNot);
                break;
            case EXECUTABLE:
                FilterExecutable executable = new FilterExecutable(sourceFiles, command, isNot);
                break;
            case HIDDEN:
                FilterHidden hidden = new FilterHidden(sourceFiles, command, isNot);
                break;
            case ALL:
                FilterAll all = new FilterAll(sourceFiles, command, isNot);
                break;
        }
    }


    /**
     * returns filtered list
     *
     * @return filtered files
     */
    public static LinkedList<File> getFiltered() {
        return afterFilter;
    }

    /**
     * check if filter is a valid name
     *
     * @param str filter name
     * @return true if valid, otherwise false
     */
    public static boolean isValidFilterName(String str) {
        if (filters.size() == ZERO)
            createFilters();
        return filters.contains(str);
    }

}
