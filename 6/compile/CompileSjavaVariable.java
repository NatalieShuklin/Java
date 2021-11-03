package oop.ex6.compile;

import oop.ex6.main.errors.*;
import oop.ex6.main.errors.syntax.ArrayUsedException;
import oop.ex6.main.errors.syntax.OperatorsUsedException;
import oop.ex6.main.errors.variable.*;
import oop.ex6.sjavacomponents.IfWhile;
import oop.ex6.sjavacomponents.Method;
import oop.ex6.sjavacomponents.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents compilation of the Variables part of the  sjava code
 */
public class CompileSjavaVariable {
    /*
  regex double type pattern
   */
    private final static Pattern DOUBLE_REGEX = Pattern.compile("[-]?\\d+\\.(\\d+)");
    /*
    regex int type pattern
     */
    private final static Pattern INT_REGEX = Pattern.compile("[-]?\\d+");
    /*
    regex boolean pattern
     */
    private final static Pattern BOOLEAN_REGX = Pattern.compile("true|false");
    /*
    regex string pattern
     */
    private final static Pattern STRING_REGEX = Pattern.compile("\\s*\".*\"");
    /*
    regex char pattern
     */
    private final static Pattern CHAR_REGEX = Pattern.compile("^'.'");
    /*
    regex assign line pattern
     */
    private final static Pattern REGEX_MULT_LINE_ASSIGN = Pattern.compile("([^=]+=\\s*([^\\s^,^=]+" +
            "|\".*\"\\s*)\\s*[,;][\\s]*)+");
    /*
    regex variable pattern
     */
    private final static Pattern REGEX_VAR_VALUE = Pattern.compile("=[\\s]*[^\\s ^,]+[\\s]*[,;][\\s]*");
    /*
    regex declaration pattern
     */
    private final static Pattern REGEX_DECLARATION = Pattern.compile("([\\s]*[\\w]+[\\s]*[,;][\\s]*)+");
    /*
    regex operator pattern
     */
    private final static Pattern REGEX_OPERATOR = Pattern.compile("[-+/*]");
    /*
    duplicate error msg
     */
    private static final String ERROR_DUP = "Two variables with the same name cannot be " +
            "declared/assigned in the same line and scope.";
    private static final String BOOLEAN = "boolean";
    private static final String STR = "String";
    private static final String DOUBLE = "double";
    private static final String CHAR = "char";
    private static final String INT = "int";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final String FINAL = "final";
    private static final String ERR_FINAL = "final cannot be declared after the type and" +
            "cannot be a variable's name";
    private static final String ERR_NON_WORD = "A variable's name cannot contain non-word characters";
    private static final String ERR_NAME_START_DIGIT = "A variable's name cannot start with a digit";
    private static final char UNDERS = '_';
    private static final String ERR_NAME_UNDERS = "A variables name cannot be a single underscore";
    private static final String ERR_FIN_UNASSIGN = "A variable cannot be declared final and not be " +
            "assigned a value.";
    private static final String ERR_NO_TYPE = "A variable cannot be declared without a type";
    private static final String ENDING_CODE_LINE = ";";
    private static final String EMPTY = "";
    private static final String BRACKETS = "[]";
    private static final String COMMA = ",";
    private static final String EQUAL = "=";
    private static final char EQUAL_CHAR = '=';
    private static final String SPACE = " ";
    private static final String ERR_ILLEGAL = "Illegal variable line.";
    private static final String ERR_REF = "A variable cannot be referenced to a non-" +
            "initialized variable.";
    private static final String ERR_COMPT = "A variable was assigned an incompatible value.";
    private static final String ERR_FINAL_MORE = "A final variable cannot be assigned once more.";
    private static final String ERR_GLOBAL = "There cannot be two global variables with the" +
            " same name.";
    private static final String ERR_ASSIGN = "Error in assigning a an illegal value";
    private static final String ERR_ASSIGN_TWO = "Error in assigning a an illegal value/incompatible values";
    private static final String ERR_NAME_IFWHILE = "There cannot be two variables variables in a " +
            " if/while block with the same name.";
    private static final String ERR_METHOD = "A local variable inside a method cannot have" +
            " the same name as the method's parameters. ";
    private static final String ERR_METHOD_TWO = "There cannot be two local variables in a method" +
            " with the same name.";
    private static final String ERR_SYNTAX = "Syntax error in line.";
    private static final String ERR_SYNTAX_TWO = "Syntactical error in line: ";
    private static final String ERR_DECLARE_FIN = "A variable cannot be declared 'final' and not" +
            " contain a type afterwards.";
    private static final String ERR_NONAME = "A variable must have a name after it's type";

    /**
     * checks duplicates names in the line
     *
     * @param variables given vars
     * @return true if there are duplicates else false
     * @throws CompileException throw syntax error
     */
    public boolean checkDuplicatesInCodeLine(ArrayList<Variable> variables) throws CompileException {
        ArrayList<String> varNames = new ArrayList<>();
        for (Variable variable : variables) {
            if (varNames.contains(variable.getVariableName())) {
                throw new SameNamesForVarsException(ERROR_DUP);
            }
            varNames.add(variable.getVariableName());
        }
        return true;
    }

    /*
    get type of var
     */
    private String getVariableType(String str) {
        String type = str.split(" ")[ZERO].trim();
        if (type.equals(BOOLEAN))
            return BOOLEAN;
        if (type.equals(INT))
            return INT;
        if (type.equals(DOUBLE))
            return DOUBLE;
        if (type.equals(CHAR))
            return CHAR;
        if (type.equals(STR))
            return STR;
        return null;
    }

    /**
     * check is var name is valid
     *
     * @param varriableName var name
     * @return true if valid else flase
     * @throws CompileException throws syntax error in case of the name not valid
     */
    public boolean isNameLegalVar(String varriableName) throws CompileException {
        if (varriableName.contains(FINAL)) {
            throw new InvalidVarDeclareException(ERR_FINAL);
        }
        Pattern pattern = Pattern.compile("[^\\w]+");
        Matcher matcher = pattern.matcher(varriableName);
        if (matcher.find()) {
            throw new InvalidVariableNameException(ERR_NON_WORD);
        }
        char first = varriableName.charAt(ZERO);
        if (first == UNDERS && varriableName.length() == ONE) {
            throw new InvalidVariableNameException(ERR_NAME_UNDERS);
        } else if (Character.isDigit(first)) {
            throw new InvalidVariableNameException(ERR_NAME_START_DIGIT);
        }
        return true;
    }

    /*
    check declaration of variable
     */
    private void checkDeclaration(String[] names, String type, boolean isFinal,
                                  ArrayList<Variable> allVars) throws CompileException {
        if (isFinal) {
            throw new InvalidVarDeclareException(ERR_FIN_UNASSIGN);
        }
        if (type == null) {
            throw new InvalidVarDeclareException(ERR_NO_TYPE);
        }
        names[names.length - ONE] =
                names[names.length - ONE].replace(ENDING_CODE_LINE, EMPTY);
        for (String varName : names) {
            //go over names
            varName = varName.trim();
            if (isNameLegalVar(varName)) {
                allVars.add(new Variable(false, false, varName, type, null));
            }
        }
    }

    /*
    returns the variable
     */
    private Variable getVariable(String name, ArrayList<Variable> vars) {
        if (vars == null) {
            return null;
        }
        if (name != null) {
            for (int index = vars.size() - ONE; index >= 0; index--) {
                if (vars.get(index).getVariableName().equals(name)) {
                    return vars.get(index);
                }
            }
        }
        return null;
    }

    /*
    assist function of parse input var
     */
    private void parseInputContinue(String code, String type, boolean isFinal, ArrayList<Variable> vars,
                                    Matcher matcher) throws CompileException {
        String str = code.substring(matcher.start(), matcher.end());
        if (str.length() < code.length()) {
            String[] varsDeclare = code.substring(str.length()).split(COMMA);
            checkDeclaration(varsDeclare, type, isFinal, vars);
        }
        matcher = REGEX_VAR_VALUE.matcher(str);
        String assignmentLine = str;
        while (matcher.find()) {
            int lenUpdate = str.length() - assignmentLine.length();
            String oneAssignment = assignmentLine.substring(ZERO, matcher.end() - lenUpdate);
            assignmentLine = assignmentLine.replace(oneAssignment, EMPTY);
            String[] variablesWithValue = oneAssignment.split(EQUAL);
            String[] names = variablesWithValue[ZERO].split(COMMA);
            String value = variablesWithValue[ONE].trim().replace(COMMA, EMPTY).replace(ENDING_CODE_LINE,
                    EMPTY).trim();
            for (String name : names) {
                name = name.trim();
                if (isNameLegalVar(name)) {
                    vars.add(new Variable(isFinal, true, name, type, value));
                }
            }
        }
    }

    /**
     * Perform compilation on input vars line
     *
     * @param code    given code line
     * @param type    type
     * @param isFinal if final == true else false
     * @return if no errors ocurred returns the parsed vars
     * @throws CompileException throws sytanx error
     */
    public ArrayList<Variable> parseInputVar(String code, String type, boolean isFinal)
            throws CompileException {
        if (code.contains(BRACKETS))
            throw new ArrayUsedException();
        ArrayList<Variable> vars = new ArrayList<>();
        Matcher matcher = REGEX_MULT_LINE_ASSIGN.matcher(code);
        if (matcher.find()) {
            parseInputContinue(code, type, isFinal, vars, matcher);
            return vars;
        }
        matcher = REGEX_DECLARATION.matcher(code);
        if (matcher.matches()) {
            String[] namesVars = code.split(COMMA);
            checkDeclaration(namesVars, type, isFinal, vars);
            return vars;
        } else
            throw new CompileException(ERR_ILLEGAL);
    }

    /**
     * get value type
     *
     * @param valueOfVar the value
     * @return value type
     * @throws CompileException throws error
     */
    public String getTypeOfValue(String valueOfVar) throws CompileException {
        Matcher matcher = STRING_REGEX.matcher(valueOfVar);
        if (matcher.matches()) {
            return "String";
        }
        matcher = INT_REGEX.matcher(valueOfVar);
        if (matcher.matches()) {
            return "int";
        }
        matcher = BOOLEAN_REGX.matcher(valueOfVar);
        if (matcher.matches()) {
            return "boolean";
        }
        matcher = CHAR_REGEX.matcher(valueOfVar);
        if (matcher.matches()) {
            return "char";
        }
        matcher = DOUBLE_REGEX.matcher(valueOfVar);
        if (matcher.matches()) {
            return "double";
        }
        matcher = REGEX_OPERATOR.matcher(valueOfVar);
        if (matcher.find()) {
            throw new OperatorsUsedException();
        }
        return null;
    }

    /**
     * check if the value is compatible
     *
     * @param valueType    type value
     * @param variableType type var
     * @return true if compatible else flase
     */
    public boolean isCompatibleValue(String valueType, String variableType) {
        if (valueType == null) {
            return false;
        }
        if (variableType.equals(valueType)) {
            return true;
        } else if (variableType.equals(INT) || variableType.equals(STR) || variableType.equals(CHAR)) {
            return false;
        } else if (variableType.equals(DOUBLE) && valueType.equals(INT)) {
            return true;
        } else if (variableType.equals(BOOLEAN) && (valueType.equals(INT) || valueType.equals(DOUBLE))) {
            return true;
        }
        return false;
    }

    /**
     * check if reference is valid
     *
     * @param currentVars vars
     * @param var         given variable
     * @param contained   contained vars inside scope
     * @return true if valid, else flase
     * @throws CompileException throws error
     */
    public boolean isValidRef(ArrayList<Variable> currentVars, Variable var, Variable contained)
            throws CompileException {
        Variable optionRef = getVariable(var.getVariableValue(), currentVars);
        if (optionRef == null) {
            return false;
        } else if (!optionRef.isVariableInit()) {
            throw new InvalidVarValueException(ERR_REF);
        } else if (contained == null) {
            if (isCompatibleValue(optionRef.getVariableType(), var.getVariableType()))
                return true;
            throw new InvalidVarValueException(ERR_COMPT);
        } else {
            if (isCompatibleValue(optionRef.getVariableType(), contained.getVariableType())) {
                return true;
            }
            throw new InvalidVarValueException(ERR_COMPT);
        }
    }

    /*
    Check new variable value
     */
    private boolean isNewVarValueValid(Variable var, ArrayList<Variable> currentVars) throws CompileException {
        String typeValue = getTypeOfValue(var.getVariableValue());
        if (typeValue != null) {
            if (isCompatibleValue(typeValue, var.getVariableType())) {
                return true;
            }
            throw new InvalidVarValueException(ERR_COMPT);
        } else {
            return isValidRef(currentVars, var, null);
        }
    }

    /*
    check if contained varirable value is valid one
     */
    private boolean isContainedVarValueValid(ArrayList<Variable> currentVars, Variable var, Variable
            contained)
            throws CompileException {
        if (contained.isVariableFinal() && var.isVariableInit()) {
            throw new InvalidVarValueException(ERR_FINAL_MORE);
        }
        if (getTypeOfValue(var.getVariableValue()) == null) {
            return isValidRef(currentVars, var, contained);
        } else {
            if (isCompatibleValue(getTypeOfValue(var.getVariableValue()), contained.getVariableType())) {
                return true;
            }
            throw new InvalidVarValueException(ERR_COMPT);
        }
    }

    /*
    Sets a contained variable
     */
    private void setContainedVariable(Variable var, ArrayList<Variable> vars) {
        Variable contained = getVariable(var.getVariableName(), vars);
        if (!contained.isVariableInit()) {
            int index = vars.indexOf(contained);
            vars.get(index).setVariableInit();
            vars.get(index).setValue(var.getVariableValue());
        }
    }

    /*
    check if global var is correct
     */
    private boolean isCorrectGlobalVar(Variable var, ArrayList<Variable> globals) throws CompileException {
        if (var.getVariableType() != null) {
            if (getVariable(var.getVariableName(), globals) != null) {
                throw new SameNamesForVarsException(ERR_GLOBAL);
            } else if (!var.isVariableInit() || (var.isVariableInit() &&
                    isNewVarValueValid(var, globals))) {
                globals.add(var);
                return true;
            }
            return false;
        }
        Variable checkExits = getVariable(var.getVariableName(), globals);
        if (checkExits == null) {
            throw new InvalidVarValueException(ERR_ASSIGN);
        }
        if (!isContainedVarValueValid(globals, var, checkExits)) {
            throw new InvalidVarValueException(ERR_ASSIGN_TWO);
        }
        setContainedVariable(var, globals);
        return true;
    }

    /*
    assist function
     */
    private boolean ifWhileCorrectAssist(Variable var, ArrayList<Variable> global, IfWhile ifWhileBlock,
                                         Variable exists) throws CompileException {
        if (isContainedVarValueValid(ifWhileBlock.getVariablesInsideBlock(), var, exists) ||
                isContainedVarValueValid(ifWhileBlock.getVariablesOutsideBlock(), var, exists) ||
                isContainedVarValueValid(ifWhileBlock.getArguments(), var, exists) ||
                isContainedVarValueValid(global, var, exists)) {
            setContainedVariable(var, ifWhileBlock.getVariablesInsideBlock());
            return true;
        }
        return false;
    }

    /*
    assist function
    */
    private boolean ifWhileAssistTwo(Variable var, ArrayList<Variable> global, IfWhile ifWhileBlock, Variable
            exists) throws CompileException {
        if (exists.isVariableInit()) {
            return (isContainedVarValueValid(ifWhileBlock.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getVariablesOutsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getArguments(), var, exists) ||
                    isContainedVarValueValid(global, var, exists));
        } else {
            if (isContainedVarValueValid(ifWhileBlock.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getVariablesOutsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getArguments(), var, exists) ||
                    isContainedVarValueValid(global, var, exists)) {
                var.setType(exists.getVariableType());
                var.setDoesBelongToOuterBlock();
                ifWhileBlock.getVariablesInsideBlock().add(var);
                return true;
            }
            return false;
        }
    }

    /*
    assist func
     */
    private boolean ifWhileAssistThree(Variable var, ArrayList<Variable> global, IfWhile ifWhileBlock,
                                       Variable
                                               exists) throws CompileException {
        return (isContainedVarValueValid(ifWhileBlock.getVariablesInsideBlock(), var, exists)
                || isContainedVarValueValid(ifWhileBlock.getVariablesOutsideBlock(), var, exists)
                || isContainedVarValueValid(ifWhileBlock.getArguments(), var, exists) ||
                isContainedVarValueValid(global, var, exists));
    }

    /*
    assist func
     */
    private boolean ifWhileAssistFourth(Variable var, ArrayList<Variable> global, IfWhile ifWhileBlock,
                                        Variable
                                                exists) throws CompileException {
        if (exists.isVariableInit()) {
            return (isContainedVarValueValid(ifWhileBlock.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getVariablesOutsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getArguments(), var, exists) ||
                    isContainedVarValueValid(global, var, exists));
        } else {
            if (isContainedVarValueValid(ifWhileBlock.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getVariablesOutsideBlock(), var, exists)
                    || isContainedVarValueValid(ifWhileBlock.getArguments(), var, exists) ||
                    isContainedVarValueValid(global, var, exists)) {
                var.setType(exists.getVariableType());
                var.setDoesBelongToOuterBlock();
                ifWhileBlock.getVariablesInsideBlock().add(var);
                return true;
            }
            return false;
        }
    }

    /*
    assist func of if while correct
     */
    private boolean ifwhileCorrectCheckFirst(Variable var, IfWhile ifWhileBlock, ArrayList<Variable> global)
            throws CompileException {
        Variable sameNameVar = getVariable(var.getVariableName(), ifWhileBlock.getVariablesInsideBlock());
        if (sameNameVar != null && !sameNameVar.isDoesBelongToOutBlock()) {
            throw new SameNamesForVarsException(ERR_NAME_IFWHILE);
        }
        if (!var.isVariableInit()) {
            ifWhileBlock.getVariablesInsideBlock().add(var);
            return true;
        } else if (isNewVarValueValid(var, ifWhileBlock.getVariablesInsideBlock()) ||
                isNewVarValueValid(var, ifWhileBlock.getVariablesOutsideBlock()) ||
                isNewVarValueValid(var, ifWhileBlock.getArguments()) ||
                isNewVarValueValid(var, global)) {
            ifWhileBlock.getVariablesInsideBlock().add(var);
            return true;
        }
        return false;
    }

    /*
    check if the if or while is valid
     */
    private boolean isIfWhileCorrect(Variable var, ArrayList<Variable> global, IfWhile ifWhileBlock)
            throws CompileException {
        if (var.getVariableType() != null) {
            return ifwhileCorrectCheckFirst(var, ifWhileBlock, global);
        }
        //check var
        Variable exists = getVariable(var.getVariableName(), ifWhileBlock.getVariablesInsideBlock());
        if (exists != null) {
            return ifWhileCorrectAssist(var, global, ifWhileBlock, exists);
        }
        exists = getVariable(var.getVariableName(), ifWhileBlock.getVariablesOutsideBlock());
        if (exists != null) {
            return ifWhileAssistTwo(var, global, ifWhileBlock, exists);
        }
        exists = getVariable(var.getVariableName(), ifWhileBlock.getArguments());
        if (exists != null) {
            return ifWhileAssistThree(var, global, ifWhileBlock, exists);
        }
        exists = getVariable(var.getVariableName(), global);
        if (exists != null) {
            return ifWhileAssistFourth(var, global, ifWhileBlock, exists);
        }
        return false;
    }

    /*
    method check assist
     */
    private boolean methodAssist(Variable var, ArrayList<Variable> globals, Method method) throws
            CompileException {
        if (getVariable(var.getVariableName(), method.getArguments()) != null) {
            throw new SameNamesForVarsException(ERR_METHOD);
        }
        Variable name = getVariable(var.getVariableName(), method.getVariablesInsideBlock());
        if (name != null && !name.isDoesBelongToOutBlock()) {
            throw new SameNamesForVarsException(ERR_METHOD_TWO);
        }
        if (!var.isVariableInit()) {
            method.getVariablesInsideBlock().add(var);
            return true;
        } else if (isNewVarValueValid(var, method.getVariablesInsideBlock())
                || isNewVarValueValid(var, method.getArguments()) || isNewVarValueValid(var, globals)) {
            method.getVariablesInsideBlock().add(var);
            return true;
        }
        return false;
    }

    /*
    method check assist
     */
    private boolean methodAssistTwo(Variable var, ArrayList<Variable> globals, Method method,
                                    Variable exists) throws
            CompileException {
        if (isContainedVarValueValid(method.getVariablesInsideBlock(), var, exists)
                || isContainedVarValueValid(method.getArguments(), var, exists)
                || isContainedVarValueValid(globals, var, exists)) {
            setContainedVariable(var, method.getVariablesInsideBlock());
            return true;
        }
        return false;
    }

    /*
    method check assist func
     */
    private boolean methodAssistThree(Variable var, ArrayList<Variable> globals, Method method,
                                      Variable exists) throws
            CompileException {
        if (exists.isVariableInit()) {
            return (isContainedVarValueValid(method.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(method.getArguments(), var, exists)
                    || isContainedVarValueValid(globals, var, exists));
        } else {
            if (isContainedVarValueValid(method.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(method.getArguments(), var, exists)
                    || isContainedVarValueValid(globals, var, exists)) {
                var.setType(exists.getVariableType());
                var.setDoesBelongToOuterBlock();
                method.getVariablesInsideBlock().add(var);
                return true;
            }
            return false;
        }
    }

    /*
    check a meethod variables
     */
    private boolean checkMethodVariable(Variable var, ArrayList<Variable> globals, Method method)
            throws CompileException {
        if (var.getVariableType() != null) {
            return methodAssist(var, globals, method);
        }
        Variable exists = getVariable(var.getVariableName(), method.getVariablesInsideBlock());
        if (exists != null) {
            return methodAssistTwo(var, globals, method, exists);
        }
        exists = getVariable(var.getVariableName(), method.getArguments());
        if (exists != null) {
            return (isContainedVarValueValid(method.getVariablesInsideBlock(), var, exists)
                    || isContainedVarValueValid(method.getArguments(), var, exists)
                    || isContainedVarValueValid(globals, var, exists));
        }
        exists = getVariable(var.getVariableName(), globals);
        if (exists != null) {
            return methodAssistThree(var, globals, method, exists);
        }
        return false;
    }

    /*
    assist function  to isVarLineValid check if global var correct
     */
    private void checkGlobalInLine(ArrayList<Variable> globals, ArrayList<Variable> variables)
            throws CompileException {
        for (Variable elem : variables) {
            if (!isCorrectGlobalVar(elem, globals))
                throw new CompileException(ERR_SYNTAX);
        }
    }

    /*
   assist function  to isVarLineValid check if global var correct
    */
    private void checkifWhileCorrect(ArrayList<Variable> globals, ArrayList<Variable> variables,
                                     IfWhile ifOrWhile)
            throws CompileException {
        for (Variable var : variables) {
            if (!isIfWhileCorrect(var, globals, ifOrWhile)) {
                throw new CompileException(ERR_SYNTAX);
            }
        }
    }

    /**
     * check if the variable line is valid
     *
     * @param globals   the codes global variables
     * @param method    the method
     * @param line      code line
     * @param type      type
     * @param isFinal   if final var
     * @param ifOrWhile block of ifWhile
     * @return true if line is valid else false
     * @throws CompileException throws error
     */
    public boolean isVarLineValid(ArrayList<Variable> globals, Method method, String line, String type,
                                  boolean isFinal, IfWhile ifOrWhile)
            throws CompileException {
        ArrayList<Variable> variables = parseInputVar(line, type, isFinal);
        checkDuplicatesInCodeLine(variables);
        if (method == null && ifOrWhile == null) {
            checkGlobalInLine(globals, variables);
            return true;
        } else if (method == null) {
            checkifWhileCorrect(globals, variables, ifOrWhile);
            return true;
        } else {
            //now check method variables
            for (Variable var : variables) {
                if (!checkMethodVariable(var, globals, method)) {
                    throw new CompileException(ERR_SYNTAX);
                }
            }
            return true;
        }
    }

    /*
    assist func of variable line compilation
     */
    private void typeCheckAssist(Method method, ArrayList<Variable> globals, String line, IfWhile ifWhile,
                                 boolean isFinal, String type, String str) throws CompileException {
        if (type == null) {
            if (isFinal) {
                throw new InvalidVarDeclareException(ERR_DECLARE_FIN);
            }
        } else {
            str = str.replaceFirst(type, EMPTY);
            if (str.charAt(ZERO) == EQUAL_CHAR)
                throw new InvalidVariableNameException(ERR_NONAME);
        }
        if (!isVarLineValid(globals, method, str, type, isFinal, ifWhile))
            throw new CompileException(ERR_SYNTAX_TWO + line);
    }

    /**
     * start with the variable line code compilation
     *
     * @param method  the method inside
     * @param globals the globals of curr program code
     * @param line    the code line to check on
     * @param ifWhile the ifwhile
     * @throws CompileException throws error
     */
    public void variableLineCompile(Method method, ArrayList<Variable> globals, String line, IfWhile ifWhile)
            throws CompileException {
        String str = line.trim();
        //pattern for invalid modifiers
        Pattern illegal = Pattern.compile("^((public|protected|static|private|default)\\s+)");
        Matcher matcher = illegal.matcher(str);
        if (matcher.find()) {
            throw new InvalidModifierException();
        }
        //get final
        boolean isFinal = str.split(SPACE)[ZERO].trim().equals(FINAL);
        if (isFinal) {
            str = str.replace(FINAL, EMPTY).trim();
        }
        //type var
        String type = getVariableType(str);
        typeCheckAssist(method, globals, line, ifWhile, isFinal, type, str);
    }
}
