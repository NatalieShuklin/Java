package oop.ex6.main.errors.method;


import oop.ex6.main.errors.CompileException;

/**
 * invalid method type
 */
public class InvalidMethodType extends CompileException {

    private final static String ERROR = "Method can only be 'void'";

    /**
     * constr.
     */
    public InvalidMethodType(){
        super(ERROR);
    }

}
