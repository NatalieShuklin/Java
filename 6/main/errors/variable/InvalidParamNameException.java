package oop.ex6.main.errors.variable;


import oop.ex6.main.errors.CompileException;

/**
 * INVALID PARAM NAME
 */
public class InvalidParamNameException extends CompileException {

    private static final String ERROR = "Invalid parameter name";

    /**
     * CONSTR.
     */
    public InvalidParamNameException() {
        super(ERROR);
    }
}
