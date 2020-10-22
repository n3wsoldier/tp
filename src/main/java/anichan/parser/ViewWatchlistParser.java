package anichan.parser;

import anichan.command.ViewWatchlistCommand;
import anichan.exception.AniException;

import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewWatchlistParser extends CommandParser {
    protected static final String VIEW_PARAM = "v";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for watchlist ID!";
    private static final Logger LOGGER = getAniLogger(AddToWatchlistParser.class.getName());

    private ViewWatchlistCommand viewWatchlistCommand;

    public ViewWatchlistParser() {
        viewWatchlistCommand = new ViewWatchlistCommand();
        // LOGGER.setLevel(Level.WARNING);
    }

    public ViewWatchlistCommand parse(String description) throws AniException {
        String[] paramGiven = parameterSplitter(description);

        if (paramGiven.length <= 1) {
            throw new AniException("-v WATCHLIST_ID is required");
        } else {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, "Parameter parsed properly");
        }
        return viewWatchlistCommand;
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
            case VIEW_PARAM:
                paramFieldCheck(paramParts);
                paramExtraFieldCheck(paramParts);
                if (!isInt(paramParts[1].trim())) {
                    throw new AniException(NON_INTEGER_PROVIDED);
                }
                viewWatchlistCommand.setWatchlistIndex(Integer.parseInt(paramParts[1].trim()));
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
    }
}
