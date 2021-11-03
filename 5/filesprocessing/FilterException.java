package filesprocessing;

/**
 * exception erros handle for sub section filter, during command file read
 * type 1 erros
 */
public class FilterException extends Exception {
    /*
    serial
     */
    private static final long serialVersionUID = 1L;
    /*
    warning msg
    */
    private static final String WARNING = "Warning in line ";
    private static final String NEWLINE = "\n";

    /**
     * constructor with given line where error occurred
     *
     * @param line line of error
     */
    public FilterException(int line) {
        super(WARNING + line + NEWLINE);
    }
}
