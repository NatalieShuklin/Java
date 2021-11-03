package oop.ex6.main.errors.syntax;

import oop.ex6.main.errors.CompileException;

/**
 * invalid comment
 */
public class InvalidCommentException extends CompileException {
    /**
     * const.
     */
    public InvalidCommentException(){
        super("No characters allowed before a comment line");
    }
}
