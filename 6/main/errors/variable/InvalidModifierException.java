package oop.ex6.main.errors.variable;

import oop.ex6.main.errors.CompileException;

/**
 * invalid modifier
 */
public class InvalidModifierException extends CompileException {
    private final static String ERROR = "Sjava doesn't support the following modifiers: static," +
            " private, protected, public.";

    /**
     * constr.
     */
    public InvalidModifierException() {
        super(ERROR);
    }

    /**
     * const.
     *
     * @param str given err
     */
    public InvalidModifierException(String str) {
        super(str);
    }
}
