package filesprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * class that represents the main program runner of Directory Processor
 */
public class DirectoryProcessor {
    /*
    index arg 0
     */
    private static final int SOURCE_DIR = 0;
    /*
    index arg 1
     */
    private static final int COMMANDS = 1;
    /*
    two length args
     */
    private static final int TWO = 2;

    private static final String ERROR_USAGE = "ERROR: first argument should be a directory,second a file\n";

    /**
     * main function
     *
     * @param args given user input two ars of paths
     */
    public static void main(String[] args) {
        try {
            if (args.length != TWO) {
                throw new InvalidUsageException();
            }
            File inputFile = new File(args[SOURCE_DIR]);
            if (inputFile.isFile()) {
                throw new InvalidUsageException(ERROR_USAGE);
            }
            File[] sourceFiles = inputFile.listFiles();
            File commandsFile = new File(args[COMMANDS]);
            CheckCommandsFileStructure structure = new CheckCommandsFileStructure(commandsFile);
            LinkedList<String> commands = new LinkedList<>();
            FileReader reader = new FileReader(commandsFile);
            BufferedReader buffered = new BufferedReader(reader);
            String line;
            while ((line = buffered.readLine()) != null) {
                commands.add(line);
            }
            ParseCommandFile parsed = new ParseCommandFile(commands);
            parsed.parseSections(sourceFiles);
        } catch (InvalidUsageException | IOException | SectionCommandException | NullPointerException e) {
            System.err.print(e.getMessage());
        }
    }
}
