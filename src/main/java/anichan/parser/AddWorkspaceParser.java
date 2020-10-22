package anichan.parser;

import anichan.command.AddWorkspaceCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddWorkspaceParser extends CommandParser {
    public static final String REGEX_CHARACTER_HYPHEN = "-";
    public static final String EXCEPTION_INVALID_PARAMETERS = "Invalid parameters detected!";
    public static final String REGEX_CHARACTER_SPACE = " ";
    public static final String PARAMETER_NAME = "n";
    private final AddWorkspaceCommand addWorkspaceCommand;
    private static final Logger LOGGER = AniLogger.getAniLogger(BookmarkParser.class.getName());

    public AddWorkspaceParser() {
        addWorkspaceCommand = new AddWorkspaceCommand();
    }

    public AddWorkspaceCommand parse(String description) throws AniException {
        String[] paramGiven = parameterSplitter(description);
        paramIsSetCheck(paramGiven);
        parameterParser(paramGiven);
        return addWorkspaceCommand;
    }

    public void parameterParser(String[] paramGiven) throws AniException {
        for (String param : paramGiven) {
            String[] paramParts = param.split(REGEX_CHARACTER_SPACE, 2);

            if (paramParts.length == 0) {
                LOGGER.log(Level.WARNING, "Exception:" + EXCEPTION_INVALID_PARAMETERS);
                throw new AniException(EXCEPTION_INVALID_PARAMETERS);
            }


            if ((paramParts[0].trim().equals(PARAMETER_NAME))) {
                paramFieldCheck(paramParts);
                addWorkspaceCommand.setWorkspaceName(paramParts[1].trim());
                LOGGER.log(Level.INFO, "Parsed workspace name: " + paramParts[1].trim());
            }
        }

        if (addWorkspaceCommand.getNewWorkspaceName() == null) {
            LOGGER.log(Level.WARNING, "Exception:" + EXCEPTION_INVALID_PARAMETERS);
            throw new AniException(EXCEPTION_INVALID_PARAMETERS);
        }
    }
}
