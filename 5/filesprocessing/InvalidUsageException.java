package filesprocessing;

/**
 * Invalid usage exception errors handling
 */
public class InvalidUsageException extends Exception {
    /*
    serial
     */
    private static final long serialVersionUID = 1L;
    private static final String ERROR = "ERROR: Incorrect number of arguments given\n";

    /**
     * defsult constrcutor
     */
    public InvalidUsageException() {
        super(ERROR);
    }

    /**
     * constructor with given error message
     *
     * @param str error message given
     */
    public InvalidUsageException(String str) {
        super(ERROR);
    }
}
