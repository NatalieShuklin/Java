package filesprocessing;

/**
 * class represents exceptions and errors handling for type 1 errors, IO errors in commands file
 */
public class SectionCommandException extends Exception {
    /*
    serial
     */
    private static final long serialVersionUID = 1L;
    /*
    ERROR MSG FOR ERRORS SUCH AS IO EXCEPTIONS
     */
    private static final String GENERAL_ERROR = "ERROR: In reading Commands file\n";

    /**
     * defualt constr.
     */
    public SectionCommandException() {
        super(GENERAL_ERROR);
    }

    /**
     * constrcutor with gievn string error msg.
     *
     * @param str error msg given
     */
    public SectionCommandException(String str) {
        super(str);
    }
}
