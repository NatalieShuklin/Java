package oop.ex6.main.errors.syntax;

import oop.ex6.main.errors.CompileException;

/**
 * MULTI COMMENT NOT SUPPORTED
 */
public class MultiLineCommentException extends CompileException {
    private final static String ERROR = "Multi-line comments are not supported in s-java.";

    /**
     * constr.
     */
    public MultiLineCommentException() {
        super(ERROR);
    }
}
