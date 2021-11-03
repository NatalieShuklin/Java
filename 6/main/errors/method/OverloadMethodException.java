package oop.ex6.main.errors.method;

import oop.ex6.main.errors.logic.FunctionalityCodeException;

public class OverloadMethodException extends FunctionalityCodeException {

    private static final String ERROR = "Method overloading is not supported: no two methods with the same name" +
            " may exist.";

    public OverloadMethodException(){
        super(ERROR);
    }

}
