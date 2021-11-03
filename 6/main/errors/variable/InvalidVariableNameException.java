package oop.ex6.main.errors.variable;

import oop.ex6.main.errors.CompileException;

/**
 * invalid var name
 */
public class InvalidVariableNameException extends CompileException {
    /**
     * constr.
     *
     * @param str error
     */
    public InvalidVariableNameException(String str) {
        super(str);
    }
}
