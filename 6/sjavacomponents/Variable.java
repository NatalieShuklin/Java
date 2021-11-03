package oop.ex6.sjavacomponents;

/**
 * This class represents a variable in the sJava code, has it attributes and relevant operations
 *
 * @author Natalia Shuklin @cs user natashashuklin
 */
public class Variable {
    /*
    represents a var type
     */
    private String variableType;
    /*
    represents name given to variable
     */
    private String variableName;
    /*
    represents a var value
     */
    private String variableValue;
    /*
    represetns if var is final
     */
    private boolean isVariableFinal;
    /*
    represetns if var is initiated
     */
    private boolean isVariableInit;
    /*
    represents if var belong to an outer scope block
     */
    private boolean doesBelongToOutBlock;

    /**
     * Variable constructor. constructs the variable with attributes given
     *
     * @param isFinal if var is final then true, else false
     * @param isInit  if var is initiated then true, else false
     * @param name    the var name
     * @param type    the var type
     * @param value   the var value
     */
    public Variable(boolean isFinal, boolean isInit, String name, String type, String value) {
        this.isVariableFinal = isFinal;
        this.isVariableInit = isInit;
        this.doesBelongToOutBlock = false;
        this.variableName = name;
        this.variableType = type;
        this.variableValue = value;
    }

    /**
     * set varriable is init to true
     */
    public void setVariableInit() {
        this.isVariableInit = true;
    }

    /**
     * set variable is belongs to outer scope block to true
     */
    public void setDoesBelongToOuterBlock() {
        this.doesBelongToOutBlock = true;
    }

    /**
     * get the name of var
     *
     * @return the name of var
     */
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * get the var type
     *
     * @return the var type
     */
    public String getVariableType() {
        return this.variableType;
    }

    /**
     * get var value
     *
     * @return var value
     */
    public String getVariableValue() {
        return this.variableValue;
    }

    /**
     * get if var is final
     *
     * @return true if var is final, else false
     */
    public boolean isVariableFinal() {
        return this.isVariableFinal;
    }

    /**
     * get if var is initiated
     *
     * @return true if var is initiated, else false
     */
    public boolean isVariableInit() {
        return this.isVariableInit;
    }

    /**
     * get if var belongs to outer scope
     *
     * @return true if var belongs to outer scope block, else false
     */
    public boolean isDoesBelongToOutBlock() {
        return this.doesBelongToOutBlock;
    }

    /**
     * set the var type
     *
     * @param type var type
     */
    public void setType(String type) {
        this.variableType = type;
    }

    /**
     * set the var value
     *
     * @param value var value
     */
    public void setValue(String value) {
        this.variableValue = value;
    }

}
