package oop.ex6.main;

import oop.ex6.compile.CompileSjavaCode;
import oop.ex6.compile.FirstCompileTypeTwoErr;
import oop.ex6.main.errors.logic.FunctionalityCodeException;
import oop.ex6.main.errors.CompileException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Sjavac class represents the main runner of the sJava program, it receives the sJava file
 * and performs actions, and returns with correct output to user regarding the vadility
 * of the sJava file by the sJava code rules
 * @author Natasha Shuklin
 */
public class Sjavac {
    /*
    return value of correct valid sjava file
     */
    private static final int RETURN_LEGAL = 0;
    /*
    return value if IO errors occurred with the sjava file
     */
    private static final int RETURN_IO_ERROR = 2;
    /*
    return value of illegal file code, not valid by sjava code rules
     */
    private static final int RETURN_ILLEGAL = 1;
    /*
    saves the file input in a linked list of strings
     */
    private LinkedList<String> sJavaRead;
    /*
    first arg
     */
    private static final int ARG_FIRST = 0;

    /**
     * main function called for the program of sJava Compilation
     * @param args user args input
     * */
    public static void main(String[] args) {
        try {
            String file = args[ARG_FIRST];
            //looks for errors of type 2 mainly before logic parse)
            FirstCompileTypeTwoErr validity = new FirstCompileTypeTwoErr(file);
            validity.beginCompilation();
            ArrayList<String> validatedCodeLinse = validity.getCodeList(); //codelines
            CompileSjavaCode compile = new CompileSjavaCode(validatedCodeLinse);
            compile.compileSjavaCode();
            System.out.println(RETURN_LEGAL);

        }  catch (IOException e){
            System.err.println(RETURN_IO_ERROR);
        }
        catch (FunctionalityCodeException | CompileException ee){
            System.err.println(ee.getMessage());
            System.out.println(RETURN_ILLEGAL);
        }
    }
}