package oop.ex6.main.errors.method;


import oop.ex6.main.errors.CompileException;

/**
 * error of wrong method
 */
public class InvalidMethodCodeException extends CompileException {

    private final static String ERROR = "This line is not supported by Sjava.";

    public InvalidMethodCodeException(){
        super(ERROR);
    }

}
