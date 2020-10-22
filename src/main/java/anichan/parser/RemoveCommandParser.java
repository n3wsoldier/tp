package anichan.parser;

import anichan.command.RemoveCommand;
import anichan.exception.AniException;
import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveCommandParser extends CommandParser {
    protected static final String REMOVE_PARAM = "d";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for Anime ID!";
    private static final Logger LOGGER = getAniLogger(RemoveCommandParser.class.getName());

    private RemoveCommand removeCommand;

    public RemoveCommandParser() {
        removeCommand = new RemoveCommand();
        // LOGGER.setLevel(Level.WARNING);
    }

    public RemoveCommand parse(String description) throws AniException {
        String[] paramGiven = parameterSplitter(description);

        if (paramGiven.length <= 1) {
            throw new AniException("-d ANIME_ID_IN_WATCHLIST is required");
        } else {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, "Parameter parsed properly");
        }
        return removeCommand;
    }

    private void parameterParser(String[] paramGiven) throws AniException {
        for (String param : paramGiven) {
            String[] paramParts = param.split(" ");
            if (paramParts.length == 0) {
                break;
            }

            switch (paramParts[0].trim()) {
            case "": // skip empty param
                break;
            case REMOVE_PARAM:
                paramFieldCheck(paramParts);
                paramExtraFieldCheck(paramParts);
                if (!isInt(paramParts[1].trim())) {
                    throw new AniException(NON_INTEGER_PROVIDED);
                }
                removeCommand.setWatchlistListIndex(Integer.parseInt(paramParts[1].trim()));
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
    }
}
