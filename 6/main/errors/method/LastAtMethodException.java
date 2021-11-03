package oop.ex6.main.errors.method;

import oop.ex6.main.errors.logic.FunctionalityCodeException;

/**
 * last line + ending  method checking
 */
public class LastAtMethodException extends FunctionalityCodeException {
    /**
     * constr
     *
     * @param str err
     */
    public LastAtMethodException(String str) {
        super(str);
    }
}
