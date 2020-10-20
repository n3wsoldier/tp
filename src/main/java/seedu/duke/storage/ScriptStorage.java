package seedu.duke.storage;

import seedu.duke.exception.AniException;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.logger.AniLogger.getAniLogger;

public class ScriptStorage extends Storage {
    private final String storageDirectory;

    private static final Logger LOGGER = getAniLogger(ScriptStorage.class.getName());

    public ScriptStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    public String readScript(String workspaceName, String fileName) throws AniException {
        String scriptFilePath = storageDirectory + workspaceName + File.separator + fileName;
        String fileString = readFile(scriptFilePath);
        if (fileString.isBlank()) {
            LOGGER.log(Level.INFO, "Empty script file: " + scriptFilePath);
            throw new AniException("Script file is empty.");
        }

        return fileString;
    }
}
