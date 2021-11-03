package oop.ex6.main.errors.syntax;

import oop.ex6.main.errors.CompileException;

/**
 * invalid suffix
 */
public class InvalidSuffixException extends CompileException {
    private final static String ERROR = "Each line, which isn't a comment ,must end with either {, } or ;.";
    /**
     * constr.
     */
    public InvalidSuffixException(){
        super(ERROR);

    }

}
