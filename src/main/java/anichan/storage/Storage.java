package anichan.storage;

import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Storage {
    private static final String NEGATIVE_INTEGER_REGEX = "^[-]\\d+$";
    private static final String POSITIVE_INTEGER_REGEX = "^\\d+$";

    private static final String EMPTY_STRING = "";
    private static final String FILE_DOES_NOT_EXIST = "File does not exist.";
    private static final String WRITE_TO_FILE_FAILED = "Failed to write to file.";

    private static final Logger LOGGER = AniLogger.getAniLogger(Storage.class.getName());

    public String readFile(String filePath) throws AniException {
        String fileContent = EMPTY_STRING;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            LOGGER.log(Level.INFO, "Read from file: " + filePath);
        } catch (IOException exception) {
            LOGGER.log(Level.INFO, "File does not exist at: " + filePath);
            throw new AniException(FILE_DOES_NOT_EXIST);
        }

        return fileContent;
    }

    public void writeFile(String filePath, String fileContent) throws AniException {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(fileContent);
            fileWriter.close();
            LOGGER.log(Level.INFO, "Wrote to file: " + filePath);
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Failed to write to file at: " + filePath);
            throw new AniException(WRITE_TO_FILE_FAILED);
        }
    }

    public boolean isPositiveInteger(String integerString) {
        return integerString.matches(POSITIVE_INTEGER_REGEX);
    }

    public boolean isPositiveOrNegativeInteger(String integerString) {
        return integerString.matches(POSITIVE_INTEGER_REGEX) || integerString.matches(NEGATIVE_INTEGER_REGEX);
    }
}
