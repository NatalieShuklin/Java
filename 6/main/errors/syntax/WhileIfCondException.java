package oop.ex6.main.errors.syntax;


import oop.ex6.main.errors.CompileException;

/**
 * invalid if while condition
 */
public class WhileIfCondException extends CompileException {

    private static final String ERROR = "Invalid condition.";

    /**
     * CONSTR.
     */
    public WhileIfCondException() {
        super(ERROR);
    }
}
