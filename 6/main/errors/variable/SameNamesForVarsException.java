package oop.ex6.main.errors.variable;

import oop.ex6.main.errors.CompileException;

/**
 * error when given two same names to different variables
 */
public class SameNamesForVarsException extends CompileException {
    /**
     * const.
     *
     * @param str given msg
     */
    public SameNamesForVarsException(String str) {

        super(str);

    }
}
