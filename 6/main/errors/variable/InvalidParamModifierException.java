package oop.ex6.main.errors.variable;


import oop.ex6.main.errors.CompileException;

/**
 * invalid param modifier
 */
public class InvalidParamModifierException extends CompileException {

    private final static String ERROR = "Parameters in method signature can only be final.";

    /**
     * CONSTR.
     */
    public InvalidParamModifierException() {
        super(ERROR);
    }
}
