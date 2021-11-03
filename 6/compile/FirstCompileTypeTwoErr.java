package oop.ex6.compile;

import oop.ex6.main.errors.*;
import oop.ex6.main.errors.syntax.InvalidClosingSuffixException;
import oop.ex6.main.errors.syntax.InvalidCommentException;
import oop.ex6.main.errors.syntax.InvalidSuffixException;
import oop.ex6.main.errors.syntax.MultiLineCommentException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents first phase of compilation: looks for IO errors in the file and main
 * Syntax errors , if all correct it saves the sjava code in list of strings
 * @author CS USER natashashuklin
 */
public class FirstCompileTypeTwoErr {
    /*
    regex empty line - to skip
     */
    private static final Pattern emptyLine = Pattern.compile("^\\s+$");
    /*
    regex for a valid comment in sJava
     */
    private static final Pattern validComment = Pattern.compile("^\\/{2,}");
    /*
    regex for wrong comment
     */
    private static final Pattern wrongComment = Pattern.compile("^.+\\/{2,}");
    /*
    regex another wrong comment option
     */
    private static final Pattern secondWrongComment = Pattern.compile("^.*\\/\\*+");
    /*
    regex suffix check
     */
    private static final Pattern suffixOfCodeLine = Pattern.compile("^.*[};{]\\s*$");
    /*
    regex suffix check
     */
    private static final Pattern suffixOnNewCodeLine = Pattern.compile("^\\s*[}]\\s*$");
    /*
    regex suffix check
     */
    private static final Pattern suffixEndBlock = Pattern.compile("[}]+");
    /*
    java code saved as list of strings, program will access it later
     */
    private ArrayList<String> sJavaCode;
    /*
    name of the file
     */
    private String name;

    /**
     * Constructor of First compiler stage
     * @param file the file to compile on
     */
    public FirstCompileTypeTwoErr(String file) {
        this.sJavaCode = new ArrayList<>();
        this.name = file;
    }
    /*
    check end block suffix end }
     */
    private void CheckEndBlockSuffix(String line) throws InvalidClosingSuffixException {
        Matcher matcher = suffixOnNewCodeLine.matcher(line);
        if (!matcher.find()) {
            //throw exception for } appearing not in its own line
            throw  new InvalidClosingSuffixException();
        }
    }

    /**
     * compilation process start on the curr file
     * @throws IOException throw IO exceotion while reading file if error occurred
     * @throws CompileException throw Syntax error if found in file
     */
    public void beginCompilation() throws IOException, CompileException {
        FileReader readFile = new FileReader(this.name);
        BufferedReader bufferedReader = new BufferedReader(readFile);
        String line = bufferedReader.readLine();
        Matcher matcher;
        while (line != null) {
            matcher = validComment.matcher(line);
            if (matcher.find()) {
                line = bufferedReader.readLine();
                continue;
            }
            matcher = emptyLine.matcher(line);
            if (matcher.find() || line.trim().isEmpty()) {
                line = bufferedReader.readLine();
                continue;
            }
            matcher = wrongComment.matcher(line);
            if (matcher.find()) {
                //throw illegal comment
                throw new InvalidCommentException();
            }
            matcher = secondWrongComment.matcher(line);
            if (matcher.find()) {
                throw new MultiLineCommentException();
            }
            matcher = suffixEndBlock.matcher(line);
            if (matcher.find()) {
                CheckEndBlockSuffix(line);
            }
            matcher = suffixOfCodeLine.matcher(line);
            if (!matcher.find()) {
                throw new InvalidSuffixException();
            }
            this.sJavaCode.add(line);
            line = bufferedReader.readLine();
        }
    }

    /**
     * get the sjava code after type 2 errors passed, in a list of strings
     * @return  sjava code
     */
    public ArrayList<String> getCodeList() {
        return this.sJavaCode;
    }
}
