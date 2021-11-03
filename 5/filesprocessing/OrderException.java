package filesprocessing;

/**
 * exception error handle for order subsection erros, while reading commands file
 * type 1 erros
 */
public class OrderException extends Exception {
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
     * constrcutor with given line where error occurred
     *
     * @param line line where error happened
     */
    public OrderException(int line) {
        super(WARNING + line + NEWLINE);
    }
}
