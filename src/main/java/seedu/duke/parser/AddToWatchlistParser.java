package seedu.duke.parser;

import seedu.duke.command.AddToWatchlistCommand;
import seedu.duke.exception.AniException;
import static seedu.duke.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddToWatchlistParser extends CommandParser {
    protected static final String ADD_PARAM = "a";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for Anime ID!";
    private static final Logger LOGGER = getAniLogger(AddToWatchlistParser.class.getName());
    
    private AddToWatchlistCommand addToWatchlistCommand;
    
    public AddToWatchlistParser() {
        addToWatchlistCommand = new AddToWatchlistCommand();
        // LOGGER.setLevel(Level.WARNING);
    }
    
    public AddToWatchlistCommand parse(String description) throws AniException {
        String[] paramGiven = parameterSplitter(description);
        
        if (paramGiven.length <= 1) {
            throw new AniException("-a ANIME_ID is required");
        } else {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, "Parameter parsed properly");
        }
        return addToWatchlistCommand;
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
            case ADD_PARAM:
                paramFieldCheck(paramParts);
                paramExtraFieldCheck(paramParts);
                if (!isInt(paramParts[1].trim())) {
                    throw new AniException(NON_INTEGER_PROVIDED);
                }
                addToWatchlistCommand.setAnimeIndex(Integer.parseInt(paramParts[1].trim()));
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
    }
}
