package anichan.storage;

import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScriptStorage extends Storage {
    private final String storageDirectory;

    private static final String EMPTY_SCRIPT_FILE = "Script file is empty.";
    private static final Logger LOGGER = AniLogger.getAniLogger(ScriptStorage.class.getName());

    public ScriptStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    public String readScript(String workspaceName, String fileName) throws AniException {
        String scriptFilePath = storageDirectory + workspaceName + File.separator + fileName;
        String fileContent = readFile(scriptFilePath);
        if (fileContent.isBlank()) {
            LOGGER.log(Level.WARNING, "Empty script file: " + scriptFilePath);
            throw new AniException(EMPTY_SCRIPT_FILE);
        }

        return fileContent;
    }
}
