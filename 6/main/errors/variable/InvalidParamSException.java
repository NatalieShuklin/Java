package oop.ex6.main.errors.variable;


import oop.ex6.main.errors.CompileException;

/**
 * Invalid parameters
 */
public class InvalidParamSException extends CompileException {

    private static final String ERROR = "Parameter has only name, type and an optional final modifier";

    /**
     * constr.
     */
    public InvalidParamSException() {
        super(ERROR);
    }
}
