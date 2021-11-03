package oop.ex6.sjavacomponents;

import java.util.ArrayList;

/**
 * Class represents a method block of type CodeBlock
 */
public class Method extends CodeBlock {
    /*
    type method
     */
    private static final String TYPE_METHOD = "Method";

    /**
     * method constructor
     *
     * @param arguments args of the method in declaration
     */
    public Method(ArrayList<Variable> arguments) {
        this.variablesInsideBlock = new ArrayList<>();
        this.arguments = arguments;
        this.blockParent = null;
        this.blockType = TYPE_METHOD;
    }

    /**
     * get block type
     *
     * @return block type
     */
    public String getBlockType() {
        return this.blockType;
    }

    /**
     * get arguments of the method
     *
     * @return arguments of the method
     */
    public ArrayList<Variable> getArguments() {
        return this.arguments;
    }

    /**
     * get vars outside (above) method
     *
     * @return vars outside (above) method
     */
    public ArrayList<Variable> getVariablesOutsideBlock() {
        return this.variablesOutsideBlock;
    }

    /**
     * get vars inside the current method
     *
     * @return vars inside the current method
     */
    public ArrayList<Variable> getVariablesInsideBlock() {
        return this.variablesInsideBlock;
    }
}
