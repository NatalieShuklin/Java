package oop.ex6.main.errors.method;


import oop.ex6.main.errors.CompileException;

/**
 * error when calling wrongly for method
 */
public class InvalidCallForMethodException extends CompileException {
    /**
     * const.
     *
     * @param str given err
     */
    public InvalidCallForMethodException(String str) {
        super(str);
    }
}
