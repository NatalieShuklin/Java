package oop.ex6.main.errors.variable;

import oop.ex6.main.errors.CompileException;

/**
 * invalid declaration variable
 */
public class InvalidVarDeclareException extends CompileException {
    /**
     * CONSTR.
     *
     * @param str
     */
    public InvalidVarDeclareException(String str) {
        super(str);
    }
}
