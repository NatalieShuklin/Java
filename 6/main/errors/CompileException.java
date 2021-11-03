package oop.ex6.main.errors;

/**
 * sjava compile errors handle
 */
public class CompileException extends Exception {

    /**
     * constr. of error excpetion
     */
    public CompileException() {
        super();
    }

    /**
     * constructor  of error excpetion
     *
     * @param str given str error
     */
    public CompileException(String str) {
        super(str);
    }
}
