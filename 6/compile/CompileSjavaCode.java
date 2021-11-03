package oop.ex6.compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.errors.logic.FunctionalityCodeException;
import oop.ex6.main.errors.method.InvalidCallForMethodException;
import oop.ex6.main.errors.CompileException;
import oop.ex6.main.errors.method.MethodNotClosedBracketException;
import oop.ex6.sjavacomponents.Variable;
import oop.ex6.sjavacomponents.Method;

/**
 * This class is responsible for sjava code compilation 2nd phase
 * goes over the logic of given sjava code
 */
public class CompileSjavaCode {
    private final static String ERR_METHOD = "A method can only be called inside a method.";
    private CompileSjavaMethod compileMethods;
    private CompileSjavaVariable compileVar;
    private ArrayList<Variable> globalVariables;
    private ArrayList<String> parsedSjava;
    private HashMap<String, Method> methodsMap;
    private final static String OPENING_BRACKET = "{";
    private final static int ZERO = 0;
    private final static int ONE = 1;

    /**
     * constructor
     *
     * @param sjava parsed code after first compilation
     */
    public CompileSjavaCode(ArrayList<String> sjava) {
        this.parsedSjava = sjava;
        this.globalVariables = new ArrayList<>();
        this.methodsMap = new HashMap<>();
        this.compileVar = new CompileSjavaVariable();
        this.compileMethods = new CompileSjavaMethod(this.methodsMap, this.globalVariables);

    }

    /*
    Assist func of compilation
     */
    private int assistCompile(int methodIndex, ArrayList<MethodBlock> methodsBlocks) throws CompileException,
            FunctionalityCodeException {
        int methodSignatureBracket = this.parsedSjava.get(methodIndex).indexOf(OPENING_BRACKET);
        String methodSignature = this.parsedSjava.get(methodIndex).substring(ZERO, methodSignatureBracket);
        String methodName = this.compileMethods.checkDeclarationLine(methodSignature);
        int startOfMethodIndex = methodIndex + ONE;
        int endOfMethodIndex = getEndBlock(methodIndex) - ONE;
        MethodBlock newPointer = new MethodBlock(startOfMethodIndex, endOfMethodIndex, methodName);
        methodsBlocks.add(newPointer);
        return endOfMethodIndex;
    }

    /*
   check if current code line is a method call
    */
    private boolean isMethodCall(String line) {
        Pattern pattern = Pattern.compile(".*\\(.*;\\s*");
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }

    /*
    get the end of a method block
     */
    private int getEndBlock(int index) throws CompileException {
        int countOpenB = ONE, countCloseB = ZERO;
        index++;
        while (countOpenB > countCloseB) {
            if (index == this.parsedSjava.size()) {
                throw new MethodNotClosedBracketException();
            }
            if (checkClosingBracket(this.parsedSjava.get(index).trim())) {
                countCloseB++;
            } else if (checkIfDeclaration(this.parsedSjava.get(index).trim())) {
                countOpenB++;
            }
            index++;
        }
        return index;
    }

    /**
     * Performs the compilation after first phase compile
     *
     * @throws CompileException  throws err
     * @throws FunctionalityCodeException throws err
     */
    public void compileSjavaCode() throws CompileException, FunctionalityCodeException {
        int methodIndex = ZERO;
        ArrayList<MethodBlock> methodsBlocks = new ArrayList<>();
        while (methodIndex < parsedSjava.size()) {
            if (checkIfDeclaration(this.parsedSjava.get(methodIndex).trim())) {
                methodIndex = assistCompile(methodIndex, methodsBlocks);
            } else if (this.isMethodCall(this.parsedSjava.get(methodIndex))) {
                throw new InvalidCallForMethodException(ERR_METHOD);
            } else {
                this.compileVar.variableLineCompile(null, globalVariables,
                        this.parsedSjava.get(methodIndex), null);
            }
            methodIndex++;
        }
        for (MethodBlock block : methodsBlocks) {
            compileMethods.performCompilation(block.methodsName,
                    parsedSjava.subList(block.indexAtStart, block.indexAtEnd + ONE));
        }
    }

    /*
    check if the line is a method declare
     */
    private boolean checkIfDeclaration(String line) {
        Pattern pattern = Pattern.compile("\\{$");
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    /*
    check for closing bracket
     */
    private boolean checkClosingBracket(String line) {
        //checks in current line
        Pattern pattern = Pattern.compile("\\}$");
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    /*
        inner class - represents a method block
        for tracking where start and where ends with brackets { }
     */
    private static class MethodBlock {
        private int indexAtStart;
        private int indexAtEnd;
        private String methodsName;

        MethodBlock(int beginIndex, int endIndex, String name) {
            this.indexAtStart = beginIndex;
            this.indexAtEnd = endIndex;
            this.methodsName = name;
        }
    }
}
