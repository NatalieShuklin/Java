package oop.ex6.main.errors.variable;


import oop.ex6.main.errors.CompileException;

/**
 * invalid type pf parameter
 */
public class InvalidTypeOfParamException extends CompileException {

    private final static String ERROR = "Parameter has an invalid type.";

    /**
     * CONSTR.
     */
    public InvalidTypeOfParamException() {
        super(ERROR);
    }

}
