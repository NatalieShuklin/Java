package oop.ex6.main.errors.method;

import oop.ex6.main.errors.CompileException;

/**
 * invalid method parameters
 */
public class InvalidMethodParamsException extends CompileException {

    private final static String ERROR = "Invalid Parameters.";

    /**
     * constr.
     */
    public InvalidMethodParamsException() {
        super(ERROR);
    }
}
