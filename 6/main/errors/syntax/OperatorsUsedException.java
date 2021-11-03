package oop.ex6.main.errors.syntax;

import oop.ex6.main.errors.CompileException;

/**
 * cant use operators
 */
public class OperatorsUsedException extends CompileException {
    private final static String ERROR = "Sjava doesn't support the use of operators.";

    public OperatorsUsedException() {
        super(ERROR);
    }
}
