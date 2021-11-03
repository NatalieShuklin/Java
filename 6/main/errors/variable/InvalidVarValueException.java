package oop.ex6.main.errors.variable;

import oop.ex6.main.errors.CompileException;

/**
 * invalid value of variable
 */
public class InvalidVarValueException extends CompileException {
    /**
     * constr.
     *
     * @param str error
     */
    public InvalidVarValueException(String str) {
        super(str);
    }
}
