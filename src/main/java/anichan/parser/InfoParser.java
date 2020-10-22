package anichan.parser;

import anichan.exception.AniException;
import anichan.logger.AniLogger;
import anichan.command.InfoCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoParser extends CommandParser {
    protected static final String ANIME_ID_PARAM = "a";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for Anime ID!";
    private static final Logger LOGGER = AniLogger.getAniLogger(InfoParser.class.getName());
    
    private InfoCommand infoCommand;
    
    public InfoParser() {
        infoCommand = new InfoCommand();
        // LOGGER.setLevel(Level.WARNING);
    }
    
    public InfoCommand parse(String description) throws AniException {
        String[] paramGiven = parameterSplitter(description);

        if (paramGiven.length <= 1) {
            throw new AniException("-a ANIME_ID is required");
        } else {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, "Parameter parsed properly");
        }
        return infoCommand;
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
            case ANIME_ID_PARAM:
                paramFieldCheck(paramParts);
                paramExtraFieldCheck(paramParts);
                if (!isInt(paramParts[1].trim())) {
                    throw new AniException(NON_INTEGER_PROVIDED);
                }
                infoCommand.setAnimeIndex(Integer.parseInt(paramParts[1].trim()));
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
    }
}
