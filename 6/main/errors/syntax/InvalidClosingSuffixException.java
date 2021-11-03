package oop.ex6.main.errors.syntax;

import oop.ex6.main.errors.CompileException;

/**
 * closing bracket doesnt appear in its own line
 */
public class InvalidClosingSuffixException extends CompileException {
    private static final String ERROR = "Closing bracket should appear by itself in a new line.";

    /**
     * constr.
     */
    public InvalidClosingSuffixException() {
        super(ERROR);
    }
}
