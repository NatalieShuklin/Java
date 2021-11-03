package filesprocessing;

import java.io.*;
import java.util.LinkedList;

/**
 * class that responsible to check the 2-type error of the commands line first
 */
public class CheckCommandsFileStructure {
    /*
     * filter name
     */
    private static final String FILTER = "FILTER";
    /*
    order name
     */
    private static final String ORDER = "ORDER";
    /*
    filter count
     */
    private int filterCount = 0;
    /*
    order count
     */
    private int orderCount = 0;
    /*
    counter lines per section
     */
    private int count = ZERO;
    /*
    linked list of section
     */
    private LinkedList<String> section = new LinkedList<>();
    /*
    one init
     */
    private static final int ONE = 1;
    /*
    zero init
     */
    private static final int ZERO = 0;
    /*
    three init
     */
    private static final int THREE = 3;
    /*
    four init
     */
    private static final int FOUR = 4;
    /*
    null string
     */
    private static final String NULL_STR = "";
    /*
    error msg
     */
    private static final String ERROR_NAME_FILTER = "ERROR: Bad FILTER subsection name\n";

    /*
    error msg
    */
    private static final String ERROR_MISSING_FILTER = "ERROR: FILTER sub-section is missing\n";

    /*
    error msg
    */
    private static final String ERROR_NAME_ORDER = "ERROR: Bad ORDER subsection name\n";

    /*
    error msg
    */
    private static final String ERROR_MISSING_ORDER = "ERROR: ORDER sub-section is missing\n";

    /**
     * checks the command file structure file, looks for type 2 errors
     *
     * @param commandsFile commands file
     * @throws IOException             during read of command files throws IO errors
     * @throws SectionCommandException if error type 2 occurs
     */
    protected CheckCommandsFileStructure(File commandsFile) throws IOException, SectionCommandException {
        FileReader reader = new FileReader(commandsFile);
        BufferedReader buffered = new BufferedReader(reader);
        String line;
        int count = ZERO;
        while ((line = buffered.readLine()) != null) {
            section.add(line);
            count++;
            line = buffered.readLine();
            if (line == null) {
                section.add(NULL_STR);
            } else
                section.add(line);
            count++;
            line = buffered.readLine();
            if (line == null) {
                section.add(NULL_STR);
                count++;
            }
            if (line != null && !line.equalsIgnoreCase(FILTER)) {
                section.add(line);
                count++;
            }
            if (line != null && line.equalsIgnoreCase(FILTER) && count == THREE) {
                subsectionPerformCheck();
                section.add(line);
                count = ONE;
                continue;
            }
            line = buffered.readLine();
            if (line != null && !line.equalsIgnoreCase(FILTER)) {
                section.add(line);
                count++;
                if (count == FOUR) {
                    subsectionPerformCheck();
                    count = ZERO;
                }
            }
            if (line == null) {
                section.add(NULL_STR);
                if (count == FOUR) {
                    subsectionPerformCheck();
                    count = ZERO;
                }
            }
            if (line != null && (line.equals(FILTER) || line.equalsIgnoreCase(FILTER))) {
                subsectionPerformCheck();
                section.add(line);
                count = ONE;
            } else {
                subsectionPerformCheck();
            }
        }
        finalCheck();
    }

    /*
     * check given section, its subsections filter+order
     *
     * @param section given section to check
     * @throws SectionCommandException if type 2 erros occur throw them
     */
    private void checkSection() throws SectionCommandException {
        int line = ONE;
        while (section.size() > ZERO) {
            String str = section.pop();
            if (str.equals(FILTER) && line == ONE) {
                filterCount++;
                line++;
            } else if ((str.equalsIgnoreCase(FILTER) || str.matches(".*[FILTERfilter].*")) && line ==
                    ONE) {
                throw new SectionCommandException(ERROR_NAME_FILTER);
            } else if (line == ONE) {
                throw new SectionCommandException(ERROR_NAME_FILTER);
            } else if (str.equals(ORDER) && line == THREE) {
                orderCount++;
                line++;
            } else if ((str.equalsIgnoreCase(ORDER) || str.matches(".*[ORDEorde].*")) && line ==
                    THREE) {
                throw new SectionCommandException(ERROR_NAME_ORDER);
            } else if (line == THREE) {
                throw new SectionCommandException(ERROR_NAME_ORDER);
            } else {
                line++;
            }
        }
    }

    /*
    help function of check command - checks if amount of orders equals amount of filters
     */
    private void finalCheck() throws SectionCommandException {
        if (section.size() > ZERO) {
            String s = section.pop();
            if (s.equalsIgnoreCase(FILTER)) {
                filterCount++;
            }
        }
        if (orderCount > filterCount) {
            throw new SectionCommandException(ERROR_MISSING_FILTER);
        }
        if (filterCount > orderCount) {
            throw new SectionCommandException(ERROR_MISSING_ORDER);
        }
    }

    /*
    help function of checkcommands, calls the check section func and clears the section after finished.
     */
    private void subsectionPerformCheck() throws SectionCommandException {
        checkSection();
        section.clear();
    }

    /*
    help function of commandsfile Strcuture, parses and checks
     */
    private void commandsFileSecond(String line) throws SectionCommandException {
        if (line != null && !line.equalsIgnoreCase(FILTER)) {
            section.add(line);
            count++;
            if (count == FOUR) {
                subsectionPerformCheck();
                count = ZERO;
            }
        }
        if (line == null) {
            section.add(NULL_STR);
            if (count == FOUR) {
                subsectionPerformCheck();
                count = ZERO;
            }
        }
        if (line != null && (line.equals(FILTER) || line.equalsIgnoreCase(FILTER))) {
            subsectionPerformCheck();
            section.add(line);
            count = ONE;
        } else {
            subsectionPerformCheck();
        }
    }


}
