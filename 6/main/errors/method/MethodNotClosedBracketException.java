package oop.ex6.main.errors.method;

import oop.ex6.main.errors.CompileException;

/**
 * method should be closed correctly by scopes inside
 */
public class MethodNotClosedBracketException extends CompileException {

    private static final String ERROR = "There are unclosed scopes inside the method.";

    /**
     * CONSTR.
     */
    public MethodNotClosedBracketException() {
        super(ERROR);
    }
}
