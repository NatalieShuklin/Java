package oop.ex6.compile;

import oop.ex6.main.errors.*;
import oop.ex6.main.errors.logic.InvalidReturnState;
import oop.ex6.main.errors.logic.FunctionalityCodeException;
import oop.ex6.main.errors.method.*;
import oop.ex6.main.errors.syntax.WhileIfCondException;
import oop.ex6.main.errors.variable.InvalidParamModifierException;
import oop.ex6.main.errors.variable.InvalidParamNameException;
import oop.ex6.main.errors.variable.InvalidParamSException;
import oop.ex6.main.errors.variable.InvalidTypeOfParamException;
import oop.ex6.sjavacomponents.IfWhile;
import oop.ex6.sjavacomponents.Method;
import oop.ex6.sjavacomponents.CodeBlock;
import oop.ex6.sjavacomponents.Variable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the Method compiler, calls a compile function that checks if all method parts
 * are correct in syntax, and logical verification inside the sjava code
 * @author Natalia Shuklin
 */
public class CompileSjavaMethod {
    private final static int ZERO = 0;
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int LENGTH_VARIABLE_DECL = 3;
    private final static int LENGTH_VARIABLE_DEC_NORM = 2;
    private CompileSjavaVariable variableCompile;
    private Stack<CodeBlock> codeBlockStack;
    private ArrayList<Variable> globalVars;
    private Map<String, Method> methods;
    private static final String METHOD = "Method";
    private static final String OPEN_PARENTHESES = "(";
    private static final String CLOSE_PARENTHESES = ")";
    private static final String COMMA = ",";
    private static final String EMPTY = "";
    private static final String LEGAL_METHOD = "void";
    private static final String ENDING = ";";
    private static final List<String> varTypes = Arrays.asList("boolean", "char", "double", "int", "String");
    private static final Pattern CHAR_CHECK_NO_WORD = Pattern.compile("[^\\w]+");
    private static final Pattern LEGAL_VAR_START = Pattern.compile("^[a-z]|[_]+[a-z]");
    private static final Pattern LEGAL_COND_VALUES = Pattern.compile("\\w+|\\-\\d+\\.?\\d*");
    private static final Pattern LEGAL_RETURN = Pattern.compile("return");
    private static final Pattern LEGAL_RETURN_TWO = Pattern.compile("\\s*return\\s*(;\\s*)$");
    private static final Pattern LEGAL_END = Pattern.compile("\\s*}\\s*");
    private static final Pattern LEGAL_VAR = Pattern.compile("(;\\s*)$");
    private static final Pattern LEGAL_DECLARATION = Pattern.compile("^(\\s*\\w+\\s+)(\\w+\\s*)" +
            "(\\(.*\\)\\s*)$");
    private static final Pattern LEGAL_PARAMETER_ORDER = Pattern.compile("\\((\\s*|(\\s*\\w+\\s*){2,3}|" +
            "(((\\s*\\w+\\s*){2,3}\\,\\s*)+(\\w+\\s*){2,3}))(\\s*\\)\\s*)$");
    private static final Pattern LEGAL_CALL_METHOD = Pattern.compile("\\s*\\w+\\s*\\(.*\\)\\s*;\\s*");
    private static final Pattern LEGAL_IF_WHILE = Pattern.compile("\\s*(while|if)\\s*(\\(.+\\))\\s*\\{\\s*");
    private static final Pattern LEGAL_COND = Pattern.compile("\\s*\\(\\s*(\\w+|-?\\d*(\\.\\d+)?){1}\\s*" +
            "((\\|\\||\\&\\&)\\s*((\\w+)|-?\\d+(\\.\\d+)?){1}\\s*)*\\)\\s*");
    private static final String ERR_NAME = "A method's name must start with a letter";
    private static final String ERR_NO_PARAMS = "A method was called with parameters even though it " +
            "shouldn't be called with any parameters at all";
    private static final String ERR_WRONG_PARAMS = "A method was called with the wrong number of " +
            "parameters";
    private static final String ERR_INCMP_PARAMS = "A method was called with incompatible parameters.";
    private static final String ERR_EXPECTED_PARAMS = "A method was called without any parameters even " +
            "though it has to be called with a number of parameters";
    private static final String ERR_NOT_EXIST = "There was a call to an non-existing method.";
    private static final String BOOLEAN = "boolean";
    private static final String TRUE = "true";
    private static final String ERR_MUST_RETURN = "There must be a return statement before the end of a " +
            "method.";
    private static final String ERR_MUST_CLOSE = "Method must end with a closing bracket";

    /**
     * constructor of method compiler
     *
     * @param methodsList methods in program
     * @param global      global variables
     */
    public CompileSjavaMethod(HashMap<String, Method> methodsList, ArrayList<Variable> global) {
        this.globalVars = global;
        this.codeBlockStack = new Stack<>();
        this.methods = methodsList;
        this.variableCompile = new CompileSjavaVariable();
    }

    /*
    checks the name
     */
    private void checkName(String name) throws CompileException, FunctionalityCodeException {
        Matcher word = CHAR_CHECK_NO_WORD.matcher(name);
        if (word.find()) {
            throw new InvalidMethodName(ERR_NAME);
        } else if (!Character.isLetter(name.charAt(ZERO))) {
            throw new InvalidMethodName(ERR_NAME);
        }
        if (this.methods != null) {
            if (this.methods.containsKey(name)) {
                throw new OverloadMethodException();
            }
        }
    }

    /*
    check if its final
     */
    private boolean checkIsFinal(String type) throws InvalidParamModifierException {
        if (!type.equals("final")) {
            throw new InvalidParamModifierException();
        } else
            return true;
    }

    /*
    check arg type
     */
    private void checkArgType(String type) throws CompileException {
        if (!varTypes.contains(type)) {
            throw new InvalidTypeOfParamException();
        }
    }

    /*
    check arg name
     */
    private void checkArgName(String name) throws CompileException {
        Matcher argNameMatch = LEGAL_VAR_START.matcher(name);
        if (!argNameMatch.matches()) {
            throw new InvalidParamNameException();
        }
    }

    /*
    check legal names or not in args of method
     */
    private boolean checkLegalNamesArgs(ArrayList<Variable> tocheck) throws CompileException {
        this.variableCompile.checkDuplicatesInCodeLine(tocheck);
        for (Variable var : tocheck) {
            this.variableCompile.isNameLegalVar(var.getVariableName());
        }
        return true;
    }

    /*
    assist func of check declaration args
     */
    private void assistCheckDeclare(String declaration, String[] params, ArrayList<Variable> declarationVars)
            throws CompileException {
        for (String arg : params) {
            String[] elems = arg.trim().split("\\s+");
            int typeIndex = ZERO;
            boolean isFinal = false;
            if (elems.length > LENGTH_VARIABLE_DECL || elems.length < LENGTH_VARIABLE_DEC_NORM)
                throw new InvalidParamSException();
            if (elems.length == LENGTH_VARIABLE_DECL) {
                isFinal = checkIsFinal(elems[ZERO]);
                typeIndex++;
            }
            String type = elems[typeIndex];
            checkArgType(type);
            int nameIndex = typeIndex + ONE;
            String name = elems[nameIndex];
            checkArgName(name);
            Variable variableInDeclaration = new Variable(isFinal, true, name, type, null);
            declarationVars.add(variableInDeclaration);
        }
    }

    /*
    check the declaration args
     */
    private ArrayList<Variable> checkDeclarationArguments(String declaration) throws CompileException {
        ArrayList<Variable> declarationVars = new ArrayList<>();
        String[] params = null;
        int startIndexArgs = declaration.indexOf(OPEN_PARENTHESES) + ONE;
        int endIndexArgs = declaration.indexOf(CLOSE_PARENTHESES);
        String methodArgs = declaration.substring(startIndexArgs, endIndexArgs).trim();
        if (methodArgs.isEmpty())
            return null;
        params = methodArgs.split(COMMA);
        assistCheckDeclare(declaration, params, declarationVars);
        checkLegalNamesArgs(declarationVars);
        return declarationVars;
    }

    /*
    get argumentss as in array representation
     */
    private String[] argsInArray(String str) {
        String[] params = null;
        int startIndexArgs = str.indexOf(OPEN_PARENTHESES) + ONE;
        int endIndexArgs = str.indexOf(CLOSE_PARENTHESES);
        String methodArgs = str.substring(startIndexArgs, endIndexArgs).trim();
        if (methodArgs.isEmpty()) {
            return params;
        }
        params = methodArgs.split(COMMA);
        return params;
    }

    /*
    creates method
     */
    private void createMethod(Matcher declaration, ArrayList<Variable> declareArgs) {
        Method newMethod = new Method(declareArgs);
        String name = declaration.group(TWO).trim();
        this.methods.put(name, newMethod);
        this.codeBlockStack.push(newMethod);
    }

    /**
     * Check declaration line
     *
     * @param method method
     * @return true if correct else false
     * @throws CompileException  throws error
     * @throws FunctionalityCodeException throws error
     */
    public String checkDeclarationLine(String method) throws CompileException, FunctionalityCodeException {
        Matcher declarationMatcher = LEGAL_DECLARATION.matcher(method);
        if (!declarationMatcher.matches()) {
            throw new MethodDeclarationException();
        } else {
            if (!declarationMatcher.group(ONE).trim().equals(LEGAL_METHOD)) {
                throw new InvalidMethodType();
            }
            checkName(declarationMatcher.group(TWO).trim());

            ArrayList<Variable> declarationVars = checkDeclarationArguments(method);
            createMethod(declarationMatcher, declarationVars);
        }
        return declarationMatcher.group(TWO).trim();
    }

    /*
    is it a call for a method checker
     */
    private boolean isCallForMethod(String code) {
        Matcher callMethod = LEGAL_CALL_METHOD.matcher(code);
        return callMethod.matches();
    }

    /*
     * is the block is a method
     */
    private boolean isMethodBlock() {
        return this.codeBlockStack.peek().getBlockType().equals(METHOD);
    }

    /*
    assist func of correct vars check
     */
    private boolean assistCorrectVars(Variable rightVar, Method method, IfWhile block, Variable var)
            throws CompileException {
        if (method != null) {
            return this.variableCompile.isValidRef(method.getVariablesInsideBlock(), var, rightVar) ||
                    this.variableCompile.isValidRef(method.getArguments(), var, rightVar) ||
                    this.variableCompile.isValidRef(globalVars, var, rightVar);
        } else {
            return this.variableCompile.isValidRef(block.getVariablesInsideBlock(), var, rightVar
            ) || this.variableCompile.isValidRef(block.getVariablesOutsideBlock(), var,
                    rightVar) ||
                    this.variableCompile.isValidRef(block.getArguments(), var, rightVar) ||
                    this.variableCompile.isValidRef(globalVars, var,
                            rightVar);
        }
    }

    /*
    is correct variable
     */
    private boolean isCorrectVariables(Variable rightVar, Method method, IfWhile block, String param)
            throws CompileException {
        String type = this.variableCompile.getTypeOfValue(param.trim());
        if (type == null) {
            Variable var = new Variable(false, false, null, null, param.trim());
            boolean isChecked = assistCorrectVars(rightVar, method, block, var);
            if (!isChecked)
                return false;
        }
        if (type != null) {
            return this.variableCompile.isCompatibleValue(type, rightVar.getVariableType());
        }
        return true;
    }

    /*
    check is variables in the call are valid
     */
    private boolean isVarsInCallValid(Method methodCall, Method method, String[] params, IfWhile block)
            throws CompileException {
        if (!params[ZERO].equals(EMPTY)) {
            //not empty
            if (methodCall.getArguments().isEmpty()) {
                throw new InvalidCallForMethodException(ERR_NO_PARAMS);
            }
            if (params.length != methodCall.getArguments().size()) {
                throw new InvalidCallForMethodException(ERR_WRONG_PARAMS);
            }
            for (int i = 0; i < params.length; i++) {
                if (!this.isCorrectVariables(methodCall.getArguments().get(i), method, block, params[i])) {
                    throw new InvalidCallForMethodException(ERR_INCMP_PARAMS);
                }
            }
            return true;
        } else {
            //empty
            if (methodCall.getArguments() != null) {
                throw new InvalidCallForMethodException(ERR_EXPECTED_PARAMS);
            }
            return true;
        }
    }
    /*
    is a method call valid
     */
    private boolean isMethodCallValid(String code, Method method, IfWhile block) throws CompileException {
        String[] str = code.split("\\(");
        String name = str[ZERO].trim();
        if (!this.methods.containsKey(name.trim())) {
            throw new InvalidCallForMethodException(ERR_NOT_EXIST);
        }
        String[] params = str[ONE].split(COMMA);
        params[params.length - ONE] = params[params.length - ONE].replace(ENDING, EMPTY).trim();
        params[params.length - ONE] = params[params.length - ONE].replace(CLOSE_PARENTHESES, EMPTY).trim();
        return isVarsInCallValid(this.methods.get(name), method, params, block);
    }

    /*
    is it a closing bracket of block
     */
    private boolean isCloseBlockChar(String str) {
        Matcher charClose = LEGAL_END.matcher(str);
        return charClose.matches();
    }

    /*
    is the code is a return statement
     */
    private boolean isReturn(String str) throws FunctionalityCodeException {
        Matcher returnFromMethod = LEGAL_RETURN.matcher(str);
        if (!returnFromMethod.find()) {
            return false;
        } else {
            Matcher returnMatcherValid = LEGAL_RETURN_TWO.matcher(str);
            if (returnMatcherValid.matches())
                return true;
            else
                throw new InvalidReturnState();
        }
    }

    /*
    is a variable
     */
    private boolean isVariable(String str) {
        Matcher var = LEGAL_VAR.matcher(str);
        return var.find();
    }

    /*
    is if while block
     */
    private boolean isIfOrWhile(String str) {
        Matcher ifwhileMatcher = LEGAL_IF_WHILE.matcher(str);
        return ifwhileMatcher.matches();
    }

    /*
    is the args in if/while valid
     */
    private boolean isValidIfWhileArgs(Method method, String str, IfWhile block) throws CompileException {
        if (!isCorrectVariables(new Variable(false, true, null, BOOLEAN, TRUE),
                method, block, str))
            throw new WhileIfCondException();
        return true;
    }

    /*
    Check the condition of the if or while statement
     */
    private boolean verifyIfOrWhileCond(String method, String str) throws CompileException {
        Matcher condMatcher = LEGAL_IF_WHILE.matcher(str);
        condMatcher.matches();
        String condIfOrWhile = condMatcher.group(TWO);
        Matcher validCond = LEGAL_COND.matcher(condIfOrWhile);
        if (!validCond.matches()) {
            throw new WhileIfCondException();
        } else {
            Matcher elemMatcher = LEGAL_COND_VALUES.matcher(condIfOrWhile);
            while (elemMatcher.find()) {
                String elem = elemMatcher.group();
                if (!isMethodBlock()) {
                    IfWhile ifOrWhile = (IfWhile) this.codeBlockStack.peek();
                    isValidIfWhileArgs(null, elem, ifOrWhile);
                } else {
                    isValidIfWhileArgs(this.methods.get(method), elem, null);
                }
            }
        }
        return validCond.matches();
    }

    /*
    assists compilation process
     */
    private void assistCompilation(String name, String codeLine) throws CompileException {
        if (!isMethodBlock()) {
            IfWhile ifOrWhile = (IfWhile) this.codeBlockStack.peek();
            isMethodCallValid(codeLine, null, ifOrWhile);
        } else {
            isMethodCallValid(codeLine, this.methods.get(name), null);
        }
    }
    /*
    assists compilation process
    */
    private void assistCompilationVerify(String codeLine, List<String> code) throws FunctionalityCodeException {
        if (isCloseBlockChar(codeLine)) {
            String prevCode = code.get(code.indexOf(codeLine) - ONE);
            if (!isReturn(prevCode))
                throw new LastAtMethodException(ERR_MUST_RETURN);
        } else
            throw new LastAtMethodException(ERR_MUST_CLOSE);
    }

    /*
assists compilation process
*/
    private void assistCompilationFinale(String codeLine, String name) throws CompileException {
        if (isVariable(codeLine)) {
            if (!isMethodBlock()) {
                IfWhile ifOrWhile = (IfWhile) this.codeBlockStack.peek();
                this.variableCompile.variableLineCompile(null, globalVars, codeLine, ifOrWhile);
            }
            if (isMethodBlock()) {
                this.variableCompile.variableLineCompile(this.methods.get(name), globalVars, codeLine,
                        null);
            }
        } else if (isIfOrWhile(codeLine)) {
            if (!verifyIfOrWhileCond(name, codeLine)) {
                throw new WhileIfCondException();
            } else {
                ArrayList<Variable> declarationMethodVars = this.methods.get(name).getArguments();
                IfWhile ifOrWhile = new IfWhile(declarationMethodVars, this.codeBlockStack.peek());
                this.codeBlockStack.push(ifOrWhile);
            }
        } else
            throw new InvalidMethodCodeException();
    }

    /**
     * Performs the compilation of the method
     *
     * @param name name
     * @param code code
     * @throws CompileException  throws error
     * @throws FunctionalityCodeException throws error
     */
    public void performCompilation(String name, List<String> code) throws CompileException, FunctionalityCodeException {
        for (String codeLine : code) {
            if (isCallForMethod(codeLine)) {
                assistCompilation(name, codeLine);
            } else if (code.indexOf(codeLine) == code.size() - ONE) {
                assistCompilationVerify(codeLine, code);
            } else if (isReturn(codeLine)) {
                ;
            } else if (isCloseBlockChar(codeLine))
                this.codeBlockStack.pop();
            else {
                assistCompilationFinale(codeLine, name);
            }
        }
        //check if last above last line theres return statement;
        if (isCloseBlockChar(code.get(code.size() - ONE))) {
            String prevCode = code.get(code.size() - TWO);
            if (!isReturn(prevCode))
                throw new LastAtMethodException(ERR_MUST_RETURN);
        }
    }
}
