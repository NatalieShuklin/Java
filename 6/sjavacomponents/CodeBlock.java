
package oop.ex6.sjavacomponents;
import java.util.ArrayList;

/**
 * Abstract Class shows a code block under scopes, in sjava: while code block, method code block,
 * if code block - represents the state from inside the current scopes
 */
public abstract class CodeBlock {
    /**
     * the block type
     */
    protected String blockType;
    /**
     * vars outside scope block
     */
    protected ArrayList<Variable> variablesOutsideBlock;
    /**
     * vars inside scope block
     */
    protected ArrayList<Variable> variablesInsideBlock;
    /**
     * the block parent of the current block, that start and ends with {}
     */
    protected IfWhile blockParent;
    /**
     * arguments for current block in the given ( ) in declaration method, or if/while condition
     */
    protected ArrayList<Variable> arguments;

    /**
     * abstract function to get the inner variables of the block
     *
     * @return the inner variables of the block
     */
    public abstract ArrayList<Variable> getVariablesInsideBlock();

    /**
     * abstract function get the outer vars of the block
     *
     * @return get the outer vars of the block
     */
    public abstract ArrayList<Variable> getVariablesOutsideBlock();

    /**
     * abstract function to get the arguments of current block
     *
     * @return the arguments of current block
     */
    public abstract ArrayList<Variable> getArguments();

    /**
     * return type of block
     *
     * @return type of block: method, ifOrwhile
     */
    public abstract String getBlockType();
}
