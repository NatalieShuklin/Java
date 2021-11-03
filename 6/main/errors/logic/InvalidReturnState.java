package oop.ex6.main.errors.logic;


/**
 * illegal return
 */
public class InvalidReturnState extends FunctionalityCodeException {

    private static final String ERROR = "Return statement should be empty";

    public InvalidReturnState() {

        super(ERROR);
    }
}
