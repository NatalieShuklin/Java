package oop.ex6.main.errors.method;


import oop.ex6.main.errors.CompileException;

/**
 * declaration method error
 */
public class MethodDeclarationException extends CompileException {

    private final static String ERROR = "Method has insufficient number of values.";

    /**
     * CONSTR.
     */
    public MethodDeclarationException() {
        super(ERROR);
    }

}
