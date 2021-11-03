package oop.ex6.main.errors.method;


import oop.ex6.main.errors.CompileException;

/**
 * invalid method name
 */
public class InvalidMethodName extends CompileException {

    /**
     * const
     *
     * @param str given err
     */
    public InvalidMethodName(String str) {
        super(str);
    }
}
