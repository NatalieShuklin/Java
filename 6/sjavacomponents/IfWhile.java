package oop.ex6.sjavacomponents;

import java.util.ArrayList;

/**
 * This class represents a IfWhile Block of type Code Block
 * If and while handled similarly thus IfWhile object represents an If block
 * or a while block, by sJava both have same requirements and attributes alike
 *
 * @author Natalia Shuklin cs user - natashashuklin
 */
public class IfWhile extends CodeBlock {
    /*
    parent block
     */
    private CodeBlock blockParent;
    /*
    current type block
     */
    private static final String TYPE = "IfOrWhile";
    /*
    method type
     */
    private static final String TYPE_METHOD = "Method";

    /**
     * constructor of IfWhile object
     *
     * @param arguments the given arguments for the if or while in ( )
     * @param codeBlock code block
     */
    public IfWhile(ArrayList<Variable> arguments, CodeBlock codeBlock) {
        this.variablesInsideBlock = new ArrayList<>();
        this.arguments = arguments;
        this.blockType = TYPE;
        this.blockParent = codeBlock;
        this.variablesOutsideBlock = findOuterScopeVariables();
        this.arguments = arguments;
    }


    /**
     * get vars inside the current block
     *
     * @return vars inside the current block
     */
    public ArrayList<Variable> getVariablesInsideBlock() {
        return this.variablesInsideBlock;
    }

    /**
     * get vars outside block scope
     *
     * @return vars outside block scope
     */
    public ArrayList<Variable> getVariablesOutsideBlock() {
        return this.variablesOutsideBlock;
    }
    /**
     * get block scope type
     *
     * @return block scope type
     */
    public String getBlockType() {
        return this.blockType;
    }

    /**
     * get current block arguments inside the ( ) condition
     *
     * @return current block arguments inside the ( ) condition
     */
    public ArrayList<Variable> getArguments() {
        return this.arguments;
    }

    /**
     * return type of parent block
     *
     * @return type of parent block
     */
    public String getTypeOfContainsScope() {
        return blockParent.getBlockType();
    }

    /*
    get the outside(above) block variables for current scope
     */
    private ArrayList<Variable> findOuterScopeVariables() {
        this.variablesOutsideBlock = new ArrayList<>();
        if (this.blockParent.getBlockType().equals(TYPE_METHOD)) {
            Method parentScope = (Method) this.blockParent;
            if (parentScope.getVariablesInsideBlock() != null) {
                this.variablesOutsideBlock.addAll(parentScope.getVariablesInsideBlock());
            }
        } else {
            IfWhile parentScope = (IfWhile) this.blockParent;
            if (parentScope.variablesOutsideBlock != null) {
                this.variablesOutsideBlock.addAll(parentScope.getVariablesOutsideBlock());
            }
            if (parentScope.variablesInsideBlock != null) {
                this.variablesOutsideBlock.addAll(parentScope.variablesInsideBlock);
            }
        }
        return this.variablesOutsideBlock;
    }
}
