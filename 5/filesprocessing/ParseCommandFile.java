package filesprocessing;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import static filter.Filter.*;
import static order.Order.*;

import filter.Filter;
import order.Order;

/**
 * class represents module 2 - parsing the commands file after type 2 erros where checked,
 * this class responsible for performing the commands section by section by the correct filter/order
 */
public class ParseCommandFile {
    /*
    filter name
     */
    private static final String FILTER = "FILTER";
    /*
    order name
     */
    private static final String ORDER = "ORDER";
    /*
    new line
     */
    private static final String NEW_LINE = "\n";
    /*
    warning
     */
    private static final String WARNING = "Warning in line ";
    /**
     * commands list
     */
    protected LinkedList<String> commands;
    /*
    current line during file read
     */
    private int currentLine = 0;
    /*
    zero init
    */
    private static final int ZERO = 0;
    /*
    one init
    */
    private static final int ONE = 1;
    /*
    array index 2
     */
    private static final int TWO = 2;
    /*
    array index 3
     */
    private static final int THREE = 3;
    /*
    delimiter
     */
    private static final String REGEX = "#";
    /*
    reverse
     */
    private static final String REVERSE = "REVERSE";
    /*
    not
     */
    private static final String NOT = "NOT";
    /**
     * files list names
     */
    public String[] files;
    /**
     * current commend processed
     */
    public String currentCommand = "";
    /**
     * curent filter
     */
    public String currFilter = "";
    /**
     * curent order
     */
    public String currOrder = "";
    /**
     * filtered files result
     */
    public LinkedList<File> filteredFiles;

    /**
     * constructor parses commands file start
     *
     * @param input commands
     * @throws NullPointerException throws error
     */
    public ParseCommandFile(LinkedList<String> input) throws NullPointerException {
        if (input == null) {
            throw new NullPointerException();
        }
        commands = new LinkedList<>();
        commands.addAll(input);
        filteredFiles = new LinkedList<>();
    }

    /*
    help function of checksection
     */
    private void checkSectionSecond(File[] srcFiles) throws IOException {
        performCurrentFilter(currFilter, srcFiles);
        performCurrentOrder(currOrder, filteredFiles, files);
        String[] toPrint = Order.getSortedFiles();
        if (toPrint != null) {
            for (String s : toPrint) {
                System.out.println(s);
            }
        }
        filteredFiles.clear();
    }

    /*
    check section in commands file
     */
    private void checkSection(LinkedList<String> section, File[] srcFiles) throws IOException {
        int line = ZERO;
        while (section.size() > ZERO) {
            String str = section.pop();
            line++;
            currentLine++;
            if (str.equals(FILTER) && line == ONE) {
                line++;
                String strSecond = section.pop();
                currentLine++;
                try {
                    checkFilterSubSection(strSecond);
                    currentCommand = strSecond;
                    currFilter = strSecond;
                } catch (FilterException e) {
                    System.err.print(WARNING + currentLine + NEW_LINE);
                    currFilter = ALL;
                }
            } else if (str.equals(ORDER) && line == THREE) {
                String orderCommand;
                if (section.size() > ZERO) {
                    orderCommand = section.pop();
                    line++;
                } else
                    orderCommand = FILTER;
                try {
                    if (orderCommand.equals(FILTER)) {
                        currOrder = ABS;
                    } else {
                        currentLine++;
                        checkOrderSubSection(orderCommand);
                        currOrder = orderCommand;
                    }
                } catch (OrderException e) {
                    System.err.print(WARNING + currentLine + NEW_LINE);
                    currOrder = ABS;
                }
            } else {
                line++;
            }
        }
        checkSectionSecond(srcFiles);
    }

    /**
     * parses commands files
     *
     * @param srcFiles the files
     */
    public void parseSections(File[] srcFiles) throws IOException {
        LinkedList<String> section = new LinkedList<>();
        for (Iterator<String> i = commands.iterator(); i.hasNext(); ) {
            int count = ZERO;
            while (i.hasNext()) {
                String s = i.next(); //first
                section.add(s);
                count++;
                if (i.hasNext()) {
                    section.add(i.next());
                    count++;
                }
                if (i.hasNext() && count != THREE) {
                    section.add(i.next());
                    count++;
                } //third
                String str;
                if (i.hasNext()) {
                    str = i.next();
                    if (str.equals(FILTER)) {
                        checkSection(section, srcFiles);
                        section.clear();
                        section.add(str);
                        count = ONE;
                    } else {
                        section.add(str);
                        checkSection(section, srcFiles);
                        section.clear();
                        count = ZERO;
                    }
                }
            }
            if (section.size() > ZERO && !i.hasNext())
                checkSection(section, srcFiles);
        }
    }

    /*
    PERFORM ORDER COMMAND
     */
    private void performCurrentOrder(String currentOrder, LinkedList<File> filtered, String[] files) {
        String[] arr = currentOrder.split(REGEX);
        boolean isReverse = false;
        if (currentOrder.endsWith(REVERSE)) {
            isReverse = true;
        }
        Order.performOrder(arr[ZERO], filtered, isReverse);
    }

    /*
    perform current filter command
     */
    private void performCurrentFilter(String currentFilter, File[] srcFiles) throws IOException {
        boolean isNot = false;
        String[] arr = currentFilter.split(REGEX);
        if (currentFilter.endsWith(NOT)) {
            isNot = true;
        }
        Filter.performFilter(arr[ZERO], srcFiles, currentFilter, isNot);
        filteredFiles.addAll(Filter.getFiltered());
    }

    /*
    check order in subsection
    THROWS ORDEREXCEPTION
     */
    private void checkOrderSubSection(String command) throws OrderException {
        if (command.equals(FILTER)) {
            return;
        }
        String[] comm = command.split(REGEX);
        if (!Order.isValidOrderName(comm[ZERO])) {
            throw new OrderException(currentLine);
        }
    }

    /*
    check filter in sub section
    throw filter exception
     */
    private void checkFilterSubSection(String command) throws FilterException {
        String[] arrOfStr = command.split(REGEX);
        if (!Filter.isValidFilterName(arrOfStr[ZERO])) {
            throw new FilterException(currentLine);
        }
        if (arrOfStr[0].equals(WRITABLE) || arrOfStr[0].equals(HIDDEN) || arrOfStr[0].equals(EXECUTABLE)) {
            if (arrOfStr[1].equals(YES)) {
                ;
            } else if (arrOfStr[1].equals(NO)) {
                ;
            } else {
                throw new FilterException(currentLine);
            }
        }
        if (arrOfStr[ZERO].equals(GREATER_THAN) || arrOfStr[ZERO].equals(BETWEEN) ||
                arrOfStr[ZERO].equals(SMALLER_THAN)) {
            if (arrOfStr[ZERO].equals(BETWEEN)) {
                double first = Double.parseDouble(arrOfStr[ONE]);
                double second = Double.parseDouble(arrOfStr[TWO]);
                if (second < first)
                    throw new FilterException(currentLine);
            }
            double parameter = Double.parseDouble(arrOfStr[ONE]);
            if (parameter < ZERO)
                throw new FilterException(currentLine);

        }
    }
}



