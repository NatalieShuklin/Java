package oop.ex6.main.errors.syntax;

import oop.ex6.main.errors.CompileException;

/**
 * array exception
 */
public class ArrayUsedException extends CompileException {
    private static final String ERROR = "Sjava doesn't support the use of arrays";

    /**
     * constr.
     */
    public ArrayUsedException() {
        super(ERROR);
    }
}
